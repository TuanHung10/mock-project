package com.th.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private int id;

    private String name;

    private int totalMember;

    private String type;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate;

}
