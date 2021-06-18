package com.example.CalculadoraMetrosCuadrados.controller;

import com.example.CalculadoraMetrosCuadrados.dto.HouseDTO;
import com.example.CalculadoraMetrosCuadrados.dto.RoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculateRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static HouseDTO houseCorrect;
    private HouseDTO houseIncorrect;
    private static List<RoomDTO> roomList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        roomList.add(new RoomDTO("Habitación", 20, 30));
        roomList.add(new RoomDTO("Baño", 2, 4));
        roomList.add(new RoomDTO("Comedor", 5, 10));
        houseCorrect = new HouseDTO(
                "Casa correcta",
                "Belgrano",
                1000,
                roomList);

    }

    @AfterAll
    static void afterAll() {
        roomList = null;
        houseCorrect = null;
    }

    @BeforeEach
    void setUp() {
        houseIncorrect = new HouseDTO(
                "Casa incorrecta",
                "Belgrano",
                4000,
                roomList);
    }

    @AfterEach
    void tearDown() {
        houseIncorrect = null;
    }

    @Test
    @DisplayName("calculateTotalSquareFeet -> Valores correctos")
    void testCalculateTotalSquareFeetSimpleValuesCalculated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalSquareFeet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseCorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.squareFeet").value(658.0))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("calculateTotalSquareFeet -> Valores incorrectos")
    void testCalculateTotalSquareFeetInvalidValuesError() throws Exception {
        List<RoomDTO> tmp = new ArrayList<>();
        tmp.add(new RoomDTO("Pieza", 40, 40));
        houseIncorrect.setRooms(tmp);
        mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalSquareFeet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseIncorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }

    @Test
    @DisplayName("calcutalePrice -> Valores correctos")
    void testCalculatePriceSimpleValueCalculated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseCorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(658000.0))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("calcutalePrice -> Valores incorrectos")
    void testCalculatePriceInvalidValueError() throws Exception {
        houseIncorrect.setPropName("invalido");
        mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseIncorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }

    @Test
    @DisplayName("getBiggestRoom -> Valores correctos")
    void testGetBiggestRoomSimpleValueSearch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/getBiggestRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseCorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.environment_name").value("Habitación"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("getBiggestRoomInvalidValue -> Valores incorrectos")
    void testGetBiggestRoomInvalidValueError() throws Exception {
        List<RoomDTO> tmp = new ArrayList<>();
        tmp.add(new RoomDTO("pieza", 40, 40));
        houseIncorrect.setRooms(tmp);
        mockMvc.perform(MockMvcRequestBuilders.post("/getBiggestRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseIncorrect)))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                .andReturn();
    }

    @Test
    @DisplayName("calculateSquareFeetPerRoom -> Valores correctos")
    void testCalculateSquareFeetPerRoomSimpleValuesCalculated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/calculateSquareFeetPerRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseCorrect)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("calculateSquareFeetPerRoom -> Valores incorrectos")
    void testCalculateSquareFeetPerRoomInvalidValuesError() throws Exception {
        houseIncorrect.setDistrictPrice(5000.0);
        mockMvc.perform(MockMvcRequestBuilders.post("/calculateSquareFeetPerRoom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseIncorrect)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("El precio máximo permitido por metro cuadrado no puede superar los 4000 $US."))
                .andExpect(status().isNotAcceptable())
                .andReturn();
    }

    @Test
    @DisplayName("error -> NoSuchFieldException")
    void testCalculatePriceIncorrectValueThrowsError() throws Exception {
        houseIncorrect.setDistrictName("No existe");
        mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(houseIncorrect)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("exception -> HttpMessageNotReadableException")
    void testCalculatePriceIncorrectValueThrowsException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
}