package com.banco.service;

import com.banco.model.Movement;
import com.banco.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;


    public Movement create (Movement movement){
        return movementRepository.save(movement);
    }

    public List<Movement> findAll (){
        return movementRepository.findAll();
    }

    public void delete (Movement movement){
        movementRepository.delete(movement);
    }

    public void deleteAll (){
        movementRepository.deleteAll();
    }

    public Optional<Movement> findById (long id){
        return movementRepository.findById(id);
    }

}
