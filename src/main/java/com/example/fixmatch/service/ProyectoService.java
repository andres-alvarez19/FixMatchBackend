package com.example.fixmatch.service;

import com.example.fixmatch.dto.ProyectoRequest;
import com.example.fixmatch.entity.Proyecto;
import com.example.fixmatch.entity.ProyectoFoto;
import com.example.fixmatch.entity.User;
import com.example.fixmatch.repository.ProyectoFotoRepository;
import com.example.fixmatch.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final ProyectoFotoRepository fotoRepository;

    public Proyecto create(ProyectoRequest request, User user) {
        Proyecto p = new Proyecto();
        p.setNombre(request.getNombre());
        p.setDescripcion(request.getDescripcion());
        p.setFecha(LocalDate.parse(request.getFecha()));
        p.setUsuario(user);
        return proyectoRepository.save(p);
    }

    public void addFoto(Long proyectoId, MultipartFile file) throws IOException {
        Proyecto p = proyectoRepository.findById(proyectoId).orElseThrow();
        String dirPath = "uploads/proyectos";
        File dir = new File(dirPath);
        dir.mkdirs();
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        file.transferTo(dest);
        ProyectoFoto foto = new ProyectoFoto();
        foto.setProyecto(p);
        foto.setUrl(dest.getPath());
        foto.setFechaSubida(LocalDateTime.now());
        fotoRepository.save(foto);
    }
}
