package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.repository.MessageRepository;
import com.pruu.pombo.model.repository.UserRepository;
import com.pruu.pombo.model.selector.MessageSelector;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean deleteById(String id) throws PruuException {
        userRepository.deleteById(id);
        return true;
    }

    public Message findById(String id) throws PruuException {
        return messageRepository.findById(id).orElseThrow(() -> new PruuException("Message not found!"));
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message update(Message message) {
        return messageRepository.save(message);
    }

    public void likeMessage(String messageId, String userId) {

        Message message = messageRepository.findById(messageId).orElse(null);
        List<User> likes = message.getLikes();

        User user = userRepository.findById(userId).orElse(null);

        if (likes.contains(user)) {
            likes.remove(user);
        } else {
            likes.add(user);
        }

        message.setLikes(likes);
        messageRepository.save(message);

    }
}
