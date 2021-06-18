package com.example.CalculadoraMetrosCuadrados.repository;


import com.example.CalculadoraMetrosCuadrados.entities.District;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Repository
public class DistrictRepository implements IDistrictRepository {

    private List<District> districts;
    private String classPath = "classpath:districts.json";

    public DistrictRepository() {
        this.districts = loadFromDB();
    }

    /**
     * Metodo para cargar el archivo Json como base
     * @return
     */
    private List<District> loadFromDB() {
        List<District> ret = null;

        File file = null;

        try {
            file = ResourceUtils.getFile(classPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        var objectMapper = new ObjectMapper();

        try {
            ret = objectMapper.readValue(file, new TypeReference<List<District>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Metodo para obtener un barrio a partir de su nombre
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    @Override
    public District getDistrictByName(String name) throws NoSuchFieldException {
        return this.districts.stream()
                .filter(district -> district.getName().equals(name))
                .findFirst().orElseThrow((() -> new NoSuchFieldException("El barrio " + name + ", no fue encontrado"))
                );
    }
}
