package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.UserSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    public User save(User newUser) throws PruuException {
        cpfValidate(newUser);
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

    private void cpfValidate(User user) throws PruuException {
        User existingUser = repository.findByCpf(user.getCpf());

        if (existingUser != null) {
            throw new PruuException("CPF already registred!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    public void isAdmin(String userId) throws PruuException {
        User user = repository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        if (user.getRole() == Role.USER) {
            throw new PruuException("Not an admin.");
        }
    }

}
