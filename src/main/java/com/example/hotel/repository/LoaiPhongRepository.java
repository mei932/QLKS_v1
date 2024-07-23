package com.example.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.hotel.domain.LoaiPhong;

public interface LoaiPhongRepository  extends JpaRepository<LoaiPhong, Long>{

    LoaiPhong findByMaLoai(String maLoai);

}
