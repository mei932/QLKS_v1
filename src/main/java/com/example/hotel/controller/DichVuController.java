package com.example.hotel.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.dto.DichVuDto;
import com.example.hotel.service.DichVuService;

@RestController
@RequestMapping("/api/dichvu")
public class DichVuController {
    private final DichVuService dichVuService;

    @Autowired
    public DichVuController(DichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/createOrUpdate/{id}")
    public ResponseEntity<DichVuDto> updateDichVu(@PathVariable("id") Long id, @RequestBody DichVuDto dichVuDto) {
        dichVuDto.setId(id); // Ensure ID from path variable is set in DTO
        return ResponseEntity.ok(dichVuService.createOrUpdateDichVu(dichVuDto));
    }

    @PreAuthorize(value = "hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<DichVuDto>> getAllDichVu() {
        return ResponseEntity.ok(dichVuService.getAllDichVu());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DichVuDto> getDichVuById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(dichVuService.getDichVuById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tenDV/{tenDV}")
    public ResponseEntity<DichVuDto> getDichVuByTenDV(@PathVariable("tenDV") String tenDV) {
        return ResponseEntity.ok(dichVuService.getDichVuByTenDV(tenDV));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<DichVuDto> createOrUpdateDichVu(@RequestBody DichVuDto dichVuDto) {
        DichVuDto savedDichVu = dichVuService.createOrUpdateDichVu(dichVuDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDichVu);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDichVu(@PathVariable("id") Long id) {
        dichVuService.deleteDichVu(id);
        return ResponseEntity.noContent().build();
    }

    private String loadResourceFile(String filePath) {
        try {
            Resource resource = new ClassPathResource(filePath);
            byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
            return new String(bytes);
        } catch (IOException e) {
            return "Error loading file: " + filePath;
        }
    }
}
