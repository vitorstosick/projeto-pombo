package com.pruu.pombo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportDTO {

    private String messageId;
    private Integer totalReports;
    private Integer pendingReports;
    private Integer analyzedReports;
}
