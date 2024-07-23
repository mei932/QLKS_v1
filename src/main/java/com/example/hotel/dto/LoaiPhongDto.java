package com.example.hotel.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoaiPhongDto {
    private Long id;
    private String maLoai;
    private String tenLoaiPhong;
}
