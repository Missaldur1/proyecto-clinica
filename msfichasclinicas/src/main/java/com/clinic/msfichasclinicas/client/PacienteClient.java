package com.clinic.msfichasclinicas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pacientes", url = "http://localhost:8082")
public interface PacienteClient {

    @GetMapping("/pacientes/{id}")
    Object buscarPaciente(@PathVariable Long id);

}