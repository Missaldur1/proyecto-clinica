package com.clinic.msfichasclinicas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-medicos", url = "http://localhost:8083")
public interface MedicoClient {

    @GetMapping("/medicos/{id}")
    Object buscarMedico(@PathVariable Long id);

}