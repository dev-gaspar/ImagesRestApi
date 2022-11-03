package com.yourcode.ImagesRestApi.servicios;

import com.yourcode.ImagesRestApi.modelos.Post;
import com.yourcode.ImagesRestApi.respositorios.PostRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServicio {
    @Autowired
    PostRepositorio postRepositorio;

    public boolean saveOrUpdatePost(Post publicacion){
        Post post=postRepositorio.save(publicacion);
        if (postRepositorio.findById(post.getId())!=null){
            return true;
        }
        return false;
    }

    public List<Post> getAllPost(){
        List<Post> postList= new ArrayList<>();
        postRepositorio.findAll().forEach(imagen -> postList.add(imagen));
        return postList;
    }

    public Post getPostById(Integer id){
        return (Post) postRepositorio.findById(id).get();
    }

    public boolean deletePost(Integer id){
        postRepositorio.deleteById(id);
        if(this.postRepositorio.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public boolean exists(int id){
        return postRepositorio.existsById(id);
    }

}
