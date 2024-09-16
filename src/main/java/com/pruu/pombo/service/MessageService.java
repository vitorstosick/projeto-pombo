package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.repository.MessageRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.MessageSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message save(Message newMessage) throws PruuException {
        return messageRepository.save(newMessage);
    }

    public boolean delete(String id) throws PruuException {
        messageRepository.deleteById(id);
        return true;
    }

    public Message findById(String id) throws PruuException {
        return messageRepository.findById(id).orElseThrow(() -> new PruuException("Message not found!"));
    }

    public List<Message> findAll() {

        return messageRepository.findByIsBlockedFalse();
    }

    public Message update(Message message) {
        return messageRepository.save(message);
    }

    public void likeMessage(String messageId, String userId) throws PruuException {

        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new PruuException("Message not found."));

        List<User> likes = message.getLikes();

        if (likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        message.setLikes(likes);
        messageRepository.save(message);

    }

    public List<Message> filters(MessageSelector messageSelector) {
        if (messageSelector.pagination()) {
            int numberPages = messageSelector.getPages();
            int size = messageSelector.getLimit();
            PageRequest page = PageRequest.of(numberPages - 1, size);
            return messageRepository.findAll(messageSelector, page).toList();
        }
        return messageRepository.findAll(messageSelector);
    }

    public void blockMessage(String userId, String messageId) throws PruuException {

        this.isAdmin(userId);

        Message message = messageRepository.findById(messageId).orElseThrow(() -> new PruuException("Message not found."));
        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        message.setBlocked(!message.isBlocked());

        messageRepository.save(message);
    }

    private void isAdmin(String userId) throws PruuException {
        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        if (user.getRole() == Role.USER) {
            throw new PruuException("Not an admin.");
        }
    }
}
