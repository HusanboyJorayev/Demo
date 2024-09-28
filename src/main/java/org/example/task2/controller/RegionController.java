package org.example.task2.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.task2.dto.RegionDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REGION")
@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody RegionDto dto) {
        ResponseDto<?> response = regionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestParam("id") Integer id) {
        ResponseDto<?> response = regionService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody RegionDto dto) {
        ResponseDto<?> response = regionService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        ResponseDto<?> response = regionService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        ResponseDto<?> response = regionService.getAll();
        return ResponseEntity.ok(response);
    }
}
