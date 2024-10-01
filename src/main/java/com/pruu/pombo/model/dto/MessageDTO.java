package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDTO {

    private String id;
    private String text;
    private boolean isBlocked;
    private String userId;
    private String username;
    private Integer likes;
    private Integer reports;

}
