package com.th.form.department;

import com.th.entity.Department;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatingDepartmentForm  {

    private String name;

    private Department.Type type;

}
