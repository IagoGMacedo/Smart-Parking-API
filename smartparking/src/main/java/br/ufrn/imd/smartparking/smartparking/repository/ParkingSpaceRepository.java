package br.ufrn.imd.smartparking.smartparking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSpaceSuggestionProjection;
import br.ufrn.imd.smartparking.smartparking.entities.Enterprise;
import br.ufrn.imd.smartparking.smartparking.entities.ParkingSpace;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    List<ParkingSpace> findByEnterprise(Enterprise enterprise);

    @Query(value = """
            SELECT
                p.id AS id,
                p.code AS code,
                p.level AS level,
                p.type AS type,
                ST_Y(p.location) AS latitude,
                ST_X(p.location) AS longitude,
                ST_DistanceSphere(
                    p.location,
                    ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
                ) AS distanceMeters
            FROM parking_space p
            WHERE p.enterprise_id = :enterpriseId
            ORDER BY p.location <-> ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
            LIMIT :limit
            """, nativeQuery = true)
    List<ParkingSpaceSuggestionProjection> findNearestSpacesByEnterprise(
            @Param("enterpriseId") Long enterpriseId,
            @Param("lon") double longitude,
            @Param("lat") double latitude,
            @Param("limit") int limit);
}
