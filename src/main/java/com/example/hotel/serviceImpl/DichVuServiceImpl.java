package com.example.hotel.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel.domain.DichVu;
import com.example.hotel.dto.DichVuDto;
import com.example.hotel.repository.DichVuRepository;
import com.example.hotel.service.DichVuService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DichVuServiceImpl implements DichVuService {

    private final DichVuRepository dichVuRepository;

    @Autowired
    public DichVuServiceImpl(DichVuRepository dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }

    @Override
    public List<DichVuDto> getAllDichVu() {
        return dichVuRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DichVuDto getDichVuById(Long id) {
        return dichVuRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }


    @Override
    public DichVuDto createOrUpdateDichVu(DichVuDto dichVuDto) {
        DichVu dichVu = new DichVu();
        BeanUtils.copyProperties(dichVuDto, dichVu);

        // Kiểm tra xem có phải thao tác cập nhật hay thêm mới
        if (dichVu.getId() != null) {
            // Nếu có ID, kiểm tra và cập nhật dịch vụ đã tồn tại
            Optional<DichVu> existingDichVuOptional = dichVuRepository.findById(dichVu.getId());
            if (existingDichVuOptional.isPresent()) {
                DichVu existingDichVu = existingDichVuOptional.get();
                BeanUtils.copyProperties(dichVu, existingDichVu, "id"); // Copy các thuộc tính trừ id
                dichVu = existingDichVu; // Cập nhật lại reference cho dichVu để save
            } else {
                throw new EntityNotFoundException("Không tìm thấy DichVu có ID: " + dichVu.getId());
            }
        } else if (dichVu.getMaDV() != null) {
            // Nếu không có ID nhưng có mã dịch vụ (maDV), kiểm tra và cập nhật dịch vụ đã tồn tại
            DichVu existingDichVu = dichVuRepository.findByMaDV(dichVu.getMaDV());
            if (existingDichVu != null) {
                BeanUtils.copyProperties(dichVu, existingDichVu, "id"); // Copy các thuộc tính trừ id
                dichVu = existingDichVu; // Cập nhật lại reference cho dichVu để save
            }
        }

        DichVu savedDichVu = dichVuRepository.save(dichVu);
        return convertToDto(savedDichVu);
    }


    @Override
    public void deleteDichVu(Long id) {
        dichVuRepository.deleteById(id);
    }

    private DichVuDto convertToDto(DichVu dichVu) {
        DichVuDto dichVuDto = new DichVuDto();
        BeanUtils.copyProperties(dichVu, dichVuDto);
        return dichVuDto;
    }

    @Override
    public DichVuDto getDichVuByTenDV(String tenDV) {
        DichVu dichVu = dichVuRepository.findByTenDV(tenDV);
        if (dichVu == null) {
            throw new EntityNotFoundException("Không tìm thấy DichVu náo có tên: " + tenDV);
        }

        return convertToDto(dichVu);
    }

}
