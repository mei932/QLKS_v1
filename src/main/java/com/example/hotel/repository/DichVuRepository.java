package com.example.hotel.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.domain.DichVu;


public interface DichVuRepository extends JpaRepository<DichVu, Long> {

    // Query method để tìm DichVu theo tenDV
    DichVu findByTenDV(String tenDV);
    DichVu findByMaDV(String maDV);

}


