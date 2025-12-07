package br.ufrn.imd.smartparking.smartparking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSuggestionRequest;
import br.ufrn.imd.smartparking.smartparking.controller.dto.ParkingSuggestionResponse;
import br.ufrn.imd.smartparking.smartparking.service.ParkingSuggestionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingSuggestionController {
    private final ParkingSuggestionService parkingSuggestionService;

    @PostMapping("/suggest")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ParkingSuggestionResponse>> suggestParkingSpaces(
            @RequestBody ParkingSuggestionRequest request) {
        List<ParkingSuggestionResponse> suggestions = parkingSuggestionService.suggestSpaces(request);
        return ResponseEntity.ok(suggestions);
    }
}
