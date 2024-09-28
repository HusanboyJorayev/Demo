package org.example.task2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.task2.dto.OrganizationDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.service.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ORGANIZATION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody OrganizationDto dto) {
        ResponseDto<?> response = organizationService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        ResponseDto<?> response = organizationService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody OrganizationDto dto) {
        ResponseDto<?> response = organizationService.update(dto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        ResponseDto<?> response = organizationService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseDto<?> response = organizationService.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countOrganizationAndAmount")
    public ResponseEntity<?> countOrganizationAndAmount() {
        ResponseDto<?> response = this.organizationService.countOrganizationAndAmount();
        return ResponseEntity.ok(response);
    }
}
