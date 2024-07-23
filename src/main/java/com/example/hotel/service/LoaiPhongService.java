package com.example.hotel.service;

import java.util.List;
import com.example.hotel.dto.LoaiPhongDto;

public interface LoaiPhongService {
    List<LoaiPhongDto> getAllLoaiPhong();

    LoaiPhongDto getLoaiPhongById(Long id);

    LoaiPhongDto createOrUpdate(LoaiPhongDto loaiPhongDto);

    void deleteLP(Long id);

}
