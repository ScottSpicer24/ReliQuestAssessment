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

    private final Map<UUID, Employee> employees = new ConcurrentHashMap<>();

    public EmployeeServiceImpl() {
        // Optionally seed a couple of mock employees
        EmployeeImpl alice = new EmployeeImpl(
                UUID.randomUUID(),
                "Scott",
                "Spicer",
                90000,
                26,
                "Software Engineer",
                "scott.spicer@proton.me",
                Instant.now(),
                null);
        employees.put(alice.getUuid(), alice);

        EmployeeImpl bob = new EmployeeImpl(
                UUID.randomUUID(),
                "John",
                "Doe",
                150000,
                35,
                "Senior Developer",
                "John.Doe@gmail.com",
                Instant.now(),
                null);
        employees.put(bob.getUuid(), bob);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Employee getEmployeeByUuid(UUID uuid) {
        Employee employee = employees.get(uuid);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with UUID %s not found".formatted(uuid));
        }
        return employee;
    }

    @Override
    public Employee createEmployee(CreateEmployeeRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body must not be null");
        }

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

        employees.put(employee.getUuid(), employee);
        return employee;
    }
}
