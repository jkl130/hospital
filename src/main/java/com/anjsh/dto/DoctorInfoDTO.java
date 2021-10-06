package com.anjsh.dto;

import com.anjsh.entity.Doctor;
import com.anjsh.entity.Hospital;
import lombok.Data;

@Data
public class DoctorInfoDTO extends DTO{

    private Doctor doctor;

    private Hospital hospital;
}
