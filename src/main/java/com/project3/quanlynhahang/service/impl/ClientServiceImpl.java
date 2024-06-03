package com.project3.quanlynhahang.service.impl;

import com.project3.quanlynhahang.config.ApiException;
import com.project3.quanlynhahang.dtos.reponse.LoginResponse;
import com.project3.quanlynhahang.dtos.reponse.OrderItemResponse;
import com.project3.quanlynhahang.dtos.reponse.OrderResponse;
import com.project3.quanlynhahang.dtos.request.*;
import com.project3.quanlynhahang.entity.*;
import com.project3.quanlynhahang.enums.AccountStatus;
import com.project3.quanlynhahang.enums.Role;
import com.project3.quanlynhahang.enums.ShippingStatus;
import com.project3.quanlynhahang.enums.TransactionStatus;
import com.project3.quanlynhahang.repository.*;
import com.project3.quanlynhahang.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final CustomerRepository customerRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public String register(RegisterRequest request) {
        validateUsername(request.getUsername());
        Customer customer = Customer.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .status(AccountStatus.ACTIVE)
                .role(Role.USER)
                .build();
        customerRepository.save(customer);
        return "Register successful.";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new ApiException("Username or password is incorrect.")
        );
        if (!encoder.matches(request.getPassword(), customer.getPassword())) {
            throw new ApiException("Username or password is incorrect.");
        }
        validateCustomerStatus(customer.getId());

        return LoginResponse.builder()
                .message("Login successful.")
                .object(customer)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public String updateInfo(Long customerId, UpdateInfoRequest request) {
        Customer customer = validateCustomerStatus(customerId);
        if (Objects.nonNull(request.getUsername())) {
            validateUsername(request.getUsername());
            customer.setUsername(request.getUsername());
        }
        if (Objects.nonNull(request.getPassword())) {
            customer.setPassword(encoder.encode(request.getPassword()));
        }
        if (Objects.nonNull(request.getPhone())) {
            customer.setPhone(request.getPhone());
        }
        if (Objects.nonNull(request.getEmail())) {
            customer.setEmail(request.getEmail());
        }
        if (Objects.nonNull(request.getAddress())) {
            customer.setAddress(request.getAddress());
        }
        customerRepository.save(customer);

        return "Update successful.";
    }

    @Override
    public List<Food> getListMenu() {
        return foodRepository.findAll();
    }

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {
        validateCustomerStatus(request.getCustomerId());
        BigDecimal totalAmount = getTotal(request);
        Orders orders = Orders.builder()
                .customerId(request.getCustomerId())
                .shipperId(findAvailableShipper().getId())
                .paymentMethod(request.getPaymentMethod())
                .transactionStatus(request.getTransactionStatus())
                .shippingStatus(ShippingStatus.PENDING)
                .totalAmount(totalAmount)
                .remark(request.getNote())
                .build();
        orderRepository.save(orders);

        List<OrderItem> orderItems = new ArrayList<>();
        request.getOrderItems().forEach(t -> {
            Food food = foodRepository.findById(t.getFoodId())
                    .orElseThrow(() -> new ApiException("Food not found with id: " + t.getFoodId()));
            OrderItem orderItem = OrderItem.builder()
                    .orders(orders)
                    .food(food)
                    .quantity(t.getQuantity())
                    .build();
            orderItems.add(orderItem);
        });
        orderItemRepository.saveAll(orderItems);

        return OrderResponse.builder()
                .orderItems(toOrderItemResponse(orderItems))
                .total(totalAmount)
                .shipperName(findAvailableShipper().getFullName())
                .transactionStatus(request.getTransactionStatus())
                .shippingStatus(ShippingStatus.PENDING)
                .build();
    }

    @Override
    public String cancelOrder(Long orderId, CancelRequest request) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(
                () -> new ApiException("Order not found with id: " + orderId));
        orders.setShippingStatus(ShippingStatus.CANCELED);
        orders.setTransactionStatus(TransactionStatus.CANCELED);
        orders.setRemark(request.getCancelRemark());
        orderRepository.save(orders);

        return "Cancel order successful.";
    }

    @Override
    public String rateOrder(Long orderId, RatingRequest request) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(
                () -> new ApiException("Order not found with id: " + orderId));
        if (orders.getShippingStatus() != ShippingStatus.DELIVERED) {
            throw new ApiException("Can not rate shipping order. Order not delivered.");
        }
        orders.setRatingPoint(request.getRatingPoint());
        orders.setRatingComment(request.getRatingComment());
        orderRepository.save(orders);

        return "Rate order successful.";
    }

    @Override
    public List<OrderResponse> getListOrder(Long customerId) {
        List<Orders> ordersList = orderRepository.findAllByCustomerId(customerId);
        List<OrderResponse> responses = new ArrayList<>();
        ordersList.forEach(t -> {
            Employee shipper = employeeRepository.findById(t.getShipperId()).orElse(null);
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderItems(toOrderItemResponse(t.getOrderItems()))
                    .total(t.getTotalAmount())
                    .shipperName(Objects.nonNull(shipper) ? shipper.getFullName() : null)
                    .shippingStatus(t.getShippingStatus())
                    .transactionStatus(t.getTransactionStatus())
                    .ratingPoint(t.getRatingPoint())
                    .ratingComment(t.getRatingComment())
                    .createdAt(t.getCreatedAt())
                    .updatedAt(t.getUpdatedAt())
                    .build();
            responses.add(orderResponse);
        });
        return responses;
    }

    @Override
    public String payOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(
                () -> new ApiException("Order not found with id: " + orderId)
        );
        // TODO implement payment logic
        orders.setTransactionStatus(TransactionStatus.SUCCESS);
        orderRepository.save(orders);

        return "Pay order successful.";
    }

    private void validateUsername(String username) {
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        if (customerOpt.isPresent()) {
            throw new ApiException("Username already in used.");
        }
    }

    private Employee findAvailableShipper() {
        List<Employee> shipper = employeeRepository.findByRoleAndStatus(Role.SHIPPER, AccountStatus.ACTIVE);
        Map<Long, Employee> mapEmployeeById = shipper.stream()
                .collect(Collectors.toMap(Employee::getId, e -> e));
        List<Long> unavailableShipper = orderRepository.findUnavailableShipperIds(
                List.of(ShippingStatus.DELIVERED, ShippingStatus.CANCELED));
        return mapEmployeeById.keySet()
                .stream()
                .filter(e -> !unavailableShipper.contains(e))
                .map(mapEmployeeById::get)
                .findFirst()
                .orElseThrow(() -> new ApiException("No available shipper found"));
    }

    public BigDecimal getTotal(OrderRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItemRequest> orderItems = request.getOrderItems();
        for (OrderItemRequest orderItem : orderItems) {
            Food food = foodRepository.findById(orderItem.getFoodId())
                    .orElseThrow(() -> new ApiException("Food not found with id: " + orderItem.getFoodId()));
            BigDecimal quantity = BigDecimal.valueOf(orderItem.getQuantity());
            BigDecimal price = BigDecimal.valueOf(food.getPrice());
            total = total.add(quantity.multiply(price));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private Customer validateCustomerStatus(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ApiException("Customer not found.")
        );
        switch (customer.getStatus()) {
            case INACTIVE -> throw new ApiException("Customer is inactive.");
            case SUSPENDED -> throw new ApiException("Customer is suspended.");
            case LOCKED -> throw new ApiException("Customer is locked.");
            case DELETED -> throw new ApiException("Customer not found.");
        }
        return customer;
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
