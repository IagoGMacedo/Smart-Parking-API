package br.ufrn.imd.smartparking.smartparking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSpaceSuggestionProjection;
import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSuggestionRequest;
import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSuggestionResponse;
import br.ufrn.imd.smartparking.smartparking.entities.DriverProfile;
import br.ufrn.imd.smartparking.smartparking.entities.Enterprise;
import br.ufrn.imd.smartparking.smartparking.entities.Enums.TypeParkingSpace;
import br.ufrn.imd.smartparking.smartparking.entities.User;
import br.ufrn.imd.smartparking.smartparking.repository.DriverProfileRepository;
import br.ufrn.imd.smartparking.smartparking.repository.EnterpriseRepository;
import br.ufrn.imd.smartparking.smartparking.repository.ParkingSpaceRepository;
import br.ufrn.imd.smartparking.smartparking.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingSuggestionService {
    private static final int DEFAULT_LIMIT = 5;

    private final CurrentUserService currentUserService;
    private final EnterpriseRepository enterpriseRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional(readOnly = true)
    public List<ParkingSuggestionResponse> suggestSpaces(ParkingSuggestionRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Enterprise enterprise = enterpriseRepository.findById(request.enterpriseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Estabelecimento não encontrado"));

        DriverProfile profile = driverProfileRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Perfil de motorista não encontrado para o usuário logado"));

        UUID vehicleId = request.vehicleId();

        vehicleRepository.findByVehicleIdAndOwner(vehicleId, profile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Veículo não pertence ao usuário logado"));

        double lat = request.latitude();
        double lon = request.longitude();

        List<ParkingSpaceSuggestionProjection> projections = parkingSpaceRepository.findNearestSpacesByEnterprise(
                enterprise.getEnterpriseId(),
                lon,
                lat,
                DEFAULT_LIMIT);

        return projections.stream()
                .map(p -> {
                    boolean allowed = isSpaceAllowedForUser(p.getType(), profile);
                    return new ParkingSuggestionResponse(
                            p.getId(),
                            p.getCode(),
                            p.getLevel(),
                            p.getType(),
                            p.getLatitude(),
                            p.getLongitude(),
                            p.getDistanceMeters(),
                            allowed);
                })
                .toList();
    }

    /**
     * Regra simples: só permite vaga IDOSO se tiver credencial de idoso,
     * só permite PCD se tiver credencial PCD.
     * Demais tipos são livres.
     */
    private boolean isSpaceAllowedForUser(TypeParkingSpace spaceType, DriverProfile profile) {
        if (spaceType == TypeParkingSpace.IDOSO) {
            return Boolean.TRUE.equals(profile.getHasElderlyCredential());
        }
        if (spaceType == TypeParkingSpace.PCD) {
            return Boolean.TRUE.equals(profile.getHasPcdCredential());
        }
        return true;
    }
}
