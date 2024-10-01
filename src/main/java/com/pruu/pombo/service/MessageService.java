package com.pruu.pombo.service;

import com.pruu.pombo.exception.PruuException;
import com.pruu.pombo.model.dto.MessageDTO;
import com.pruu.pombo.model.entity.Message;
import com.pruu.pombo.model.entity.Report;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Role;
import com.pruu.pombo.model.enums.Status;
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

        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new PruuException("Message not found."));

        this.isAdmin(userId);

        List<Report> messageReports = message.getReports();
        boolean hasAnalyzedReport = false;

        for(Report report : messageReports) {
            if(report.getStatus() == Status.ANALYZED) {
                hasAnalyzedReport = true;
                break;
            }
        }

        if(hasAnalyzedReport) {
            message.setBlocked(true);
            messageRepository.save(message);
        } else {
            throw new PruuException("The message needs at least one proven report");
        }
    }

    private void isAdmin(String userId) throws PruuException {
        User user = userRepository.findById(userId).orElseThrow(() -> new PruuException("User not found."));

        if (user.getRole() == Role.USER) {
            throw new PruuException("Not an admin.");
        }
    }

    public List<MessageDTO> findAllMessagesDTO() {
        List<Message> messages = messageRepository.findByIsBlockedFalse();

        return messages.stream()
                .map(message -> {
                    int likeCount = message.getLikes() != null ? message.getLikes().size() : 0;

                    int reportCount = message.getReports() != null ? message.getReports().size() : 0;

                    return Message.toDTO(message, likeCount, reportCount);
                })
                .toList();
    }

    public MessageDTO findMessageDTOById(String messageId) throws PruuException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new PruuException("Message not found"));

        if(message.isBlocked()) {
            throw new PruuException("Blocked by ADMIN");
        }

        int likeCount = message.getLikes().size();

        int reportCount = message.getReports().size();

        return Message.toDTO(message, likeCount, reportCount);
    }

}
