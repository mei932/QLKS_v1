package com.example.hotel.service;

import com.example.hotel.domain.BookingRoom;
import com.example.hotel.domain.DichVu;
import com.example.hotel.domain.Phong;
import com.example.hotel.domain.Role;
import com.example.hotel.domain.User;
import com.example.hotel.dto.DichVuDto;
import com.example.hotel.dto.PhongDto;
import com.example.hotel.dto.UserDto;
import com.example.hotel.repository.BookingRoomRepository;
import com.example.hotel.repository.DichVuRepository;
import com.example.hotel.repository.PhongRepository;
import com.example.hotel.repository.RoleRepository;
import com.example.hotel.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PhongRepository phongRepository;
    private final UserRepository userRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final DichVuRepository dichVuRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PhongRepository phongRepository, UserRepository userRepository, BookingRoomRepository bookingRoomRepository, DichVuRepository dichVuRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.phongRepository = phongRepository;
        this.userRepository = userRepository;
        this.bookingRoomRepository = bookingRoomRepository;
        this.dichVuRepository = dichVuRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PhongDto> findAllRooms() {
        return phongRepository.findAll()
                .stream()
                .map(phong -> PhongDto.builder()
                        .id(phong.getId())
                        .maPhong(phong.getMaPhong())
                        .tenPhong(phong.getTenPhong())
                        .giaPhong(phong.getGiaPhong())
                        .diaChi(phong.getDiaChi())
                        .imageUrl(phong.getImageUrl())
                        .tinhTrangPhong(phong.getTinhTrangPhong())
                        .loaiPhong(phong.getLoaiPhong())
                        .build())
                .toList();
    }

    public PhongDto findPhongDtoById(Long id) {
        return phongRepository.findById(id)
                .map(phong -> PhongDto.builder()
                        .id(phong.getId())
                        .maPhong(phong.getMaPhong())
                        .tenPhong(phong.getTenPhong())
                        .giaPhong(phong.getGiaPhong())
                        .diaChi(phong.getDiaChi())
                        .imageUrl(phong.getImageUrl())
                        .tinhTrangPhong(phong.getTinhTrangPhong())
                        .loaiPhong(phong.getLoaiPhong())
                        .build())
                .orElse(null);
    }

    public Phong findPhongById(Long id) {
        return phongRepository.findById(id).orElse(null);
    }

    public User findUserByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    public boolean saveUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return false;
        }

        Role role = roleRepository.findById(2L).orElse(null);
        if (role == null) {
            return false;
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return true;
    }

    public void datPhong(BookingRoom bookingRoom) {
        Phong phong = bookingRoom.getPhong();
        phong.setTinhTrangPhong("Đã đặt");

        phongRepository.save(phong);

        Double giaPhong = phong.getGiaPhong();
        long l = bookingRoom.getNgayDi().getTime() - bookingRoom.getNgayDen().getTime();
        double day = Math.ceil((double) l / (1000 * 60 * 60 * 24));

        Double reduce = bookingRoom.getDichVu()
                .stream()
                .map(DichVu::getGiaDV)
                .reduce(0D, Double::sum);

        bookingRoom.setThanhTien(giaPhong * day + reduce);
        bookingRoom.setTrangThai("Đã được đặt");

        bookingRoomRepository.save(bookingRoom);
    }

    public List<DichVuDto> findAllDichVu() {
        return dichVuRepository.findAll()
                .stream()
                .map(dichVu -> DichVuDto.builder()
                        .id(dichVu.getId())
                        .maDV(dichVu.getMaDV())
                        .tenDV(dichVu.getTenDV())
                        .giaDV(dichVu.getGiaDV())
                        .build())
                .toList();
    }

    public List<BookingRoom> findRoomsByUserId(Long userId) {
        return bookingRoomRepository.findByUserId(userId);
    }

    public void huyPhong(long id) {
        bookingRoomRepository.findById(id).ifPresent(bookingRoom -> {
            Phong phong = bookingRoom.getPhong();
            phong.setTinhTrangPhong("Trống");
            phongRepository.save(phong);

            bookingRoomRepository.deleteById(id);
        });
    }
}
