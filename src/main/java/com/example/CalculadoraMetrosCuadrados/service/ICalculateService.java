package com.example.CalculadoraMetrosCuadrados.service;

import com.example.CalculadoraMetrosCuadrados.dto.*;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseSquareFeetPerRoomDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseTotalSquareFeetDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseValueDTO;

public interface ICalculateService {

    HouseResponseTotalSquareFeetDTO calculateTotalSquareFeet(HouseDTO house);

    HouseResponseValueDTO calculatePrice(HouseDTO house) throws NoSuchFieldException;

    RoomDTO getBiggestRoom(HouseDTO house);

    HouseResponseSquareFeetPerRoomDTO calculateSquareFeetPerRoom(HouseDTO house);
}
