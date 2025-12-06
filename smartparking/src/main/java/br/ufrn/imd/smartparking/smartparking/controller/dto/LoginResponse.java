package br.ufrn.imd.smartparking.smartparking.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
