package br.ufrn.imd.smartparking.smartparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.imd.smartparking.smartparking.entities.DriverProfile;
import br.ufrn.imd.smartparking.smartparking.entities.User;

public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {

    Optional<DriverProfile> findByUser(User user);
}
