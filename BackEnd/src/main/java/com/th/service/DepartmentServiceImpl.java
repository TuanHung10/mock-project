package com.th.service;

import com.th.entity.Department;

import com.th.form.department.CreatingDepartmentForm;
import com.th.form.department.DepartmentFilterForm;
import com.th.form.department.UpdatingDepartmentForm;
import com.th.repository.DepartmentRepository;
import com.th.specification.department.DepartmentSpecification;
import org.modelmapper.ModelMapper;

import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

        Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);

        return departmentRepository.findAll(where, pageable);
    }

    @Override
    public void createDepartment(CreatingDepartmentForm form) {
//omit id field
        TypeMap<CreatingDepartmentForm, Department> typeMap = modelMapper.getTypeMap(CreatingDepartmentForm.class, Department.class);

        if (typeMap == null) { //if not already added
            //skip field
            modelMapper.addMappings(new PropertyMap<CreatingDepartmentForm, Department>() {

                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        //convert form to entity
        Department department = modelMapper.map(form, Department.class);
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(int id, UpdatingDepartmentForm form) {
        Department department = getDepartmentById(id);
        department.setName(form.getName());
        departmentRepository.save(department);
    }

    @Override
    public boolean isDepartmentExistsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public void deleteDepartments(List<Integer> ids) {
        departmentRepository.deleteByIds(ids);
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public boolean isDepartmentExistsById(int id) {
        return departmentRepository.existsById(id);
    }

    @Override
    public List<Department> getNameDepartments() {
        return departmentRepository.findAll();
    }
}
