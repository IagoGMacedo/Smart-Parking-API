package br.ufrn.imd.smartparking.smartparking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartparking.smartparking.controller.dto.LocationRequest;
import br.ufrn.imd.smartparking.smartparking.controller.dto.NearbyEnterpriseResponse;
import br.ufrn.imd.smartparking.smartparking.service.EnterpriseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    @PostMapping("/nearby")
    public List<NearbyEnterpriseResponse> getNearbyEnterprises(@RequestBody LocationRequest request) {
        return enterpriseService.findNearbyEnterprises(request);
    }
}
