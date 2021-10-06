package com.anjsh.dto;

import com.anjsh.entity.Hospital;
import com.anjsh.entity.Office;
import lombok.Data;

import java.util.List;

@Data
public class HosInfoDTO extends DTO{

    private Hospital hospital;

    private List<Office> office;

    private boolean like;
}
