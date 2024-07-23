package com.example.hotel.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hotel.domain.LoaiPhong;
import com.example.hotel.dto.LoaiPhongDto;
import com.example.hotel.repository.LoaiPhongRepository;
import com.example.hotel.service.LoaiPhongService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LoaiPhongServiceImpl implements LoaiPhongService {

    private final LoaiPhongRepository loaiPhongRepository;

    @Autowired
    public LoaiPhongServiceImpl(LoaiPhongRepository loaiPhongRepository) {
        this.loaiPhongRepository = loaiPhongRepository;
    }

    @Override
    public List<LoaiPhongDto> getAllLoaiPhong() {
        return loaiPhongRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LoaiPhongDto getLoaiPhongById(Long id) {
        return loaiPhongRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public LoaiPhongDto createOrUpdate(LoaiPhongDto loaiPhongDto) {
        LoaiPhong entity = new LoaiPhong();
        BeanUtils.copyProperties(loaiPhongDto, entity);

        // Kiểm tra xem có phải thao tác cập nhật hay thêm mới
        if (entity.getId() != null) {
            // Nếu có ID, kiểm tra và cập nhật loại phòng đã tồn tại
            Optional<LoaiPhong> existingEntityOptional = loaiPhongRepository.findById(entity.getId());
            if (existingEntityOptional.isPresent()) {
                LoaiPhong existingEntity = existingEntityOptional.get();
                BeanUtils.copyProperties(entity, existingEntity, "id"); // Copy các thuộc tính trừ id
                entity = existingEntity; // Cập nhật lại reference cho entity để save
            } else {
                throw new EntityNotFoundException("Không tìm thấy LoaiPhong có ID: " + entity.getId());
            }
        } else if (entity.getMaLoai() != null) {
            // Nếu không có ID nhưng có mã loại phòng (maLoai), kiểm tra và cập nhật loại phòng đã tồn tại
            LoaiPhong existingEntity = loaiPhongRepository.findByMaLoai(entity.getMaLoai());
            if (existingEntity != null) {
                BeanUtils.copyProperties(entity, existingEntity, "id"); // Copy các thuộc tính trừ id
                entity = existingEntity; // Cập nhật lại reference cho entity để save
            }
        }

        LoaiPhong savedEntity = loaiPhongRepository.save(entity);
        return convertToDto(savedEntity);
    }

    @Override
    public void deleteLP(Long id) {
        loaiPhongRepository.deleteById(id);
    }

    // @Override
    // public LoaiPhongDto getLoaiPhongByTenLP(String tenLP) {
    //     LoaiPhong entity = loaiPhongRepository.findByTenLoaiPhong(tenLP);
    //     if (entity == null) {
    //         throw new EntityNotFoundException("Không tìm thấy LoaiPhong có tên: " + tenLP);
    //     }
    //     return convertToDto(entity);
    // }

    private LoaiPhongDto convertToDto(LoaiPhong entity) {
        LoaiPhongDto dto = new LoaiPhongDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
