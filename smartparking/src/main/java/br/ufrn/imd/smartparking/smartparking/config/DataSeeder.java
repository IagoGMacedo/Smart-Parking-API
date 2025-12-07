package br.ufrn.imd.smartparking.smartparking.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.ufrn.imd.smartparking.smartparking.entities.Enterprise;
import br.ufrn.imd.smartparking.smartparking.entities.ParkingSpace;
import br.ufrn.imd.smartparking.smartparking.entities.Enums.TypeParkingSpace;
import br.ufrn.imd.smartparking.smartparking.repository.EnterpriseRepository;
import br.ufrn.imd.smartparking.smartparking.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final EnterpriseRepository enterpriseRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;
    private final GeometryFactory geometryFactory;

    @Override
    public void run(String... args) throws Exception {
        String enterpriseName = "IMD/UFRN";

        // Se já existe, não faz nada
        if (enterpriseRepository.findByName(enterpriseName).isPresent()) {
            return;
        }

        // Coordenadas do IMD (lon, lat)
        double imdLon = -35.20546034385188;
        double imdLat = -5.832300849999999;

        Point imdLocation = geometryFactory.createPoint(new Coordinate(imdLon, imdLat));
        imdLocation.setSRID(4326);

        Enterprise imd = new Enterprise();
        imd.setName(enterpriseName);
        imd.setAddress("IMD/UFRN - Natal/RN");
        imd.setLocation(imdLocation);

        Enterprise savedImd = enterpriseRepository.save(imd);

        // Criar 5 vagas ao redor do IMD (pequenos deslocamentos)
        for (int i = 1; i <= 5; i++) {
            ParkingSpace parkingSpace = new ParkingSpace();
            parkingSpace.setCode("A-" + i);
            parkingSpace.setLevel("A");
            // Ajuste o tipo conforme o seu enum
            parkingSpace.setType(TypeParkingSpace.REGULAR); // ou REGULAR, etc.
            parkingSpace.setEnterprise(savedImd);

            // Pequeno deslocamento pra cada vaga (pra simular posições diferentes)
            double delta = i * 0.00003; // ~3 metros aprox. (depende da latitude)

            Point spotLocation = geometryFactory.createPoint(
                    new Coordinate(imdLon + delta, imdLat + delta));
            spotLocation.setSRID(4326);

            parkingSpace.setLocation(spotLocation);

            parkingSpaceRepository.save(parkingSpace);
        }
    }

}
