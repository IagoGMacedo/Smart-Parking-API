package br.ufrn.imd.smartparking.smartparking.controller.dto;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.VehicleType;

public record UpdateVehicleDto(
        String brand,
        String model,
        String color,
        VehicleType type) {
}
