package com.example.hotel.domain;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tbl_booking_room")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRoom {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngay_den", nullable = false)
    private Date ngayDen;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngay_di")
    private Date ngayDi;

    @Column(name = "so_tien_coc", nullable = false)
    private Double soTienCoc;

    @Column(name="thanh_tien")
    private Double thanhTien;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "cccd", nullable = false)
    private String CCCD;

    @Column(name = "ho_tenkh", nullable = false)
    private String hoTenKH;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "gioi_tinh", nullable = false)
    private String gioiTinh;

    @Column(name = "sdt", nullable = false)
    private String SDT;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ma_phong", nullable = false)
    private Phong phong;

    @ManyToMany
    @JoinTable(
            name = "tbl_booking_room_dich_vu",
            joinColumns = @JoinColumn(name = "booking_room_id"),
            inverseJoinColumns = @JoinColumn(name = "dich_vu_id"))
    private Set<DichVu> dichVu;

    public BookingRoom(Long id) {
        this.id = id;
    }

    public String getListDV() {
        return this.getDichVu().stream()
                .map(DichVu::getTenDV)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "BookingRoom{" +
                "id=" + id +
                ", ngayDen=" + ngayDen +
                ", ngayDi=" + ngayDi +
                ", soTienCoc=" + soTienCoc +
                ", thanhTien=" + thanhTien +
                ", trangThai='" + trangThai + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", hoTenKH='" + hoTenKH + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", SDT='" + SDT + '\'' +
                '}';
    }
}
