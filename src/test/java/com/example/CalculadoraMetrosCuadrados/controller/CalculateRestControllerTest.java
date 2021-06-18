package com.example.CalculadoraMetrosCuadrados.controller;

import com.example.CalculadoraMetrosCuadrados.dto.HouseDTO;
import com.example.CalculadoraMetrosCuadrados.dto.RoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class CalculateRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static HouseDTO houseCorrect;
    private static HouseDTO houseIncorrect;
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
        houseIncorrect = new HouseDTO(
                "Casa incorrecta",
                "No existe",
                6000,
                roomList);
    }

    @AfterAll
    static void afterAll() {
        roomList = null;
        houseIncorrect = null;
        houseCorrect = null;
    }

    @Test
    void testCalculateTotalSquareFeetSimpleValuesCalculated() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalSquareFeet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseCorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.squareFeet").value(658.0))
                        .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testCalculateTotalSquareFeetInvalidValuesError() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculateTotalSquareFeet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseIncorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                        .andReturn();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testCalculatePriceSimpleValueCalculated() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseCorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(658000.0))
                        .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testCalculatePriceInvalidValueError() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseIncorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                        .andReturn();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetBiggestRoomSimpleValueSearch() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/getBiggestRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseCorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.environment_name").value("Habitación"))
                        .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testGetBiggestRoomInvalidValueError() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/getBiggestRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseIncorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                        .andReturn();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testCalculateSquareFeetPerRoomSimpleValuesCalculated() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculateSquareFeetPerRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseCorrect)))
                        .andDo(print())
                        .andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void testCalculateSquareFeetPerRoomInvalidValuesError() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/calculateSquareFeetPerRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(houseIncorrect)))
                        .andDo(print())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Informacion no valida"))
                        .andReturn();
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), mvcResult.getResponse().getStatus());
    }
}