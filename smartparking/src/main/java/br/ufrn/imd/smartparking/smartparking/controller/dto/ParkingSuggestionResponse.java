package br.ufrn.imd.smartparking.smartparking.controller.dto;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.TypeParkingSpace;

public record ParkingSuggestionResponse(
        Long id,
        String code,
        String level,
        TypeParkingSpace type,
        double latitude,
        double longitude,
        double distanceInMeters,
        boolean allowedForUser) {
}
