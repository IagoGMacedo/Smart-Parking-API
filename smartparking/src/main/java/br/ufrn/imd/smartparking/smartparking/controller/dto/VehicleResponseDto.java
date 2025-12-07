package br.ufrn.imd.smartparking.smartparking.controller.dto;

import java.util.UUID;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.VehicleType;

public record VehicleResponseDto(
        UUID id,
        String plate,
        String brand,
        String model,
        String color,
        VehicleType type) {
}
