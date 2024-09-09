package com.pruu.pombo.model.selector;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BaseSelector {

    private int pages;
    private int offset;

    public BaseSelector() {
        this.offset = 0;
        this.pages = 0;
    }

    public boolean pagination() {
        return this.offset > 0 && this.pages > 0;
    }

    public boolean validString(String text) {
        return text != null && !text.isBlank();
    }

    public static void aplicarFiltroIntervalo(Root root,
                                              CriteriaBuilder cb, List<Predicate> predicates,
                                              Integer minValue, Integer maxValue, String attributeName) {
        if (minValue != null && maxValue != null) {
            //WHERE atributo BETWEEN min AND max
            predicates.add(cb.between(root.get(attributeName), minValue, maxValue));
        } else if (minValue != null) {
            //WHERE atributo >= min
            predicates.add(cb.greaterThanOrEqualTo(root.get(attributeName), minValue));
        } else if (maxValue != null) {
            //WHERE atributo <= max
            predicates.add(cb.lessThanOrEqualTo(root.get(attributeName), maxValue));
        }
    }

    public static void aplicarFiltroPeriodo(Root root,
                                            CriteriaBuilder cb, List<Predicate> predicates,
                                            LocalDate startDate, LocalDate finishDate, String attributeName) {
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
