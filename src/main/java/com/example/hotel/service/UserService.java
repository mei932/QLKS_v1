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
  public UserService(
      PhongRepository phongRepository,
      UserRepository userRepository,
      BookingRoomRepository bookingRoomRepository,
      DichVuRepository dichVuRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder) {
    this.phongRepository = phongRepository;
    this.userRepository = userRepository;
    this.bookingRoomRepository = bookingRoomRepository;
    this.dichVuRepository = dichVuRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<PhongDto> findAllRooms() {
    return phongRepository.findAll().stream()
        .map(
            phong ->
                PhongDto.builder()
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
    return phongRepository
        .findById(id)
        .map(
            phong ->
                PhongDto.builder()
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

  public boolean saveUser(UserDto user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      return false;
    }

    final Role role = roleRepository.findById(2L).get();
    userRepository.save(
        User.builder()
            .username(user.getUsername())
            .password(passwordEncoder.encode(user.getPassword()))
            .role(role)
            .build());
    return true;
  }

  public void datPhong(BookingRoom bookingRoom) {
    Double giaPhong = bookingRoom.getPhong().getGiaPhong();
    long l = bookingRoom.getNgayDi().getTime() - bookingRoom.getNgayDen().getTime();
    double day = Math.ceil((double) l / (1000 * 60 * 60 * 24));

    Double reduce = bookingRoom.getDichVu().stream().map(DichVu::getGiaDV).reduce(0D, Double::sum);

    bookingRoom.setThanhTien(giaPhong * day + reduce);

    bookingRoomRepository.save(bookingRoom);
  }

  public List<DichVuDto> findAllDichVu() {
    return dichVuRepository.findAll().stream()
        .map(
            dichVu ->
                DichVuDto.builder()
                    .id(dichVu.getId())
                    .maDV(dichVu.getMaDV())
                    .tenDV(dichVu.getTenDV())
                    .giaDV(dichVu.getGiaDV())
                    .build())
        .toList();
  }
}
