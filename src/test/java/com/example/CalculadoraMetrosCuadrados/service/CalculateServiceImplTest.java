package com.example.CalculadoraMetrosCuadrados.service;

import com.example.CalculadoraMetrosCuadrados.dto.HouseDTO;
import com.example.CalculadoraMetrosCuadrados.dto.RoomDTO;
import com.example.CalculadoraMetrosCuadrados.dto.RoomSquareFeetDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseSquareFeetPerRoomDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseTotalSquareFeetDTO;
import com.example.CalculadoraMetrosCuadrados.dto.response.HouseResponseValueDTO;
import com.example.CalculadoraMetrosCuadrados.entities.District;
import com.example.CalculadoraMetrosCuadrados.entities.Room;
import com.example.CalculadoraMetrosCuadrados.repository.DistrictRepository;
import com.example.CalculadoraMetrosCuadrados.repository.IDistrictRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateServiceImplTest {

    @Mock
    DistrictRepository districtRepository;
    @InjectMocks
    CalculateServiceImpl calculateServiceImpl;

    private static HouseDTO houseCorrect;
    private static HouseDTO houseIncorrect;
    private static List<RoomDTO> roomList = new ArrayList<>();
    private static List<District> districts = new ArrayList<>();
    District expectedDistrict = new District("Maipú",2000.0);

    @BeforeAll
    static void beforeAll(){
        roomList.add(new RoomDTO("Habitación",20,30));
        roomList.add(new RoomDTO("Baño",2,4));
        roomList.add(new RoomDTO("Comedor",5,10));
        houseCorrect = new HouseDTO(
                "Casa correcta",
                "Maipú",
                1000,
                roomList);
        houseIncorrect = new HouseDTO(
                "casa incorrecta",
                "No existe",
                6000,
                new ArrayList<RoomDTO>());
        districts.add(new District("Maipú",2000.0));
        districts.add(new District("Cerrillos",1500.0));
        districts.add(new District("Peñaflor",1000.0));
    }

    @AfterAll
    static void afterAll(){
        houseCorrect = null;
        houseIncorrect = null;
        roomList = null;
        districts = null;
    }

    @Test
    @DisplayName("totalSquareFeet -> calculo correcto")
    void testcalculateTotalSquareFeetCorrectCalculate() {
        HouseResponseTotalSquareFeetDTO expected = new HouseResponseTotalSquareFeetDTO(658.0);
        var actual = calculateServiceImpl.calculateTotalSquareFeet(houseCorrect);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("totalSquareFeet -> calculo incorrecto")
    void testCalculateTotalSquareFeetIncorrectCalculate() {
        HouseResponseTotalSquareFeetDTO expected = new HouseResponseTotalSquareFeetDTO(0.0);
        var actual = calculateServiceImpl.calculateTotalSquareFeet(houseIncorrect);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("calculatePrice -> calculo correcto")
    void testCalculatePriceCorrectCalculate() throws NoSuchFieldException {
        ReflectionTestUtils.setField(districtRepository,"districts", districts);
        when(districtRepository.getDistrictByName(any())).thenReturn(expectedDistrict);
        HouseResponseValueDTO expected = new HouseResponseValueDTO(1316000.0);
        var actual = calculateServiceImpl.calculatePrice(houseCorrect);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("calculatePrice -> calculo incorrecto")
    void testCalculatePriceIncorrectThrowsException() throws NoSuchFieldException {
        when(districtRepository.getDistrictByName(any())).thenThrow(NoSuchFieldException.class);
        assertThrows(NoSuchFieldException.class, () -> calculateServiceImpl.calculatePrice(houseIncorrect), "Error en la busqueda del barrio");
    }

    @Test
    @DisplayName("getBiggestRoom -> calculo correcto")
    void testGetBiggestRoomCorrectCalculate() {
        RoomDTO expected = new RoomDTO("Habitación",20.0,30.0);
        var actual = calculateServiceImpl.getBiggestRoom(houseCorrect);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("getBiggestRoom -> calculo incorrecto")
    void testGetBiggestRoomIncorrectCalculate() {
        houseIncorrect.setRooms(new ArrayList<RoomDTO>());
        var actual = calculateServiceImpl.getBiggestRoom(houseIncorrect);
        assertNull(actual);
    }

    @Test
    @DisplayName("calculateSquareFeetPerRoom -> calculo correcto")
    void testCalculateSquareFeetPerRoomCorrectCalculate() {
        HouseResponseSquareFeetPerRoomDTO expected = new HouseResponseSquareFeetPerRoomDTO();
        List<RoomSquareFeetDTO> tmp = new ArrayList<>();
        tmp.add(new RoomSquareFeetDTO("Habitación",600.0));
        tmp.add(new RoomSquareFeetDTO("Baño",8.0));
        tmp.add(new RoomSquareFeetDTO("Comedor",50.0));
        expected.setRoomsSquareFeet(tmp);
        var actual = calculateServiceImpl.calculateSquareFeetPerRoom(houseCorrect);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("calculateSquareFeetPerRoom -> calculo incorrecto")
    void testCalculateSquareFeetPerRoomIncorrectCalculate() {
        var expected = new HouseResponseSquareFeetPerRoomDTO(new ArrayList<>());
        var actual = calculateServiceImpl.calculateSquareFeetPerRoom(houseIncorrect);
        assertEquals(expected,actual);
    }
}