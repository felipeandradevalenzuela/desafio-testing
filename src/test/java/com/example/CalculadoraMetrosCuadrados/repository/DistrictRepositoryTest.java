package com.example.CalculadoraMetrosCuadrados.repository;

import com.example.CalculadoraMetrosCuadrados.entities.District;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DistrictRepositoryTest {

    private DistrictRepository districtRepository = new DistrictRepository();

    private static String districtNameCorrect;
    private static String districtNameIncorrect;
    private static List<District> districts = new ArrayList<>();
    District expected = new District("Maipú", 2000.0);

    @BeforeAll
    static void beforeAll() {
        districtNameCorrect = "Maipú";
        districtNameIncorrect = "No existe";
        districts.add(new District("Maipú", 2000.0));
        districts.add(new District("Cerrillos", 1500.0));
        districts.add(new District("Peñaflor", 1000.0));

    }

    @AfterAll
    static void afterAll() {
        districtNameCorrect = null;
        districtNameIncorrect = null;
        districts = null;
    }

    @Test
    @DisplayName("getDistrictByName -> Nombre de barrio correcto.")
    void testGetDistrictByNameCorrectValueSearch() throws NoSuchFieldException {
        ReflectionTestUtils.setField(districtRepository, "districts", districts);
        var actual = districtRepository.getDistrictByName(districtNameCorrect);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("getDistrictByName -> Nombre de barrio incorrecto.")
    void getDistrictByNameIncorrectValueSearch() throws NoSuchFieldException {
        assertThrows(NoSuchFieldException.class, () -> districtRepository.getDistrictByName(districtNameIncorrect));
    }

    @Test
    @DisplayName("getDistrictByName -> Archivo invalido.")
    void testGetDistrictByNameInvalidFileThrowException() {
        String classPath = "classpath:diaddsf.json";
        ReflectionTestUtils.setField(districtRepository, "classPath", classPath);
        assertThrows(NoSuchFieldException.class, () -> districtRepository.getDistrictByName(districtNameCorrect));
    }
}