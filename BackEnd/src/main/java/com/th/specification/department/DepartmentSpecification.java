package com.th.specification.department;

import com.th.entity.Department;
import com.th.form.department.DepartmentFilterForm;
import com.th.utils.Utils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class DepartmentSpecification {

    @SuppressWarnings("depreaction")
    public static Specification<Department> buildWhere(String search, DepartmentFilterForm filterForm) {

        Specification<Department> where = null;

        if (!StringUtils.isEmpty(search)) {

            search = Utils.formatSearch(search);

            CustomSpecification2 name = new CustomSpecification2("name", search);
            where = Specification.where(name);

        }

        //if there is filter by Type
        if (filterForm != null && filterForm.getType() != null) {
            CustomSpecification2 type = new CustomSpecification2("type", filterForm.getType());

            if (where == null) {
                where = type;
            } else {
                where = where.and(type);
            }
        }

        return where;
    }
}
