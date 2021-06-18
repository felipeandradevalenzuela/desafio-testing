package com.example.CalculadoraMetrosCuadrados.service;

import com.example.CalculadoraMetrosCuadrados.dto.*;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseSquareFeetPerRoomDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseTotalSquareFeetDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseValueDTO;
import com.example.CalculadoraMetrosCuadrados.repository.IDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("CalculateService")
public class CalculateServiceImpl implements ICalculateService {

  @Autowired
  IDistrictRepository districtRepository;

  /**
   * Metodo que crea un objeto a retornar a partir de ciertos datos, y nos entrega
   * el tamaño total calculado
   * @param house
   * @return
   */
  @Override
  public HouseResponseTotalSquareFeetDTO calculateTotalSquareFeet(HouseDTO house) {
    return new HouseResponseTotalSquareFeetDTO( calculateRoomSquareFeet(house) );
  }

  /**
   * Metodo que calcula el valor total de la casa y nos entrega un DTO
   * @param house
   * @return
   * @throws NoSuchFieldException
   */
  @Override
  public HouseResponseValueDTO calculatePrice(HouseDTO house) throws NoSuchFieldException {
    var square = calculateRoomSquareFeet(house) * priceByDistrict( house.getDistrictName() );
    return new HouseResponseValueDTO( square );
  }

  /**
   * Metodo de paso para obtener la habitación mas grande
   * @param house
   * @return
   */
  @Override
  public RoomDTO getBiggestRoom(HouseDTO house) {
    return calculateBiggestRoom(house);
  }

  /**
   * Metodo para obtener los metros cuadrados de cada habitación
   * @param house
   * @return
   */
  @Override
  public HouseResponseSquareFeetPerRoomDTO calculateSquareFeetPerRoom(HouseDTO house) {
    var rooms = new ArrayList<RoomSquareFeetDTO>();

    for (RoomDTO room : house.getRooms()) {
      double squareFeet = getSquareFeet(room);
      rooms.add(new RoomSquareFeetDTO( room.getName(), squareFeet ));
    }
    return new HouseResponseSquareFeetPerRoomDTO( rooms );
  }

  /**
   * Metodo para obtener los metros cuadrados de una habitación
   * @param room
   * @return
   */
  private double getSquareFeet(RoomDTO room) {
    return room.getWidth() * room.getLength();
  }

  /**
   * Metodo para obtener los metros cuadrados de una casa
   * @param house
   * @return
   */
  private double calculateRoomSquareFeet(HouseDTO house) {
    double totalSquareFeet = 0;
    for (RoomDTO room : house.getRooms()) {
      totalSquareFeet += getSquareFeet(room);
    }

    return totalSquareFeet;
  }

  /**
   * Metodo para calcular la habitación mas grande
   * @param house
   * @return
   */
  private RoomDTO calculateBiggestRoom(HouseDTO house) {
    RoomDTO biggest = null;
    double maxRoom = 0;
    for (RoomDTO room : house.getRooms()) {
      double squareFeet = getSquareFeet(room);
      if (biggest == null || squareFeet > maxRoom){
        biggest = room;
        maxRoom = squareFeet;
      }
    }

    return biggest;
  }

  /**
   * Metodo para obtener el precio por barrio
   * @param districtName
   * @return
   * @throws NoSuchFieldException
   */
  private Double priceByDistrict(String districtName) throws NoSuchFieldException {
      var price = districtRepository.getDistrictByName(districtName).getPrice();
      return price;
  }


}
