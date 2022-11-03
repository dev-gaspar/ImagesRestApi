package com.yourcode.ImagesRestApi.respositorios;
import com.yourcode.ImagesRestApi.modelos.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositorio extends JpaRepository<Post, Integer>{
}