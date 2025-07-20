package com.abhishek.patient_service.dto;

import com.abhishek.patient_service.dto.validator.CreatePaymentValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class PatientRequestDto {

    @NotBlank(message="Name is required")
    @Size(max=100, message="Name can't be exceed 100 characters")
    private String name;

    @NotBlank(message="Email is required")
    @Email(message="Enter valid email")
    private String email;

    @NotBlank(message="Address is required")
    private String address;

    @NotBlank(groups = CreatePaymentValidationGroup.class, message="Registered date is required")
    private String registeredDate;

    @NotBlank(message="Date of birth is required")
    private String dateOfBirth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
