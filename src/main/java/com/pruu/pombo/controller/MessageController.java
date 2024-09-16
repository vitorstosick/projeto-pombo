package com.pruu.pombo.controller;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.selector.MessageSelector;
import com.pruu.pombo.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping
    public List<Message> findAll() {
        List<Message> messages = service.findAll();
        return messages;
    }

    @PostMapping
    public ResponseEntity<Message> save(@Valid @RequestBody Message newMessage) throws PruuException {
        return ResponseEntity.ok(service.save(newMessage));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Message> findById(@PathVariable String id) throws PruuException {
        Message message = service.findById(id);
        return ResponseEntity.ok(message);
    }

    @PutMapping
    public ResponseEntity<Message> update(@Valid @RequestBody Message updatedMessage) {
        return ResponseEntity.ok(service.update(updatedMessage));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws PruuException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/like")
    public ResponseEntity<Void> likeMessage(@RequestParam String messageId, @RequestParam String userId) throws PruuException {
        service.likeMessage(messageId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filters")
    public ResponseEntity<List<Message>> filters(@RequestBody MessageSelector messageSelector) {
        return ResponseEntity.ok(service.filters(messageSelector));
    }

    @PatchMapping("/block")
    public ResponseEntity<Void> blockMessage(@RequestParam String userId, @RequestParam String messageId) throws PruuException {
        service.blockMessage(userId, messageId);
        return ResponseEntity.ok().build();
    }

}
