package com.abhishek.patient_service.service;

import com.abhishek.patient_service.dto.PatientRequestDto;
import com.abhishek.patient_service.dto.PatientResponseDTO;
import com.abhishek.patient_service.exception.EmailNotFoundException;
import com.abhishek.patient_service.exception.PatientNotFoundException;
import com.abhishek.patient_service.grpc.BillingServiceGrpcClient;
import com.abhishek.patient_service.kafka.KafkaProducer;
import com.abhishek.patient_service.mapper.PatientMapper;
import com.abhishek.patient_service.model.Patient;
import com.abhishek.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private BillingServiceGrpcClient billingServiceGrpcClient;

    @Autowired
    private KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patientList = patientRepository.findAll();

        return patientList.stream().map(PatientMapper::modelToDto).collect(Collectors.toList());
    }

    public PatientResponseDTO createPatient(PatientRequestDto patientRequestDto){

        if(patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailNotFoundException("Email already exist");
        }
        Patient newPatient=patientRepository.save(PatientMapper.requestDtoToModel(patientRequestDto));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);
        
        return PatientMapper.modelToDto(newPatient);

    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDto patientRequestDto){

        Patient patient = patientRepository.findById(id).
                orElseThrow(()-> new PatientNotFoundException("Patient not found with id: "+ id));

        if(!patient.getEmail().equals(patientRequestDto.getEmail()) && patientRepository.existsByEmail(patientRequestDto.getEmail())){
            throw new EmailNotFoundException("Email already exist");
        }

        patient.setName(patientRequestDto.getName());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.modelToDto(updatedPatient);

    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}
