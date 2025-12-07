package br.ufrn.imd.smartparking.smartparking.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartparking.smartparking.controller.dto.CreateVehicleDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.UpdateVehicleDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.VehicleResponseDto;
import br.ufrn.imd.smartparking.smartparking.service.VehicleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> listMyVehicles() {
        List<VehicleResponseDto> vehicles = vehicleService.getCurrentUserVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@RequestBody CreateVehicleDto dto) {
        VehicleResponseDto created = vehicleService.createVehicleForCurrentUser(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(
            @PathVariable("id") UUID id,
            @RequestBody UpdateVehicleDto dto) {
        VehicleResponseDto updated = vehicleService.updateVehicleForCurrentUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") UUID id) {
        vehicleService.deleteVehicleForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }
}
