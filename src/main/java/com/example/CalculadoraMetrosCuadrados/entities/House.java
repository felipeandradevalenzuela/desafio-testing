package com.example.CalculadoraMetrosCuadrados.entities;

import com.example.CalculadoraMetrosCuadrados.dto.RoomDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private String propName;
    private String districtName;
    private double districtPrice;
    private List<RoomDTO> rooms;

}
