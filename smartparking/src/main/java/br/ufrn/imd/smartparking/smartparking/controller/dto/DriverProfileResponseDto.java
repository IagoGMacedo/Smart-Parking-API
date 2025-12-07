package br.ufrn.imd.smartparking.smartparking.controller.dto;

import java.time.LocalDate;

public record DriverProfileResponseDto(
        Boolean hasElderlyCredential,
        Boolean hasPcdCredential,
        LocalDate birthDate) {
}
