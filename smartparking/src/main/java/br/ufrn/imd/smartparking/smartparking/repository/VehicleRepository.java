package br.ufrn.imd.smartparking.smartparking.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.imd.smartparking.smartparking.entities.DriverProfile;
import br.ufrn.imd.smartparking.smartparking.entities.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findByOwner(DriverProfile owner);

    Optional<Vehicle> findByVehicleIdAndOwner(UUID vehicleId, DriverProfile owner);
}
