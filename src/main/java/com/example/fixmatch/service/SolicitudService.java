package com.example.fixmatch.service;

import com.example.fixmatch.dto.SolicitudRequest;
import com.example.fixmatch.entity.Solicitud;
import com.example.fixmatch.entity.SolicitudFoto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.SolicitudFotoRepository;
import com.example.fixmatch.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudService {
    private final SolicitudRepository solicitudRepository;
    private final SolicitudFotoRepository fotoRepository;

    public Solicitud create(SolicitudRequest request, User user) {
        Solicitud s = new Solicitud();
        s.setEspecialidad(request.getEspecialidad());
        s.setNombreSolicitud(request.getNombreSolicitud());
        s.setDescripcion(request.getDescripcion());
        s.setFechaCreacion(LocalDateTime.now());
        s.setUsuario(user);
        return solicitudRepository.save(s);
    }

    public void addFoto(Long solicitudId, MultipartFile file) throws IOException {
        log.info("Iniciando upload de foto para solicitud: {}", solicitudId);
        log.info("Archivo recibido: {} - Tamaño: {} bytes", file.getOriginalFilename(), file.getSize());
        
        Solicitud s = solicitudRepository.findById(solicitudId).orElseThrow();
        
        // Usar una ruta más robusta para el almacenamiento
        String uploadDir = System.getProperty("user.home") + File.separator + "fixmatch_uploads" + File.separator + "solicitudes";
        File dir = new File(uploadDir);
        
        log.info("Directorio de upload: {}", uploadDir);
        log.info("Directorio existe: {}", dir.exists());
        
        // Crear directorios con manejo de errores
        if (!dir.exists()) {
            log.info("Creando directorio: {}", uploadDir);
            boolean created = dir.mkdirs();
            if (!created) {
                log.error("No se pudo crear el directorio: {}", uploadDir);
                throw new IOException("No se pudo crear el directorio: " + uploadDir);
            }
            log.info("Directorio creado exitosamente");
        }
        
        // Verificar permisos de escritura
        if (!dir.canWrite()) {
            log.error("No hay permisos de escritura en: {}", uploadDir);
            throw new IOException("No hay permisos de escritura en: " + uploadDir);
        }
        
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        
        log.info("Guardando archivo en: {}", dest.getAbsolutePath());
        
        try {
            file.transferTo(dest);
            log.info("Archivo guardado exitosamente");
        } catch (IOException e) {
            log.error("Error al guardar el archivo: {}", e.getMessage());
            throw new IOException("Error al guardar el archivo: " + e.getMessage());
        }
        
        SolicitudFoto foto = new SolicitudFoto();
        foto.setSolicitud(s);
        foto.setUrl(dest.getAbsolutePath());
        foto.setFechaSubida(LocalDateTime.now());
        fotoRepository.save(foto);
        
        log.info("Foto registrada en base de datos con ID: {}", foto.getId());
    }
}
