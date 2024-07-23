package com.example.hotel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DichVuDto {
    Long id;
    private String maDV;
    private String tenDV;
    private Double giaDV;
}
