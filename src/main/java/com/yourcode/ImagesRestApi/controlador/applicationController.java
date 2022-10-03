package com.yourcode.ImagesRestApi.controlador;

import com.yourcode.ImagesRestApi.modelos.Imagen;
import com.yourcode.ImagesRestApi.servicios.ImagenServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/application")
public class applicationController {
    @Autowired
    ImagenServicio imagenServicio;

    @GetMapping("/cargar")
    public String cargarImages(Model model) {
        Imagen img = new Imagen();
        model.addAttribute("img", img);
        return "CargarImagen";
    }

    @PostMapping("/save")
    public String guardar(@RequestParam("file") MultipartFile imagen, RedirectAttributes redirectAttributes) {
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
        return "redirect:/application/cargar";
    }

    @GetMapping("/AllImages")
    public String verImages(Model model) {
        List<Imagen> images=imagenServicio.getAllImages();
        model.addAttribute("img",images);
        return "MostrarImagen";
    }

    @GetMapping("/show/{id}")
    public String mostrarImagen(@PathVariable Integer id, Model model) {
        List<Imagen> images= new ArrayList<>();
        images.add(imagenServicio.getImageById(id));
        model.addAttribute("img",images);
        return "MostrarImagen";
    }
}
