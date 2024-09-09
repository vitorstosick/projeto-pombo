package com.pruu.pombo.model.selector;

import com.pruu.pombo.model.entity.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class MessageSelector extends BaseSelector implements Specification<Message> {

    private String text;
    private LocalDate firstDate;
    private LocalDate lastDate;

    public boolean hasFilter() {
        return(this.validString(this.text)
                || this.firstDate != null
                || this.lastDate != null);
    }

    @Override
    public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if(this.getText() != null && this.getText().trim().length() > 0) {
            // WHERE/AND COLUNA OPERADOR VALOR
            // WHERE      nome   like    '%Popó%'
            predicates.add(cb.like(root.get("text"), "%" + this.getText() + "%"));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
