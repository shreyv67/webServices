package com.employeesdetails.servicelayer;

import com.employeesdetails.dto.EmployeeDTO;
import com.employeesdetails.entities.Employee;
import com.employeesdetails.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<EmployeeDTO> calculateTaxDeduction() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmployeeId(employee.getEmployeeId());
            dto.setFirstName(employee.getFirstName());
            dto.setLastName(employee.getLastName());

            double yearlySalary = calculateYearlySalary(employee);
            dto.setYearlySalary(yearlySalary);

            double tax = calculateTax(yearlySalary);
            double cess = calculateCess(yearlySalary);
            dto.setTaxAmount(tax);
            dto.setCessAmount(cess);

            employeeDTOList.add(dto);
        }

        return employeeDTOList;
    }

    private double calculateYearlySalary(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        int monthsWorked = currentDate.getMonthValue() - employee.getDateOfJoining().getMonthValue();
        if (monthsWorked < 0) monthsWorked += 12;
        double totalSalary = employee.getSalary() * monthsWorked;
        return totalSalary;
    }

    private double calculateTax(double yearlySalary) {
        double tax = 0.0;
        if (yearlySalary <= 250000) {
            tax = 0.0;
        } else if (yearlySalary <= 500000) {
            tax = (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            tax = (yearlySalary - 500000) * 0.10 + 12500;
        } else {
            tax = (yearlySalary - 1000000) * 0.20 + 37500;
        }
        return tax;
    }

    private double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }
}