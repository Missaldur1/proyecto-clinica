package com.clinic.msreservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-medicos")
public interface MedicoClient {

    @GetMapping("/api/medicos/{id}")
    Object buscarMedico(
            @PathVariable Long id);

}