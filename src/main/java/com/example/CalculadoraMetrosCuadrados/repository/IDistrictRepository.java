package com.example.CalculadoraMetrosCuadrados.repository;

import com.example.CalculadoraMetrosCuadrados.entities.District;

public interface IDistrictRepository {
    District getDistrictByName(String name) throws NoSuchFieldException;

}
