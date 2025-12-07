package br.ufrn.imd.smartparking.smartparking.entities;

import org.locationtech.jts.geom.Point;

import br.ufrn.imd.smartparking.smartparking.entities.Enums.TypeParkingSpace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ParkingSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String level;

    @Enumerated(EnumType.STRING)
    private TypeParkingSpace type;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;
}
