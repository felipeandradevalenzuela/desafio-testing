package com.example.CalculadoraMetrosCuadrados.dto.response;

import com.example.CalculadoraMetrosCuadrados.dto.RoomSquareFeetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseResponseSquareFeetPerRoomDTO {
    private List<RoomSquareFeetDTO> roomsSquareFeet;
}
