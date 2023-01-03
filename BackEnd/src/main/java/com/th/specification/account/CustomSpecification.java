package com.th.specification.account;

import com.th.entity.Account;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@SuppressWarnings("serial")
@RequiredArgsConstructor
public class CustomSpecification implements Specification<Account> {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (field.equalsIgnoreCase("username")) {
            return criteriaBuilder.like(root.get("username"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("fullName")) {
            return criteriaBuilder.like(root.get("fullName"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("role")) {
            return criteriaBuilder.equal(root.get("role"), value);
        }

        if (field.equalsIgnoreCase("departmentName")) {
            return criteriaBuilder.equal(root.get("department").get("name"), value);
        }
        return null;
    }
}
