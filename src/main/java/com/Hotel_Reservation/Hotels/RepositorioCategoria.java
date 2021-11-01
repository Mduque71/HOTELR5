/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Hotel_Reservation.Hotels;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marco Moreno
 */
@Repository
public class RepositorioCategoria {
    @Autowired
    private InterfaceCategoria crud;
    
    public List<Categoria> getAll(){
        return (List<Categoria>) crud.findAll();
    }
    
    public Optional <Categoria> getCategoria (int id){
        return crud.findById(id);
    }
    
    public Categoria save(Categoria categoria){
        return crud.save(categoria);
    }
    
    public void delete(Categoria categoria){
       crud.delete(categoria);
    }
}
