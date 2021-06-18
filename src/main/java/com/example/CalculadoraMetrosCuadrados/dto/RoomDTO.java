package com.example.CalculadoraMetrosCuadrados.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    @JsonProperty("environment_name")
    @NotEmpty(message = "El nombre del ambiente no puede estar vacío.")
    @Size(min = 1, max = 30, message = "La longitud del nombre no puede superar los 30 caracteres.")
    @Pattern(regexp = "([A-Z]|[0-9])[\\s|[0-9]|A-Z|a-z|ñ|ó|í|á|é|ú|Á|Ó|É|Í|Ú]*$", message = "El nombre del ambiente debe comenzar con mayúscula.")
    private String name;

    @JsonProperty("environment_width")
    @NotNull(message = "El ancho del ambiente no puede estar vacío.")
    @DecimalMax(value = "25", message = "El máximo ancho permitido por propiedad es de 25 mts.")
    private double width;

    @JsonProperty("environment_length")
    @NotNull(message = "El campo largo del ambiente no puede estar vacío.")
    @DecimalMax(value = "33", message = "El máximo largo permitido por propiedad es de 33 mts.")
    private double length;
}
