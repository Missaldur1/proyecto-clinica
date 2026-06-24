package com.clinic.msusuarios.config;

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

                                                                .title("Microservicio Usuarios")

                                                                .description("""
                                                                                Microservicio encargado de:

                                                                                - Gestión de usuarios
                                                                                - Registro de usuarios
                                                                                - Autenticación
                                                                                - Administración de perfiles
                                                                                - Seguridad del sistema
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