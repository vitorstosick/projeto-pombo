package com.pruu.pombo.model.selector;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BaseSelector {

    private int pages;
    private int limit;

    public BaseSelector() {
        this.limit = 0;
        this.pages = 0;
    }

    public boolean pagination() {
        return this.limit > 0 && this.pages > 0;
    }

    public boolean validString(String text) {
        return text != null && !text.isBlank();
    }

    public static void dateFilter(Root root,
                                  CriteriaBuilder cb, List<Predicate> predicates,
                                  LocalDateTime startDate, LocalDateTime finishDate, String attributeName) {
        if (startDate != null && finishDate != null) {
            //WHERE atributo BETWEEN min AND max
            predicates.add(cb.between(root.get(attributeName), startDate, finishDate));
        } else if (startDate != null) {
            //WHERE atributo >= min
            predicates.add(cb.greaterThanOrEqualTo(root.get(attributeName), startDate));
        } else if (finishDate != null) {
            //WHERE atributo <= max
            predicates.add(cb.lessThanOrEqualTo(root.get(attributeName), finishDate));
        }
    }

}
