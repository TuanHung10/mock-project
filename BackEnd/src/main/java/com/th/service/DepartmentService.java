package com.th.service;

import com.th.entity.Department;
import com.th.entity.Department;
import com.th.form.department.CreatingDepartmentForm;
import com.th.form.department.DepartmentFilterForm;
import com.th.form.department.UpdatingDepartmentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {

    public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);

    public void  createDepartment(CreatingDepartmentForm form);

    public void updateDepartment(int id, UpdatingDepartmentForm form);

    public boolean isDepartmentExistsByName(String name);

    public void deleteDepartment(int id);

    public void deleteDepartments(List<Integer> ids);

    public Department getDepartmentByName(String name);

    public Department getDepartmentById(int id);

    public boolean isDepartmentExistsById (int id);

    public List<Department> getNameDepartments();
    
}
