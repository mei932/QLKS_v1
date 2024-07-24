package com.example.hotel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotel.domain.Phong;
import com.example.hotel.dto.PhongDto;
import com.example.hotel.repository.PhongRepository;
import com.example.hotel.repository.UserRepository;

@Service
public class PhongService {

  private final PhongRepository phongRepository;
  private final UserRepository userRepository;

  private static final String path = Paths.get("").toAbsolutePath() + "/src/main/resources/static/";

  @Autowired
  public PhongService(PhongRepository phongRepository, UserRepository userRepository) {
    this.phongRepository = phongRepository;
    this.userRepository = userRepository;
  }

  public List<PhongDto> getAllPhong() {
    List<Phong> entities = phongRepository.findAll();
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  public PhongDto getPhongById(Long id) {
    Optional<Phong> optionalEntity = phongRepository.findById(id);
    return optionalEntity.map(this::convertToDto).orElse(null);
  }

  public PhongDto createOrUpdatePhong(PhongDto phongDto) {
    Phong entity = new Phong();
    BeanUtils.copyProperties(phongDto, entity);

    // Kiểm tra xem có phải thao tác cập nhật hay thêm mới
    if (entity.getId() != null) {
      // Nếu có ID, kiểm tra và cập nhật phòng đã tồn tại
      Optional<Phong> existingEntityOptional = phongRepository.findById(entity.getId());
      if (existingEntityOptional.isPresent()) {
        Phong existingEntity = existingEntityOptional.get();
        BeanUtils.copyProperties(entity, existingEntity, "id"); // Copy các thuộc tính trừ id
        entity = existingEntity; // Cập nhật lại reference cho entity để save
      } else {
        throw new EntityNotFoundException("Không tìm thấy Phong có ID: " + entity.getId());
      }
    } else if (entity.getMaPhong() != null) {
      // Nếu không có ID nhưng có mã phòng (maPhong), kiểm tra và cập nhật phòng đã tồn tại
      Phong existingEntity = phongRepository.findByMaPhong(entity.getMaPhong());
      if (existingEntity != null) {
        BeanUtils.copyProperties(entity, existingEntity, "id"); // Copy các thuộc tính trừ id
        entity = existingEntity; // Cập nhật lại reference cho entity để save
      }
    }

    Phong savedEntity = phongRepository.save(entity);
    return convertToDto(savedEntity);
  }

  public void deletePhong(Long id) {
    phongRepository.deleteById(id);
  }

  public ResponseEntity<?> datPhong(long phongId) {
    return ResponseEntity.ok().build();
  }

  private PhongDto convertToDto(Phong entity) {
    PhongDto dto = new PhongDto();
    BeanUtils.copyProperties(entity, dto);
    return dto;
  }

 public ResponseEntity<?> getImage(String url) {
  String filePath = path + "img/" + url;
  Path path1 = Path.of(filePath);
  if (!Files.exists(path1)) {
      return ResponseEntity.notFound().build();
  }

  try {
      byte[] bytes = Files.readAllBytes(path1);
      Resource resource = new ByteArrayResource(bytes);
      String contentType = Files.probeContentType(path1);
      return ResponseEntity.ok()
              .header("Content-Type", contentType)
              .body(resource);
  } catch (IOException e) {
      return ResponseEntity.internalServerError().build();
  }
}
}
