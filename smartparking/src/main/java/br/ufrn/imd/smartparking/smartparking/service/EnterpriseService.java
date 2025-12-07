package br.ufrn.imd.smartparking.smartparking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufrn.imd.smartparking.smartparking.controller.dto.LocationRequest;
import br.ufrn.imd.smartparking.smartparking.controller.dto.NearbyEnterpriseProjection;
import br.ufrn.imd.smartparking.smartparking.controller.dto.NearbyEnterpriseResponse;
import br.ufrn.imd.smartparking.smartparking.repository.EnterpriseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnterpriseService {
    private static final double DEFAULT_RADIUS_METERS = 500.0;
    private static final int DEFAULT_LIMIT = 5;

    private final EnterpriseRepository enterpriseRepository;

    public List<NearbyEnterpriseResponse> findNearbyEnterprises(LocationRequest locationRequest) {

        double lat = locationRequest.latitude();
        double lon = locationRequest.longitude();

        List<NearbyEnterpriseProjection> projections = enterpriseRepository.findNearbyEnterprises(
                lon,
                lat,
                DEFAULT_RADIUS_METERS,
                DEFAULT_LIMIT);

        return projections.stream()
                .map(p -> new NearbyEnterpriseResponse(
                        p.getEnterpriseId(),
                        p.getName(),
                        p.getAddress(),
                        p.getLatitude(),
                        p.getLongitude(),
                        p.getDistanceMeters()))
                .toList();
    }
}
