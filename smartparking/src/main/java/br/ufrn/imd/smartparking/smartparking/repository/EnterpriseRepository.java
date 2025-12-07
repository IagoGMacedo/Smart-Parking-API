package br.ufrn.imd.smartparking.smartparking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ufrn.imd.smartparking.smartparking.controller.dto.NearbyEnterpriseProjection;
import br.ufrn.imd.smartparking.smartparking.entities.Enterprise;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
    Optional<Enterprise> findByName(String name);

    @Query(value = """
            SELECT
                e.enterprise_id AS enterpriseId,
                e.name AS name,
                e.address AS address,
                ST_Y(e.location) AS latitude,
                ST_X(e.location) AS longitude,
                ST_DistanceSphere(
                    e.location,
                    ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
                ) AS distanceMeters
            FROM tb_enterprises e
            WHERE ST_DWithin(
                geography(e.location),
                geography(ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)),
                :radiusMeters
            )
            ORDER BY e.location <-> ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)
            LIMIT :limit
            """, nativeQuery = true)
    List<NearbyEnterpriseProjection> findNearbyEnterprises(
            @Param("lon") double longitude,
            @Param("lat") double latitude,
            @Param("radiusMeters") double radiusMeters,
            @Param("limit") int limit);

}