package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class UserSelector extends BaseSelector implements Specification<User> {

    private String name;
    private String email;
    private String role;
    private LocalDateTime startDateCreation;
    private LocalDateTime finishDateCreation;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (this.getName() != null && !this.getName().trim().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + this.getName() + "%"));
        }

        if (this.getEmail() != null && !this.getEmail().trim().isEmpty()) {
            predicates.add(cb.like(root.get("email"), "%" + this.getEmail() + "%"));
        }

        if (this.getRole() != null && !this.getRole().trim().isEmpty()) {
            predicates.add(cb.like(root.get("role"), "%" + this.getRole() + "%"));
        }

        dateFilter(root, cb, predicates, this.getStartDateCreation(), this.getFinishDateCreation(), "createdAt");

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
