package com.challenge.api.service;

import com.challenge.api.model.Employee;
import com.challenge.api.model.dto.CreateEmployeeRequest;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    /**
     * @return all employees, unfiltered.
     */
    List<Employee> getAllEmployees();

    /**
     * @param uuid Employee UUID
     * @return matching Employee
     */
    Employee getEmployeeByUuid(UUID uuid);

    /**
     * @param request fields required to create an Employee
     * @return newly created Employee
     */
    Employee createEmployee(CreateEmployeeRequest request);
}
