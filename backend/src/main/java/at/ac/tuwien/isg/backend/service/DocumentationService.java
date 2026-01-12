package at.ac.tuwien.isg.backend.service;

import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisToPastDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryDto;

import java.util.List;

public interface DocumentationService {

    List<TextEntryDto> getAllTextEntries(String patientId);
    //get all documentation entries

    TextEntryDto createTextEntry(String patientId, TextEntryCreationDto dto);
    //create documentation entry

    TextEntryDto updateTextEntry(String patientId, String entryId, TextEntryCreationDto dto);
    //update documentation entry

    void deleteTextEntry(String patientId, String entryId);
    //delete documentation entry



    List<MedicationDto> getAllMedicationEntries(String patientId);

    MedicationDto createMedication(String patientId, MedicationCreationDto dto);

    void deleteMedication(String patientId, String medicationId);


    List<DiagnosisDto> getAllDiagnoses(String patientId);

    List<DiagnosisDto> getAllPastDiagnoses(String patientId);

    DiagnosisDto setDiagnosisToPast(String patientId, String diagnosisId, DiagnosisToPastDto diagnosisDto);

    DiagnosisDto createDiagnosis(String patientId, DiagnosisCreationDto diagnosisDto);

    void deleteDiagnosis(String patientId, String diagnosisId);
}
