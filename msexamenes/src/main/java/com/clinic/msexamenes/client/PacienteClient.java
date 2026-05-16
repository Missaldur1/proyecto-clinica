package com.clinic.msexamenes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-pacientes")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    Object buscarPaciente(
            @PathVariable("id") Long id);

}