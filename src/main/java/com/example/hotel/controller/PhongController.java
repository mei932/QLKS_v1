package com.example.hotel.controller;

import com.example.hotel.dto.PhongDto;
import com.example.hotel.service.PhongService;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/phong")
@CrossOrigin(origins = "http://localhost:8082")
public class PhongController {

  private final PhongService phongService;

  @Autowired
  public PhongController(PhongService phongService) {
    this.phongService = phongService;
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/all")
  public ResponseEntity<List<PhongDto>> getAllPhong() {
    List<PhongDto> phongs = phongService.getAllPhong();
    return new ResponseEntity<>(phongs, HttpStatus.OK);
  }

  @PostMapping("/upload-image")
  public String  uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
    File fileOut = new File(file.getOriginalFilename());

    try (OutputStream os = new FileOutputStream(fileOut)) {
      os.write(file.getBytes());
    }
    return file.getOriginalFilename();
  }

  @GetMapping("/img")
 public ResponseEntity<Resource> getImg(@RequestParam String fileName ) throws FileNotFoundException
 {
  InputStreamResource resource = new InputStreamResource(new FileInputStream(fileName));

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
 }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<PhongDto> getPhongById(@PathVariable("id") Long id) {
    PhongDto phongDto = phongService.getPhongById(id);
    return new ResponseEntity<>(phongDto, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/createOrUpdate")
  public ResponseEntity<PhongDto> createOrUpdatePhong(@RequestBody PhongDto phongDto) {
    PhongDto savedPhong = phongService.createOrUpdatePhong(phongDto);
    return new ResponseEntity<>(savedPhong, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/createOrUpdate/{id}")
  public ResponseEntity<PhongDto> UpdatePhong(
      @PathVariable("id") Long id, @RequestBody PhongDto phongDto) {
    phongDto.setId(id);
    PhongDto savedPhong = phongService.createOrUpdatePhong(phongDto);
    return new ResponseEntity<>(savedPhong, HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deletePhong(@PathVariable("id") Long id) {
    phongService.deletePhong(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


  @PreAuthorize("isAuthenticated()")
  @PostMapping("/datPhong/{phongId}")
  public ResponseEntity<?> datPhong(@PathVariable(name = "phongId") long phongId) {
    return phongService.datPhong(phongId);
  }
}
