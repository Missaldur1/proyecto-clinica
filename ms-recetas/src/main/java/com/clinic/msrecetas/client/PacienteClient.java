package com.clinic.msrecetas.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MS-PACIENTES")
    public interface PacienteClient {

        @GetMapping("/api/pacientes/{id}")
        Object buscarPaciente(
                @PathVariable Long id);
    }

