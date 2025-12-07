package br.ufrn.imd.smartparking.smartparking.controller.dto;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.TypeParkingSpace;

public interface ParkingSpaceSuggestionProjection {

    Long getId();

    String getCode();

    String getLevel();

    TypeParkingSpace getType();

    Double getLatitude();

    Double getLongitude();

    Double getDistanceMeters();
}
