package org.example.task2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.task2.dto.EmployeeDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.service.EmployeeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "EMPLOYEE")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmployeeDto dto) {
        ResponseDto<?> response = employeeService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        ResponseDto<?> response = employeeService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody EmployeeDto dto) {
        ResponseDto<?> response = employeeService.update(dto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        ResponseDto<?> response = employeeService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseDto<?> response = employeeService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/avgAmount")
    public ResponseEntity<?> avgAmount(@RequestParam(name = "date")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ResponseDto<?> response = this.employeeService.avgAmount(date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employeeSalaryVacation")
    public ResponseEntity<?> employeeSalaryVacation(@RequestParam(name = "date")
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ResponseDto<?> response = this.employeeService.employeeSalaryVacation(date);
        return ResponseEntity.ok(response);
    }
}
