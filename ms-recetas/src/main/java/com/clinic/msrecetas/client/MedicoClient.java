package com.clinic.msrecetas.client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "MS-MEDICOS")
public interface MedicoClient {

    @GetMapping("/api/medicos/{id}")
    Object buscarMedico(
            @PathVariable Long id);
}