package br.ufrn.imd.smartparking.smartparking.controller.dto;

import java.util.UUID;

public record ParkingSuggestionRequest(
        Long enterpriseId,
        UUID vehicleId,
        double latitude,
        double longitude
) {}
