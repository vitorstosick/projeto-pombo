package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User newUser) throws PruuException {
        return ResponseEntity.ok(service.save(newUser));
    }

    @GetMapping
    public List<User> findAll() {
        List<User> users = service.findAll();
        return users;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) throws PruuException {
        User user = service.findById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User updatedUser) {
        return ResponseEntity.ok(service.update(updatedUser));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) throws PruuException {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
