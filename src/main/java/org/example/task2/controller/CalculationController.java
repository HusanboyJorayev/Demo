package org.example.task2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.task2.dto.CalculationDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.service.CalculationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "CALCULATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/calculation")
public class CalculationController {
    private final CalculationService calculationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CalculationDto dto) {
        ResponseDto<?> response = calculationService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        ResponseDto<?> response = calculationService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody CalculationDto dto) {
        ResponseDto<?> response = calculationService.update(dto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        ResponseDto<?> response = calculationService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseDto<?> response = calculationService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getNPinflRate")
    public ResponseEntity<?> getNPinflRate(@RequestParam(name = "date")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                           @RequestParam(name = "pinfl") String pinfl,
                                           @RequestParam(name = "rate") Double rate) {
        ResponseDto<?> response = this.calculationService.getNPinflRate(date, pinfl, rate);
        return ResponseEntity.ok(response);
    }
}
