package br.ufrn.imd.smartparking.smartparking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.smartparking.smartparking.controller.dto.CreateVehicleDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.UpdateVehicleDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.VehicleResponseDto;
import br.ufrn.imd.smartparking.smartparking.entities.DriverProfile;
import br.ufrn.imd.smartparking.smartparking.entities.Vehicle;
import br.ufrn.imd.smartparking.smartparking.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private final DriverProfileService driverProfileService;

    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getCurrentUserVehicles() {
        DriverProfile profile = driverProfileService.getCurrentUserProfile();

        List<Vehicle> vehicles = vehicleRepository.findByOwner(profile);

        return vehicles.stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional
    public VehicleResponseDto createVehicleForCurrentUser(CreateVehicleDto dto) {
        DriverProfile profile = driverProfileService.getCurrentUserProfile();

        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(profile);
        vehicle.setPlate(dto.plate());
        vehicle.setBrand(dto.brand());
        vehicle.setModel(dto.model());
        vehicle.setColor(dto.color());
        vehicle.setType(dto.type());

        Vehicle saved = vehicleRepository.save(vehicle);

        return toResponseDto(saved);
    }

    @Transactional
    public VehicleResponseDto updateVehicleForCurrentUser(UUID vehicleId, UpdateVehicleDto dto) {
        DriverProfile profile = driverProfileService.getCurrentUserProfile();

        Vehicle vehicle = vehicleRepository.findByVehicleIdAndOwner(vehicleId, profile)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado para o usuário logado"));

        vehicle.setBrand(dto.brand());
        vehicle.setModel(dto.model());
        vehicle.setColor(dto.color());
        vehicle.setType(dto.type());

        Vehicle updated = vehicleRepository.save(vehicle);

        return toResponseDto(updated);
    }

    @Transactional
    public void deleteVehicleForCurrentUser(UUID vehicleId) {
        DriverProfile profile = driverProfileService.getCurrentUserProfile();

        Vehicle vehicle = vehicleRepository.findByVehicleIdAndOwner(vehicleId, profile)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado para o usuário logado"));

        vehicleRepository.delete(vehicle);
    }

    private VehicleResponseDto toResponseDto(Vehicle v) {
        return new VehicleResponseDto(
                v.getVehicleId(),
                v.getPlate(),
                v.getBrand(),
                v.getModel(),
                v.getColor(),
                v.getType());
    }
}
