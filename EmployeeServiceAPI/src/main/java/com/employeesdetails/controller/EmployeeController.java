package com.employeesdetails.controller;

import com.employeesdetails.dto.EmployeeDTO;
import com.employeesdetails.entities.Employee;
import com.employeesdetails.servicelayer.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@Validated @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/tax")
    public ResponseEntity<List<EmployeeDTO>> calculateTaxDeduction() {
        List<EmployeeDTO> taxDetails = employeeService.calculateTaxDeduction();
        return new ResponseEntity<>(taxDetails, HttpStatus.OK);
    }
}