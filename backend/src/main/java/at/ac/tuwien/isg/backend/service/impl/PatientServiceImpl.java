package at.ac.tuwien.isg.backend.service.impl;

import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDetailDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientModificationDto;
import at.ac.tuwien.isg.backend.endpoint.mapper.PatientMapper;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.exception.NotFoundException;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.service.PatientService;
import at.ac.tuwien.isg.backend.utils.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final UserContext userContext;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, UserContext userContext, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.userContext = userContext;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDetailDto createPatient(PatientModificationDto patientDto) {
        //check for unique sozVrNr first
        Patient newPatient = patientMapper.toPatientEntity(patientDto);
        newPatient.setUser(userContext.getUser());

        patientRepository.save(newPatient);
        return patientMapper.toPatientDetailDto(newPatient);
    }

    @Override
    @Transactional
    public PatientDetailDto updatePatient(String id, PatientModificationDto patientDto) {
        Patient patient = patientRepository.getPatientById(userContext.getUser(), id).orElseThrow(NotFoundException::new);
        patientMapper.updatePatientEntity(patientDto, patient);
        patientRepository.flush();
        return patientMapper.toPatientDetailDto(patient);
    }

    @Override
    public List<PatientDto> getPatients(String search) {
        String searchString = search != null ? search : "";
        return patientMapper.toPatientDto(patientRepository.getPatients(userContext.getUser(), searchString));
    }

    @Override
    public PatientDetailDto getPatientBySozNr(String sozNr) {
        int sozNum = Integer.parseInt(sozNr.substring(0, 4));
        LocalDate dateOfBirth = LocalDate.parse(sozNr.substring(4, 14));
        return patientMapper.toPatientDetailDto(patientRepository.getPatientBySozNr(
                userContext.getUser(), sozNum, dateOfBirth)
            .orElseThrow(NotFoundException::new));
    }

    @Override
    public PatientDetailDto getPatientById(String id) {
        return patientMapper.toPatientDetailDto(
            patientRepository.getPatientById(
                    userContext.getUser(),
                    id
                )
                .orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public List<PatientDto> getLastPatients() {
        return patientMapper.toPatientDto(patientRepository.getLastModifiedPatients(userContext.getUser()));
    }



}
