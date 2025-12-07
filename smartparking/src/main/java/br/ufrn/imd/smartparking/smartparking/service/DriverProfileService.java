package br.ufrn.imd.smartparking.smartparking.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufrn.imd.smartparking.smartparking.controller.dto.DriverProfileResponseDto;
import br.ufrn.imd.smartparking.smartparking.controller.dto.UpdateDriverProfileDto;
import br.ufrn.imd.smartparking.smartparking.entities.DriverProfile;
import br.ufrn.imd.smartparking.smartparking.entities.User;
import br.ufrn.imd.smartparking.smartparking.repository.DriverProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverProfileService {

    private final CurrentUserService currentUserService;

    private final DriverProfileRepository driverProfileRepository;

    @Transactional(readOnly = true)
    public Optional<DriverProfileResponseDto> getCurrentUserProfileResponse() {
        User currentUser = currentUserService.getCurrentUser();

        return driverProfileRepository.findByUser(currentUser)
                .map(profile -> new DriverProfileResponseDto(
                        profile.getHasElderlyCredential(),
                        profile.getHasPcdCredential(),
                        profile.getBirthDate()));
    }

    /**
     * Cria ou atualiza o DriverProfile do usuário logado.
     */
    @Transactional
    public void updateCurrentUserProfile(UpdateDriverProfileDto dto) {

        DriverProfile profile = getCurrentUserProfile();

        profile.setHasElderlyCredential(dto.hasElderlyCredential());
        profile.setHasPcdCredential(dto.hasPcdCredential());
        profile.setBirthDate(dto.birthDate());

        driverProfileRepository.save(profile);
    }

    public DriverProfile getCurrentUserProfile() {
        User currentUser = currentUserService.getCurrentUser();

        return driverProfileRepository.findByUser(currentUser)
                .orElseGet(() -> {
                    DriverProfile newProfile = new DriverProfile();
                    newProfile.setUser(currentUser);
                    return driverProfileRepository.save(newProfile);
                });
    }

}
