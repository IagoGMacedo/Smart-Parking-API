package br.ufrn.imd.smartparking.smartparking.controller.dto;

public record NearbyEnterpriseResponse(
        Long id,
        String name,
        String address,
        double latitude,
        double longitude,
        double distanceInMeters) {
}
