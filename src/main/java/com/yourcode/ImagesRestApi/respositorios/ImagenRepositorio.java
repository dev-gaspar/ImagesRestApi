package com.yourcode.ImagesRestApi.respositorios;
import com.yourcode.ImagesRestApi.modelos.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, Integer>{
}