package com.example.hotel.controller;

import com.example.hotel.domain.BookingRoom;
import com.example.hotel.domain.Phong;
import com.example.hotel.domain.User;
import com.example.hotel.dto.DichVuDto;
import com.example.hotel.dto.PhongDto;
import com.example.hotel.dto.UserDto;
import com.example.hotel.service.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String userIndex(Model model) {
    List<PhongDto> allRooms = userService.findAllRooms();
    model.addAttribute("allPhong", allRooms);
    return "user/index";
  }

  @GetMapping("/room-details/{id}")
  public String userRoomDetails(Model model, @PathVariable(name = "id") Long id) {
    PhongDto phong = userService.findPhongDtoById(id);
    model.addAttribute("phong", phong);
    return "user/room-details";
  }

  @GetMapping("/dat-phong/{id}")
  public String datPhongPage(Model model, @PathVariable(name = "id") Long id, Principal principal) {
    Phong phong = userService.findPhongById(id);
    if (phong == null) {
      return "redirect:/users/room-details/" + id;
    } else if (!phong.getTinhTrangPhong().equals("Trống")) {
      return "redirect:/users/room-details/" + id;
    }

    String name = principal.getName();
    User user = userService.findUserByUsername(name);
    if (user == null) {
      return "redirect:/users/room-details/" + id;
    }

    List<DichVuDto> allDichVu = userService.findAllDichVu();

    BookingRoom bookingRoom = new BookingRoom();
    bookingRoom.setUser(user);
    bookingRoom.setPhong(phong);

    model.addAttribute("booking_room", bookingRoom);
    model.addAttribute("allDichVu", allDichVu);
    return "user/dat-phong";
  }

  @PostMapping("/register")
  @ResponseBody
  public String registerUser(@RequestBody UserDto userDto) {
    final boolean result = userService.saveUser(userDto);
    return result ? "User registered successfully!" : "Username already exists!";
  }

  @PostMapping("/on-dat-phong")
  public String onDatPhong(@ModelAttribute BookingRoom bookingRoom) {
    userService.datPhong(bookingRoom);
    return "redirect:/users";
  }
}
