package com.example.fixmatch.service;

import com.example.fixmatch.dto.SolicitudRequest;
import com.example.fixmatch.entity.Solicitud;
import com.example.fixmatch.entity.SolicitudFoto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.SolicitudFotoRepository;
import com.example.fixmatch.repository.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        Solicitud s = solicitudRepository.findById(solicitudId).orElseThrow();
        String dirPath = "uploads/solicitudes";
        File dir = new File(dirPath);
        dir.mkdirs();
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        file.transferTo(dest);
        SolicitudFoto foto = new SolicitudFoto();
        foto.setSolicitud(s);
        foto.setUrl(dest.getPath());
        foto.setFechaSubida(LocalDateTime.now());
        fotoRepository.save(foto);
    }
}
