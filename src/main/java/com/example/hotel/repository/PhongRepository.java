package com.example.hotel.repository;

import com.example.hotel.domain.Phong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Long> {

    Phong findByMaPhong(Long maPhong);
    
}
