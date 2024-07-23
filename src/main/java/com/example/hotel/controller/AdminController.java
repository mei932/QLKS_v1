package com.example.hotel.controller;

import com.example.hotel.dto.DichVuDto;
import com.example.hotel.dto.LoaiPhongDto;
import com.example.hotel.dto.PhongDto;
import com.example.hotel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminHome() {
        return "admin/admin";  //  templates/admin.html
    }

    @GetMapping("/dichvu")
    public String showDichVuPage(Model model) {
        model.addAttribute("listDichVu", adminService.findAllDichVu());
        return "admin/dichvu";
    }

    @GetMapping("/themdichvu")
    public String themDichVuPage(Model model) {
        model.addAttribute("dichVu", new DichVuDto());
        return "admin/themdichvu";
    }

    @PostMapping("/onthemdichvu")
    public String onThemDichVuPage(@ModelAttribute DichVuDto dichVu) {
        adminService.themDichVu(dichVu);
        return "redirect:/admin/dichvu";
    }

    @GetMapping("/suadichvu/{id}")
    public String suaDichVuPage(Model model, @PathVariable(name = "id") long id) {
        DichVuDto dichVuById = adminService.findDichVuById(id);
        model.addAttribute("dichVu", dichVuById);
        return "admin/suadichvu";
    }

    @PostMapping("/onsuadichvu/{id}")
    public String onSuaDichVu(@ModelAttribute DichVuDto dichVu, @PathVariable(name = "id") long id) {
        dichVu.setId(id);
        adminService.suaDichVu(dichVu);
        return "redirect:/admin/dichvu";
    }

    @GetMapping("/onxoadichvu/{id}")
    public String onXoaDichVu(@PathVariable(name = "id") long id) {
        adminService.xoaDichVu(id);
        return "redirect:/admin/dichvu";
    }

    @GetMapping("/phong")
    public String showPhongPage(Model model) {
        List<PhongDto> allPhong = adminService.findAllPhong();
        model.addAttribute("allPhong", allPhong);
        return "/admin/phong";
    }


    @GetMapping("/themphong")
    public String themPhongPage(Model model) {
        List<LoaiPhongDto> list = adminService.findAllLoaiPhong();
        model.addAttribute("listLoaiPhong", list);
        model.addAttribute("phong", new PhongDto());
        return "admin/themphong";
    }

    @PostMapping("/onthemphong")
    public String onThemPhongPage(@ModelAttribute PhongDto phongDto) {
        adminService.themPhong(phongDto);
        return "redirect:/admin/phong";
    }

    @GetMapping("/suaphong/{id}")
    public String suaPhongPage(Model model, @PathVariable(name = "id") long id) {
        PhongDto phong = adminService.findPhongById(id);
        List<LoaiPhongDto> list = adminService.findAllLoaiPhong();
        model.addAttribute("phong", phong);
        model.addAttribute("listLoaiPhong", list);
        return "admin/suaphong";
    }

    @PostMapping("/onsuaphong/{id}")
    public String onSuaPhong(@ModelAttribute PhongDto phongDto, @PathVariable(name = "id") long id) {
        phongDto.setId(id);
        adminService.suaPhong(phongDto);
        return "redirect:/admin/phong";
    }

    @GetMapping("/onxoaphong/{id}")
    public String onXoaPhong(@PathVariable(name = "id") long id) {
        adminService.xoaPhong(id);
        return "redirect:/admin/phong";
    }
}
