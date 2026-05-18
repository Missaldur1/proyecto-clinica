package com.clinic.msfichasclinicas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-PACIENTES")
public interface PacienteClient {

    @GetMapping("/api/pacientes/{id}")
    Object buscarPaciente(@PathVariable Long id);
}