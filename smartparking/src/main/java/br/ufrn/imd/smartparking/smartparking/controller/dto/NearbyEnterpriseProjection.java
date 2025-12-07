package br.ufrn.imd.smartparking.smartparking.controller.dto;

public interface NearbyEnterpriseProjection {

    Long getEnterpriseId();

    String getName();

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    Double getDistanceMeters();
}
