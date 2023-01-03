package com.th.controller;

import com.th.dto.entity.DepartmentDTO;
import com.th.entity.Department;
import com.th.form.department.CreatingDepartmentForm;
import com.th.form.department.DepartmentFilterForm;
import com.th.form.department.UpdatingDepartmentForm;
import com.th.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/departments")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping()
    public ResponseEntity<?> getAllDepartments(Pageable pageable,
                                               @RequestParam(value = "search", required = false) String search,
                                               DepartmentFilterForm filterForm) {

        Page<Department> entityPages = departmentService.getAllDepartments(pageable, search, filterForm);

//convert entities -> DTOs
        List<DepartmentDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<DepartmentDTO>>() {
        }.getType());

        Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return new ResponseEntity<>(dtoPages, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<?> getNameDepartments() {

       List<Department> departments = departmentService.getNameDepartments();

       List<DepartmentDTO> dtos = modelMapper.map(departments, new TypeToken<List<DepartmentDTO>>(){
       }.getType());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDepartmentByID(@PathVariable(name = "id") int id) {
        Department entity = departmentService.getDepartmentById(id);

        DepartmentDTO dto = modelMapper.map(entity, DepartmentDTO.class);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createDepartment(@RequestBody CreatingDepartmentForm form) {
        departmentService.createDepartment(form);
        return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/name/{name}/exists")
    public ResponseEntity<?> existsByName(@PathVariable(name = "name") String name) {
        return new ResponseEntity<>(departmentService.isDepartmentExistsByName(name), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable(name = "id") int id,
                                              @RequestBody UpdatingDepartmentForm form) {
        departmentService.updateDepartment(id, form);
        return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(name = "id") int id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDepartments(@RequestParam(name = "ids") List<Integer> ids) {
        departmentService.deleteDepartments(ids);
        return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
    }

}
