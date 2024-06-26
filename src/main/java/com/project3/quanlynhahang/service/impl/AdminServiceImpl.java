package com.project3.quanlynhahang.service.impl;

import com.project3.quanlynhahang.config.ApiException;
import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.reponse.OrderItemResponse;
import com.project3.quanlynhahang.dtos.reponse.OrderResponse;
import com.project3.quanlynhahang.dtos.reponse.ReportResponse;
import com.project3.quanlynhahang.dtos.request.*;
import com.project3.quanlynhahang.entity.*;
import com.project3.quanlynhahang.enums.AccountStatus;
import com.project3.quanlynhahang.enums.FoodStatus;
import com.project3.quanlynhahang.enums.Role;
import com.project3.quanlynhahang.repository.CustomerRepository;
import com.project3.quanlynhahang.repository.EmployeeRepository;
import com.project3.quanlynhahang.repository.FoodRepository;
import com.project3.quanlynhahang.repository.OrderRepository;
import com.project3.quanlynhahang.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final byte[] pngPattern = new byte[] { (byte)0x89 , (byte)0x50, (byte)0x4e ,(byte)0x47};
    private final byte[] jpgPattern = new byte[] { (byte)0xFF, (byte)0xD8, (byte)0xFF};
    private final byte[] gifPattern = new byte[] { 0x47, 0x49, 0x46, 0x38};
    private final byte[] webpPattern = new byte[] {	0x52, 0x49, 0x46, 0x46};

    private final EmployeeRepository employeeRepository;
    private final FoodRepository foodRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final OrderRepository orderRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<Employee> employeeOpt = employeeRepository.findByUsername(request.getUsername());
        if (employeeOpt.isEmpty()) {
            log.warn("Login failed for username: {}. User not found.", request.getUsername());
            throw new ApiException("Username or password is incorrect.");
        }

        Employee employee = employeeOpt.get();
        if (!encoder.matches(request.getPassword(), employee.getPassword())) {
            log.warn("Login failed for username: {}. Incorrect password.", request.getUsername());
            throw new ApiException("Username or password is incorrect.");
        }

        return LoginResponse.builder()
                .message("Login successful.")
                .object(employee)
                .build();
    }

    @Override
    public String createAccount(CreateAccountRequest request) {
        Optional<Employee> employeeOpt = employeeRepository.findByUsername(request.getUsername());
        if (employeeOpt.isPresent()) {
            log.warn("Create account failed. {} already in used.", request.getUsername());
            throw new ApiException("Username already in used.");
        }
        if (request.getRole().equals(Role.ADMIN)) {
            throw new ApiException("Can not create ADMIN account.");
        }
        Employee employee = Employee.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .status(AccountStatus.ACTIVE)
                .role(request.getRole())
                .build();
        employeeRepository.save(employee);

        return "Create account successful.";
    }

    @Override
    public List<Food> getListMenu() {
        return foodRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public String addFood(FoodRequest request) {
        validateFoodName(request.getName());
        Food food = Food.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ingredients(request.getIngredients())
                .image(createImageFile(request.getImageContent()))
                .category(request.getCategory())
                .price(request.getPrice())
                .status(FoodStatus.AVAILABLE)
                .build();
        foodRepository.save(food);

        return "Add food successful.";
    }

    @Override
    public String updateFood(FoodRequest request, Long foodId) {
        Optional<Food> foodOpt = foodRepository.findById(foodId);
        if (foodOpt.isEmpty()) {
            log.warn("Update failed. Food not found for id: {}", foodId);
            throw new ApiException("Food not found.");
        }
        Food food = foodOpt.get();
        if (Objects.nonNull(request.getName())) {
            validateFoodName(request.getName());
            food.setName(request.getName());
        }
        if (Objects.nonNull(request.getDescription())) {
            food.setDescription(request.getDescription());
        }
        if (Objects.nonNull(request.getImageContent())) {
            try {
                deleteImageFile(food.getImage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            food.setImage(createImageFile(request.getImageContent()));
        }
        if (Objects.nonNull(request.getCategory())) {
            food.setCategory(request.getCategory());
        }
        if (Objects.nonNull(request.getPrice())) {
            food.setPrice(request.getPrice());
        }
        if (Objects.nonNull(request.getStatus())) {
            food.setStatus(request.getStatus());
        }
        foodRepository.save(food);

        return "Update successful.";
    }

    @Override
    public String deleteFood(Long foodId) {
        Optional<Food> foodOpt = foodRepository.findById(foodId);
        if (foodOpt.isEmpty()) {
            log.warn("Delete food failed. Food doesnt existed with id: {}", foodId);
            throw new ApiException("Food doesnt existed.");
        }
        Food food = foodOpt.get();
        food.setDeleted(true);
        foodRepository.save(food);

        return "Delete food successfully";
    }

    @Override
    public List<Employee> getListEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        employeeList.sort(Comparator.comparing(Employee::getId));

        return employeeList;
    }

    @Override
    public String updateEmployee(Long employeeId, UpdateInfoRequest request) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ApiException("Employee not found.")
        );
        if (Objects.nonNull(request.getPassword())) {
            employee.setPassword(encoder.encode(request.getPassword()));
        }
        if (Objects.nonNull(request.getPhone())) {
            employee.setPhone(request.getPhone());
        }
        if (Objects.nonNull(request.getAddress())) {
            employee.setAddress(request.getAddress());
        }
        if (Objects.nonNull(request.getRole()) && !request.getRole().equals(Role.ADMIN)) {
            employee.setRole(request.getRole());
        }
        if (Objects.nonNull(request.getStatus())) {
            employee.setStatus(request.getStatus());
        }
        employeeRepository.save(employee);

        return "Update employee successful.";
    }

    @Override
    public String deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ApiException("Employee not found.")
        );
        employee.setStatus(AccountStatus.DELETED);
        employeeRepository.save(employee);

        return "Delete employee successful.";
    }

    @Override
    public String updateCustomerStatus(UpdateStatusRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new ApiException("Customer not found")
        );
        customer.setStatus(request.getStatus());
        customerRepository.save(customer);

        return "Update customer status successful.";
    }

    @Override
    public ReportResponse getReport(ReportRequest request) {
        List<Orders> orders = orderRepository.findOrderReport(request);
        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(t -> {
            Employee shipper = employeeRepository.findById(t.getShipperId()).orElse(null);
            Customer customer = customerRepository.findById(t.getCustomerId()).orElse(null);
            OrderResponse orderItem = OrderResponse.builder()
                    .orderItems(toOrderItemResponse(t.getOrderItems()))
                    .total(t.getTotalAmount())
                    .shipperName(Objects.nonNull(shipper) ? shipper.getFullName() : null)
                    .shippingStatus(t.getShippingStatus())
                    .transactionStatus(t.getTransactionStatus())
                    .ratingPoint(t.getRatingPoint())
                    .ratingComment(t.getRatingComment())
                    .createdAt(t.getCreatedAt())
                    .updatedAt(t.getUpdatedAt())
                    .customerName(Objects.nonNull(customer) ? customer.getFullName() : null)
                    .build();
            orderResponses.add(orderItem);
        });
        BigDecimal totalAmount = orderResponses.stream()
                .map(OrderResponse::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        return ReportResponse.builder()
                .totalAmount(totalAmount)
                .totalOrders(orders.size())
                .orderResponses(orderResponses)
                .build();
    }

    private String createImageFile(String fileContent) {
        if (Objects.isNull(fileContent) || StringUtils.isEmpty(fileContent)) {
            return null;
        }
        byte[] image = Base64.getDecoder().decode(fileContent);
        String extension = isValidTypeFile(image);

        String fileName = UUID.randomUUID() + extension;
        Path folderPath = Paths.get("public/image/");
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                throw new ApiException(e.getMessage());
            }
        }
        Path destination = Paths.get(folderPath.toString(), fileName);
        try {
            Files.write(destination, image);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }

        return String.format("%s/uploads/%s", "http://localhost:8081", fileName);
    }

    private String isValidTypeFile(byte[] fileContent){
        if (isMatch(pngPattern, fileContent)) return ".png";
        if (isMatch(webpPattern, fileContent)) return ".webp";
        if (isMatch(jpgPattern, fileContent)) return ".jpg";
        if (isMatch(gifPattern, fileContent)) return ".gif";
        return null;
    }

    private boolean isMatch(byte[] pattern, byte[] data) {
        if (pattern.length <= data.length) {
            for (int idx = 0; idx < pattern.length; ++idx) {
                if (pattern[idx] != data[idx])
                    return false;
            }
            return true;
        }
        return false;
    }

    private void deleteImageFile(String url) throws IOException {
        String[] urlParts = url.split("/");
        String filename = urlParts[urlParts.length - 1].strip();

        Path path = Paths.get("public/image/", filename);
        Files.deleteIfExists(path);
    }

    private void validateFoodName(String name) {
        Optional<Food> foodOpt = foodRepository.findByName(name);
        if (foodOpt.isPresent()) {
            log.warn("Request failed. {} already exist.", name);
            throw new ApiException("Food already exist.");
        }
    }

    private List<OrderItemResponse> toOrderItemResponse(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        orderItems.forEach(t -> {
            Food food = t.getFood();
            OrderItemResponse data = OrderItemResponse.builder()
                    .id(t.getId())
                    .quantity(t.getQuantity())
                    .foodName(food.getName())
                    .category(food.getCategory())
                    .description(food.getDescription())
                    .ingredient(food.getIngredients())
                    .price(food.getPrice())
                    .build();
            orderItemResponses.add(data);
        });
        return orderItemResponses;
    }

}
