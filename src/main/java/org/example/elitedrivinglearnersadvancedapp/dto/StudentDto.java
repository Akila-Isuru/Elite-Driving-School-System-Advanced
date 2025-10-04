package org.example.elitedrivinglearnersadvancedapp.dto;

import java.util.Date;
import java.util.Set;

public class StudentDto {
    private Integer studentId;
    private String name;
    private String address;
    private String contactNumber;
    private Date registrationDate;
    private Set<String> courseIds;
}