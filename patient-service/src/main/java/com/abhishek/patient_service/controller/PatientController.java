package com.abhishek.patient_service.controller;

import com.abhishek.patient_service.dto.PatientRequestDto;
import com.abhishek.patient_service.dto.PatientResponseDTO;
import com.abhishek.patient_service.dto.validator.CreatePaymentValidationGroup;
import com.abhishek.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient", description = "API for managing Patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    @Operation(summary="Get Patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patientResponseDTOList = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOList);
    }

    @PostMapping
    @Operation(summary="Create a new Patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody @Validated({Default.class, CreatePaymentValidationGroup.class}) PatientRequestDto patientRequestDto) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary="Update a Patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
