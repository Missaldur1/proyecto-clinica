package com.clinic.msfichasclinicas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-examenes", url = "http://localhost:8084")
public interface ExamenClient {

    @GetMapping("/examenes/{id}")
    Object buscarExamen(@PathVariable Long id);

}