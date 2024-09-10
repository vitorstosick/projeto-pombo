package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User newUser) throws PruuException {
        return repository.save(newUser);
    }

    public boolean deleteById(String id) throws PruuException {
        repository.deleteById(id);
        return true;
    }

    public User findById(String id) throws PruuException {
        return repository.findById(id).orElseThrow(() -> new PruuException("User not found!"));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User update(User user) {
        return repository.save(user);
    }
}
