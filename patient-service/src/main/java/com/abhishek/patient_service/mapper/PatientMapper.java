package com.abhishek.patient_service.mapper;

import com.abhishek.patient_service.dto.PatientRequestDto;
import com.abhishek.patient_service.dto.PatientResponseDTO;
import com.abhishek.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO modelToDto(Patient patient){
        PatientResponseDTO patientResponseDTO=new PatientResponseDTO();
        patientResponseDTO.setId(String.valueOf(patient.getId()));
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setDateOfBirth(String.valueOf(patient.getDateOfBirth()));

        return patientResponseDTO;

    }

    public static Patient requestDtoToModel(PatientRequestDto patientRequestDto) {
        Patient patient=new Patient();
        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setRegisteredDate(LocalDate.parse(patientRequestDto.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        return patient;
    }
}
