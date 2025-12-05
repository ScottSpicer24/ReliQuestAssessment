package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.EmployeeImpl;
import com.challenge.api.model.dto.CreateEmployeeRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    // hash map to store employees that can be queried via uuid
    // concurrent HM to deal with concurrency
    private final Map<UUID, Employee> employees = new ConcurrentHashMap<>();

    public EmployeeServiceImpl() {
        // mock employees
        EmployeeImpl scott = new EmployeeImpl(
                UUID.randomUUID(),
                "Scott",
                "Spicer",
                90000,
                26,
                "Software Engineer",
                "scott.spicer@proton.me",
                Instant.now(),
                null);
        employees.put(scott.getUuid(), scott);

        EmployeeImpl john = new EmployeeImpl(
                UUID.randomUUID(),
                "John",
                "Doe",
                150000,
                35,
                "Senior Developer",
                "John.Doe@gmail.com",
                Instant.now(),
                null);
        employees.put(john.getUuid(), john);
    }

    // return employees
    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    // Get new employee
    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employees.get(uuid); // pull from map via uuid
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with UUID %s not found".formatted(uuid));
        }
        return employee;
    }

    // Create an new employee
    @Override
    public Employee createEmployee(CreateEmployeeRequest request) {
        // check for empty request
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body must not be null");
        }

        // gen info and pull rest from request, create employee
        UUID uuid = UUID.randomUUID();
        EmployeeImpl employee = new EmployeeImpl(
                uuid,
                request.getFirstName(),
                request.getLastName(),
                request.getSalary(),
                request.getAge(),
                request.getJobTitle(),
                request.getEmail(),
                Instant.now(),
                null);

        // Put in concurrent hashmap
        employees.put(employee.getUuid(), employee);
        return employee;
    }
}
