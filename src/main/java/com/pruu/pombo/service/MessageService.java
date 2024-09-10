package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Message save(Message newMessage) throws PruuException {
        return repository.save(newMessage);
    }

    public boolean deleteById(String id) throws PruuException {
        repository.deleteById(id);
        return true;
    }

    public Message findById(String id) throws PruuException {
        return repository.findById(id).orElseThrow(() -> new PruuException("Message not found!"));
    }

    public List<Message> findAll() {
        return repository.findAll();
    }

    public Message update(Message message) {
        return repository.save(message);
    }
}
