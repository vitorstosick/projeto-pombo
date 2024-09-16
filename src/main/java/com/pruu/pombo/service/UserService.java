package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.UserSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User newUser) throws PruuException {
        return repository.save(newUser);
    }

    public boolean delete(String id) throws PruuException {
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

    public List<User> filters(UserSelector userSelector) {
        if (userSelector.pagination()) {
            int numberPages = userSelector.getPages();
            int size = userSelector.getLimit();
            PageRequest page = PageRequest.of(numberPages - 1, size);
            return repository.findAll(userSelector, page).toList();
        }
        return repository.findAll(userSelector);
    }
}
