package com.example.CalculadoraMetrosCuadrados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseDTO {

    @JsonProperty("prop_name")
    @NotEmpty(message = "El nombre de la propiedad no puede estar vacío.")
    @Size(min = 1, max = 30, message = "La longitud del nombre no puede superar los 30 caracteres.")
    @Pattern(regexp = "([A-Z]|[0-9])[\\s|[0-9]|A-Z|a-z|ñ|ó|í|á|é|ú|Á|Ó|É|Í|Ú]*$", message = "El nombre de la propiedad debe comenzar con mayúscula.")
    private String propName;

    @JsonProperty("district_name")
    @NotNull(message = "El barrio no puede estar vacío.")
    @Size(min = 1, max = 45, message = "La longitud del barrio no puede superar los 45 caracteres.")
    private String districtName;

    @JsonProperty("district_price")
    @NotNull(message = "El precio de barrio no puede estar vacío.")
    @DecimalMax(value = "4000", message = "El precio máximo permitido por metro cuadrado no puede superar los 4000 $US.")
    private double districtPrice;

    @NotEmpty(message = "La lista no puede ser vacía.")
    private List<@Valid RoomDTO> rooms;
}
