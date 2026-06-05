package com.clinic.msusuarios.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinic.msusuarios.dto.UsuarioRequestDTO;
import com.clinic.msusuarios.dto.UsuarioResponseDTO;
import com.clinic.msusuarios.exception.ResourceNotFoundException;
import com.clinic.msusuarios.mapper.UsuarioMapper;
import com.clinic.msusuarios.model.Usuario;
import com.clinic.msusuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {

        log.info("Creando usuario con email: {}", dto.getEmail());

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = UsuarioMapper.toEntity(dto);

        // ENCRIPTAR PASSWORD
        usuario.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        Usuario guardado = usuarioRepository.save(usuario);

        log.info("Usuario creado correctamente con ID: {}", guardado.getId());

        return UsuarioMapper.toDTO(guardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {

        log.info("Listando usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO buscarPorId(Long id) {

        log.info("Buscando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return UsuarioMapper.toDTO(usuario);
    }

    //Actualización de usuario ahora utilizando Mapper para actualizar solo los campos permitidos y manteniendo la lógica de encriptación de contraseña
    @Override
    public UsuarioResponseDTO actualizarUsuario(
            Long id,
            UsuarioRequestDTO dto) {

        log.info("Actualizando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado"));

        UsuarioMapper.updateEntity(usuario, dto);

        usuario.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        Usuario actualizado = usuarioRepository.save(usuario);

        log.info(
                "Usuario actualizado correctamente con ID: {}",
                actualizado.getId());

        return UsuarioMapper.toDTO(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {

        log.info("Eliminando usuario con ID: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuarioRepository.delete(usuario);

        log.info("Usuario eliminado correctamente");
    }
}