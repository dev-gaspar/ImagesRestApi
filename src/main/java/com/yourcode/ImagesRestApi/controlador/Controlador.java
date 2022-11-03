package com.yourcode.ImagesRestApi.controlador;

import com.yourcode.ImagesRestApi.dto.Mensaje;
import com.yourcode.ImagesRestApi.modelos.Imagen;
import com.yourcode.ImagesRestApi.servicios.CloudinaryService;
import com.yourcode.ImagesRestApi.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class Controlador {
    @Autowired
    ImagenServicio imagenServicio;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("/imagen")
    public List<Imagen> AllImages() {
        return imagenServicio.getAllImages();
    }


    @PostMapping("/imagen")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile imagen)throws IOException {
        BufferedImage bi = ImageIO.read(imagen.getInputStream());
        if(bi == null){
            return new ResponseEntity(new Mensaje("imagen no válida"), HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(imagen);
        Imagen img =
                new Imagen((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("public_id"));
        imagenServicio.saveOrUpdateImage(img);

        return new ResponseEntity(new Mensaje("imagen subida"), HttpStatus.OK);
    }

    @GetMapping("/imagen/{id}")
    public Imagen imageById(@PathVariable Integer id) {
        return imagenServicio.getImageById(id);
    }

    @DeleteMapping("/imagen/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id)throws IOException {
        if(!imagenServicio.exists(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Imagen imagen = imagenServicio.getImageById(id);
        Map result = cloudinaryService.delete(imagen.getImagenId());
        imagenServicio.deleteImage(id);
        return new ResponseEntity(new Mensaje("imagen eliminada"), HttpStatus.OK);
    }
}
