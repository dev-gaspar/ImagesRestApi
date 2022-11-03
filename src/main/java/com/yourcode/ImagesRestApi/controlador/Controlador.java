package com.yourcode.ImagesRestApi.controlador;

import com.yourcode.ImagesRestApi.dto.Mensaje;
import com.yourcode.ImagesRestApi.modelos.Post;
import com.yourcode.ImagesRestApi.servicios.CloudinaryService;
import com.yourcode.ImagesRestApi.servicios.PostServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class Controlador {
    @Autowired
    PostServicio postServicio;

    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("/post")
    public List<Post> AllPost() {
        return postServicio.getAllPost();
    }


    @PostMapping("/post")

    public ResponseEntity<?> Postear(@RequestPart("titulo") String titulo, @RequestPart("description") String description, @RequestPart("file") MultipartFile imagen)throws IOException {
        BufferedImage bi = ImageIO.read(imagen.getInputStream());
        Post post = null;
        if(bi != null){
            Map result = cloudinaryService.upload(imagen);
            post =
                    new Post(titulo,description,(String)result.get("original_filename"),
                            (String)result.get("url"),
                            (String)result.get("public_id"));
        } else {
            post =
                    new Post(titulo,description,null,
                           null,
                            null);
        }

        postServicio.saveOrUpdatePost(post);
        return new ResponseEntity(new Mensaje("posteado"), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public Post PostById(@PathVariable Integer id) {
        return postServicio.getPostById(id);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> Delete(@PathVariable("id") int id)throws IOException {
        if(!postServicio.exists(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Post post = postServicio.getPostById(id);
        Map result = cloudinaryService.delete(post.getImagenId());
        postServicio.deletePost(id);
        return new ResponseEntity(new Mensaje("post eliminado"), HttpStatus.OK);
    }
}
