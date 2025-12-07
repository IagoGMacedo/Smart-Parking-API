package br.ufrn.imd.smartparking.smartparking.controller.dto;

import java.time.LocalDate;

public record UpdateDriverProfileDto(
        Boolean hasElderlyCredential,
        Boolean hasPcdCredential,
        LocalDate birthDate) {
}
