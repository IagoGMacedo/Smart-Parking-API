package br.ufrn.imd.smartparking.smartparking.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartparking.smartparking.controller.dto.DriverProfileResponseDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.UpdateDriverProfileDto;
import br.ufrn.imd.smartparking.smartparking.service.DriverProfileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class DriverProfileController {
    private final DriverProfileService driverProfileService;

    @GetMapping
    public ResponseEntity<DriverProfileResponseDto> getProfile() {
        Optional<DriverProfileResponseDto> profileOpt = driverProfileService.getCurrentUserProfileResponse();

        return profileOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateDriverProfileDto dto) {
        driverProfileService.updateCurrentUserProfile(dto);
        return ResponseEntity.ok().build();
    }
}
