package com.clinic.msfichasclinicas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-MEDICOS")
public interface MedicoClient {

    @GetMapping("/api/medicos/{id}")
    Object buscarMedico(@PathVariable Long id);
}