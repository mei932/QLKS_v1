package com.example.hotel.service;

import com.example.hotel.domain.DichVu;
import com.example.hotel.domain.LoaiPhong;
import com.example.hotel.domain.Phong;
import com.example.hotel.dto.DichVuDto;
import com.example.hotel.dto.LoaiPhongDto;
import com.example.hotel.dto.PhongDto;
import com.example.hotel.repository.DichVuRepository;
import com.example.hotel.repository.LoaiPhongRepository;
import com.example.hotel.repository.PhongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class AdminService {
    private final DichVuRepository dichVuRepository;
    private final PhongRepository phongRepository;
    private final LoaiPhongRepository loaiPhongRepository;

    @Autowired
    public AdminService(DichVuRepository dichVuRepository, PhongRepository phongRepository, LoaiPhongRepository loaiPhongRepository) {
        this.dichVuRepository = dichVuRepository;
        this.phongRepository = phongRepository;
        this.loaiPhongRepository = loaiPhongRepository;
    }

    public List<DichVuDto> findAllDichVu() {
        return dichVuRepository.findAll()
                .stream()
                .map(mapper)
                .toList();
    }

    public void themDichVu(DichVuDto dichVuDto) {
        DichVu dichVu = DichVu.builder()
                .id(dichVuDto.getId())
                .tenDV(dichVuDto.getTenDV())
                .giaDV(dichVuDto.getGiaDV())
                .maDV(dichVuDto.getMaDV())
                .build();
        dichVuRepository.save(dichVu);
    }

    public void suaDichVu(DichVuDto dichVuDto) {
        dichVuRepository.findById(dichVuDto.getId())
                .ifPresent(dichVu -> {
                    dichVu.setTenDV(dichVuDto.getTenDV());
                    dichVu.setGiaDV(dichVuDto.getGiaDV());
                    dichVu.setMaDV(dichVuDto.getMaDV());
                    dichVuRepository.save(dichVu);
                });
    }

    public DichVuDto findDichVuById(long id) {
        return dichVuRepository.findById(id).map(mapper).orElse(null);
    }

    Function<DichVu, DichVuDto> mapper = dichVu -> DichVuDto.builder()
            .id(dichVu.getId())
            .maDV(dichVu.getMaDV())
            .tenDV(dichVu.getTenDV())
            .giaDV(dichVu.getGiaDV())
            .build();

    public void xoaDichVu(long id) {
        dichVuRepository.deleteById(id);
    }

    public List<PhongDto> findAllPhong() {
        return phongRepository.findAll()
                .stream()
                .map(phong -> {
                    PhongDto phongDto = PhongDto.builder()
                            .id(phong.getId())
                            .maPhong(phong.getMaPhong())
                            .tenPhong(phong.getTenPhong())
                            .giaPhong(phong.getGiaPhong())
                            .diaChi(phong.getDiaChi())
                            .imageUrl(phong.getImageUrl())
                            .tinhTrangPhong(phong.getTinhTrangPhong())
                            .build();
                    if (phong.getLoaiPhong() != null) {
                        phongDto.setLoaiPhong(phong.getLoaiPhong());
                    }
                    return phongDto;
                })
                .toList();
    }

    public void themPhong(PhongDto phongDto) {
        Phong phong = Phong.builder()
                .maPhong(phongDto.getMaPhong())
                .tenPhong(phongDto.getTenPhong())
                .giaPhong(phongDto.getGiaPhong())
                .diaChi(phongDto.getDiaChi())
                .imageUrl(phongDto.getImageUrl())
                .tinhTrangPhong(phongDto.getTinhTrangPhong())
                .build();
        if (phongDto.getLoaiPhong() != null) {
            phong.setLoaiPhong(phongDto.getLoaiPhong());
        }
        phongRepository.save(phong);
    }

    public List<LoaiPhongDto> findAllLoaiPhong() {
        return loaiPhongRepository.findAll()
                .stream()
                .map(loaiPhong -> LoaiPhongDto.builder()
                        .id(loaiPhong.getId())
                        .maLoai(loaiPhong.getMaLoai())
                        .tenLoaiPhong(loaiPhong.getTenLoaiPhong())
                        .build())
                .toList();
    }

    public PhongDto findPhongById(long id) {
        return phongRepository.findById(id)
                .map(phong -> {
                    PhongDto phongDto = PhongDto.builder()
                            .id(phong.getId())
                            .maPhong(phong.getMaPhong())
                            .tenPhong(phong.getTenPhong())
                            .giaPhong(phong.getGiaPhong())
                            .diaChi(phong.getDiaChi())
                            .imageUrl(phong.getImageUrl())
                            .tinhTrangPhong(phong.getTinhTrangPhong())
                            .build();
                    if (phong.getLoaiPhong() != null) {
                        phongDto.setLoaiPhong(new LoaiPhong(phong.getLoaiPhong().getId()));
                    }
                    return phongDto;
                })
                .orElse(null);
    }

    public void suaPhong(PhongDto phongDto) {
        phongRepository.findById(phongDto.getId()).ifPresent(phong -> {
            phong.setTenPhong(phongDto.getTenPhong());
            phong.setGiaPhong(phongDto.getGiaPhong());
            phong.setDiaChi(phongDto.getDiaChi());
            phong.setImageUrl(phongDto.getImageUrl());
            phong.setTinhTrangPhong(phongDto.getTinhTrangPhong());
            if (phongDto.getLoaiPhong() != null) {
                phong.setLoaiPhong(new LoaiPhong(phongDto.getLoaiPhong().getId()));
            }
            phongRepository.save(phong);
        });
    }

    public void xoaPhong(long id) {
        phongRepository.deleteById(id);
    }
}
