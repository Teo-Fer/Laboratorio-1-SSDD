package com.banco.controller;

import com.banco.model.Movement;
import com.banco.repository.MovementRepository;
import com.banco.service.MovementService;
import com.banco.service.OperationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banco.kafka.Producer;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @Autowired
    Producer<Movement> producer;

    @GetMapping()
    public ResponseEntity<Collection<Movement>> getAccount() {
        Collection<Movement> movements = movementService.findAll();
        if (movements == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(movements);
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String,Double>> getBalance(){
        double balance = 0;
        for (Movement movement : movementService.findAll()){
            if (movement.getType().equals(OperationType.Deposit) || movement.getType().equals(OperationType.Interest)) {
                balance += movement.getAmount();
            } else {
                balance -= movement.getAmount();
            }
        }
        HashMap<String, Double> map = new HashMap<>();
        map.put("balance", balance);
        return ResponseEntity.ok().body(map);
    }

    @PostMapping(path = "/deposit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movement> deposit(@RequestParam("amount") double amount){
        Movement movement = movementService.create(new Movement(OperationType.Deposit, amount));
        if (movement.getId()==null) return ResponseEntity.badRequest().build();
        Movement mvAux = new Movement();
        mvAux.setType(OperationType.Deposit);
        mvAux.setAmount(amount);
        producer.logCreate("movements", mvAux);
        return ResponseEntity.ok().body(movement);
    }

    @PostMapping(path = "/extract",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movement> extract(@RequestParam("amount") double amount){
        Movement movement = movementService.create(new Movement(OperationType.Extract, amount));
        if (movement.getId()==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(movement);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Map<String,String>> deleteAll(){
        movementService.deleteAll();
        HashMap<String, String> map = new HashMap<>();
        map.put("result", "ok");
        return ResponseEntity.ok().body(map);

    }

    @PostMapping(path = "/interest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movement> interest(@RequestParam(name="amount",required = false, defaultValue = "10") double amount){
        double interest=0;
        for (Movement movement : movementService.findAll()){
            if (movement.getType().equals(OperationType.Extract)){
                interest-=movement.getAmount();
            } else {
                interest+=movement.getAmount();
            }
        }
        interest = interest * amount / 100;
        Movement movement = movementService.create(new Movement(OperationType.Interest, interest));
        if (movement.getId()==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(movement);
    }


}
