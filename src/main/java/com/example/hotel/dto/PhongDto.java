package com.example.hotel.dto;

import com.example.hotel.domain.LoaiPhong;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongDto {
    private Long id;
    private Long maPhong;
    private String tenPhong;
    private Double giaPhong;
    private String diaChi;
    private String imageUrl;
    private String tinhTrangPhong;
    private LoaiPhong loaiPhong;

    @Override
    public String toString() {
        return "PhongDto{" +
                "tinhTrangPhong='" + tinhTrangPhong + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", giaPhong=" + giaPhong +
                ", tenPhong='" + tenPhong + '\'' +
                ", maPhong=" + maPhong +
                ", id=" + id +
                '}';
    }
}
