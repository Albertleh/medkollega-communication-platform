package at.ac.tuwien.isg.backend.service;

import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDetailDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientModificationDto;

import java.util.List;

public interface PatientService {

    //create patient
    PatientDetailDto createPatient(PatientModificationDto patientDto);

    //update patient
    PatientDetailDto updatePatient(String id, PatientModificationDto patientDto);

    //get all patients
    List<PatientDto> getPatients(String search);

    //get patient by sozNr
    PatientDetailDto getPatientBySozNr(String sozNr);

    PatientDetailDto getPatientById(String id);

    List<PatientDto> getLastPatients();
    //delete patient

}
