package com.example.hotel.domain;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_phong")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Phong {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ma_phong", nullable = false, unique = true)
    private Long maPhong;

    @Column(name = "ten_phong", nullable = false)
    private String tenPhong;

    @Column(name = "gia_phong", nullable = false)
    private Double giaPhong;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "tinh_trang_phong", nullable = false)
    private String tinhTrangPhong;

    @ManyToOne
    @JoinColumn(name = "ma_loai")
    private LoaiPhong loaiPhong;

    @OneToMany(mappedBy = "phong", cascade = CascadeType.REMOVE)
    private Set<BookingRoom> bookingRooms;

    public Phong(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Phong{" +
                "id=" + id +
                ", maPhong=" + maPhong +
                ", tenPhong='" + tenPhong + '\'' +
                ", giaPhong=" + giaPhong +
                ", diaChi='" + diaChi + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tinhTrangPhong='" + tinhTrangPhong + '\'' +
                '}';
    }
}
