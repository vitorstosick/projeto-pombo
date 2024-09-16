package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.Report;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class ReportSelector extends BaseSelector implements Specification<Report> {

    private String userId;
    private String messageId;
    private String reason;
    private LocalDateTime startDateCreation;
    private LocalDateTime finishDateCreation;

    @Override
    public Predicate toPredicate(Root<Report> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (this.getUserId() != null) {
            predicates.add(cb.equal(root.get("user").get("id"), this.getUserId()));
        }

        if (this.getMessageId() != null) {
            predicates.add(cb.equal(root.get("message").get("id"), this.getMessageId()));
        }

        if (this.getReason() != null) {
            predicates.add(cb.equal(root.get("reason"), this.getReason()));
        }

        dateFilter(root, cb, predicates, this.getStartDateCreation(), this.getFinishDateCreation(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
