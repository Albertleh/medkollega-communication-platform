package at.ac.tuwien.isg.backend.endpoint.mapper;

import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDetailDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientModificationDto;
import at.ac.tuwien.isg.backend.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface PatientMapper {

    PatientDto toPatientDto(Patient patient);

    List<PatientDto> toPatientDto(List<Patient> patients);

    PatientDetailDto toPatientDetailDto(Patient patient);

    Patient toPatientEntity(PatientModificationDto patientDto);

    void updatePatientEntity(PatientModificationDto patientDto, @MappingTarget Patient patient);
}
