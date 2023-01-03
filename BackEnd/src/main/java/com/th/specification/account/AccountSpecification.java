package com.th.specification.account;

import com.th.entity.Account;
import com.th.form.account.AccountFilterForm;
import com.th.utils.Utils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AccountSpecification {

    @SuppressWarnings("deprecation")
    public static Specification<Account> buildWhere(String search, AccountFilterForm filterForm) {

        Specification<Account> where = null;

        if (!StringUtils.isEmpty(search)) {

            search = Utils.formatSearch(search);

            CustomSpecification username = new CustomSpecification("username", search);
            CustomSpecification fullName = new CustomSpecification("fullName", search);
            where = Specification.where(username).or(fullName);
        }
        //if there is filter by Role
        if (filterForm != null && filterForm.getRole() != null) {
            CustomSpecification role = new CustomSpecification("role", filterForm.getRole());

            if (where == null) {
                where = role;
            } else {
                where = where.and(role);
            }
        }

// if there is filter by department name
        if (filterForm != null && filterForm.getDepartmentName() != null) {
            CustomSpecification departmentName = new CustomSpecification("departmentName", filterForm.getDepartmentName());
            if (where == null) {
                where = departmentName;
            } else {
                where = where.and(departmentName);
            }
        }

        return where;
    }
}
