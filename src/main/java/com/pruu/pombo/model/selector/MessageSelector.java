package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class MessageSelector extends BaseSelector implements Specification<Message> {

    private String text;
    private LocalDateTime startDateCreation;
    private LocalDateTime finishDateCreation;

    public boolean hasFilter() {
        return (this.validString(this.text)
                || this.startDateCreation != null
                || this.finishDateCreation != null);
    }

    @Override
    public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (this.getText() != null && !this.getText().trim().isEmpty()) {
            predicates.add(cb.like(root.get("text"), "%" + this.getText() + "%"));
        }

        dateFilter(root, cb, predicates, this.getStartDateCreation(), this.getFinishDateCreation(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
