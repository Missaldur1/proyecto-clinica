package com.clinic.msmedicos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Microservicio Médicos")
                                .version("1.0.0")
                                .description("""
                                        Microservicio encargado de:

                                        - Registro de médicos
                                        - Consulta de profesionales
                                        - Gestión de especialidades
                                        - Actualización de información profesional
                                        - Administración de disponibilidad médica
                                        """)
                                .contact(
                                        new Contact()
                                                .name("Proyecto Clínica")
                                                .email("admin@clinica.local"))
                                .license(
                                        new License()
                                                .name("Uso Académico")));
    }
}