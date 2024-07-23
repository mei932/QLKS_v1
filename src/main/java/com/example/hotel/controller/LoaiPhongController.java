package com.example.hotel.controller;

import com.example.hotel.dto.LoaiPhongDto;
import com.example.hotel.service.LoaiPhongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/loaiphong")
@CrossOrigin(origins = "http://localhost:8082")
public class LoaiPhongController {

    private final LoaiPhongService loaiPhongService;

    @Autowired
    public LoaiPhongController(LoaiPhongService loaiPhongService) {
        this.loaiPhongService = loaiPhongService;
    }

    @GetMapping("/loaiphong.html")
    public ResponseEntity<String> viewLoaiPhongPage() {
        String content = loadResourceFile("static/loaiphong.html");
        return ResponseEntity.ok(content);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/all")
    public ResponseEntity<List<LoaiPhongDto>> getAllLoaiPhong() {
        return ResponseEntity.ok(loaiPhongService.getAllLoaiPhong());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<LoaiPhongDto> getLoaiPhongById(@PathVariable Long id) {
        LoaiPhongDto loaiPhongDto = loaiPhongService.getLoaiPhongById(id);
        if (loaiPhongDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loaiPhongDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<LoaiPhongDto> createOrUpdateLoaiPhong(@RequestBody LoaiPhongDto loaiPhongDto) {
        LoaiPhongDto savedLoaiPhong = loaiPhongService.createOrUpdate(loaiPhongDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoaiPhong);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/createOrUpdate/{id}")
    public ResponseEntity<LoaiPhongDto> updateLoaiPhong(@PathVariable("id") Long id, @RequestBody LoaiPhongDto loaiPhongDto) {
        loaiPhongDto.setId(id);
        LoaiPhongDto savedLoaiPhong = loaiPhongService.createOrUpdate(loaiPhongDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLoaiPhong);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLoaiPhong(@PathVariable Long id) {
        loaiPhongService.deleteLP(id);
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
