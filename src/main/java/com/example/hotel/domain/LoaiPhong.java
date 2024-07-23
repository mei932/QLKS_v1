package com.example.hotel.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tbl_loai_phong")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoaiPhong {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_loai", nullable = false, unique = true)
    private String maLoai;

    @Column(name = "ten_loai_phong", nullable = false)
    private String tenLoaiPhong;

    @OneToMany(mappedBy = "loaiPhong")
    private Set<Phong> phongs;

    public LoaiPhong(Long id) {
        this.id = id;
    }
}
