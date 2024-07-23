package com.example.hotel.service;

import java.util.List;

import com.example.hotel.dto.DichVuDto;

public interface DichVuService {
     List<DichVuDto> getAllDichVu();

    DichVuDto getDichVuById(Long id);

    DichVuDto getDichVuByTenDV(String tenDV); // New method

    DichVuDto createOrUpdateDichVu(DichVuDto dichVuDto);

    void deleteDichVu(Long id);
}
