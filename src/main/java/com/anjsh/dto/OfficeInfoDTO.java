package com.anjsh.dto;

import com.anjsh.entity.Doctor;
import com.anjsh.entity.Hospital;
import com.anjsh.entity.Office;
import lombok.Data;

import java.util.List;

@Data
public class OfficeInfoDTO extends DTO{

    private Office office;

    private Hospital hospital;

    private List<Doctor> doctor;
}
