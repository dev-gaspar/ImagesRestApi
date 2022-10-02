package com.yourcode.ImagesRestApi.servicios;

import com.yourcode.ImagesRestApi.modelos.Imagen;
import com.yourcode.ImagesRestApi.respositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenServicio {
    @Autowired
    ImagenRepositorio imagenRepositorio;

    public boolean saveOrUpdateImage(Imagen imagen){
        Imagen img=imagenRepositorio.save(imagen);
        if (imagenRepositorio.findById(img.getId())!=null){
            return true;
        }
        return false;
    }

    public List<Imagen> getAllImages(){
        List<Imagen> imagenList= new ArrayList<>();
        imagenRepositorio.findAll().forEach(imagen -> imagenList.add(imagen));
        return imagenList;
    }

    public Imagen getImageById(Integer id){
        return (Imagen) imagenRepositorio.findById(id).get();
    }

    public boolean deleteImage(Integer id){
        imagenRepositorio.deleteById(id);
        if(this.imagenRepositorio.findById(id).isPresent()){
            return false;
        }
        return true;
    }

}
