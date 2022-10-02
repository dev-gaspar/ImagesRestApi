package com.yourcode.ImagesRestApi.controlador;

import com.yourcode.ImagesRestApi.modelos.Imagen;
import com.yourcode.ImagesRestApi.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/api")
public class Controlador {
    @Autowired
    ImagenServicio imagenServicio;

    @GetMapping("/AllImages")
    public List<Imagen> verImages() {
        return imagenServicio.getAllImages();
    }

    @PostMapping("/save")
    public Imagen guardar(@RequestParam("file") MultipartFile imagen) {
        Imagen nuevaImagen = new Imagen();

        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);

                nuevaImagen.setUrl(rutaCompleta.toString());
                nuevaImagen.setName(imagen.getOriginalFilename());

                imagenServicio.saveOrUpdateImage(nuevaImagen);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return nuevaImagen;
    }

    @GetMapping("/show/{id}")
    public Imagen mostrarImagen(@PathVariable Integer id) {
        return imagenServicio.getImageById(id);
    }
}
