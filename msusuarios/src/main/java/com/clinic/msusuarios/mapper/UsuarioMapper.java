package com.clinic.msusuarios.mapper;

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.model.Usuario;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequestDTO dto) {

        return Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .activo(dto.getActivo())
                .build();
    }

    public static UsuarioResponseDTO toDTO(Usuario usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .activo(usuario.getActivo())
                .build();
    }

    // Método para actualizar una entidad Usuario existente con datos de un DTO, manteniendo la lógica de actualización centralizada en el Mapper
    public static void updateEntity(
            Usuario usuario,
            UsuarioRequestDTO dto) {

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.getActivo());
    }
}