package com.project3.quanlynhahang.repository;

import com.project3.quanlynhahang.entity.Employee;
import com.project3.quanlynhahang.enums.AccountStatus;
import com.project3.quanlynhahang.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    List<Employee> findByRoleAndStatus(Role role, AccountStatus status);

}
