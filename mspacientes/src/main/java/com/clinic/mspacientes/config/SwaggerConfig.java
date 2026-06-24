package com.clinic.mspacientes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI pacientesOpenAPI() {

                return new OpenAPI()

                                .info(
                                                new Info()

                                                                .title("Microservicio Pacientes")

                                                                .description("""
                                                                                Microservicio encargado de:

                                                                                - Registro de pacientes
                                                                                - Consulta de pacientes
                                                                                - Actualización de datos personales
                                                                                - Gestión del historial clínico básico
                                                                                """)

                                                                .version("1.0.0")

                                                                .contact(
                                                                                new Contact()
                                                                                                .name("Proyecto Clínica")
                                                                                                .email("admin@clinica.local"))

                                                                .license(
                                                                                new License()
                                                                                                .name("Uso Académico")));
        }
}