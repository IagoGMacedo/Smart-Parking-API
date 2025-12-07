package br.ufrn.imd.smartparking.smartparking.controller.dto;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.VehicleType;

public record CreateVehicleDto(
        String plate,
        String brand,
        String model,
        String color,
        VehicleType type) {
}
