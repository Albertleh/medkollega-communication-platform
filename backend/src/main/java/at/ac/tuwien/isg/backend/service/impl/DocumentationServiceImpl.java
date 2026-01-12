package at.ac.tuwien.isg.backend.service.impl;

import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisToPastDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryDto;
import at.ac.tuwien.isg.backend.endpoint.mapper.DocumentationMapper;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.DiagnosisEntry;
import at.ac.tuwien.isg.backend.entity.DocumentationEntry;
import at.ac.tuwien.isg.backend.entity.MedicationEntry;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.exception.NotFoundException;
import at.ac.tuwien.isg.backend.repository.DocumentationRepository;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.repository.TextEntryRepository;
import at.ac.tuwien.isg.backend.service.DocumentationService;
import at.ac.tuwien.isg.backend.utils.UserContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentationServiceImpl implements DocumentationService {

    private final UserContext userContext;
    private final DocumentationRepository documentationRepository;
    private final PatientRepository patientRepository;
    private final DocumentationMapper documentationMapper;
    private final TextEntryRepository textEntryRepository;

    public DocumentationServiceImpl(UserContext userContext, DocumentationRepository documentationRepository, PatientRepository patientRepository, DocumentationMapper documentationMapper, TextEntryRepository textEntryRepository) {
        this.userContext = userContext;
        this.documentationRepository = documentationRepository;
        this.patientRepository = patientRepository;
        this.documentationMapper = documentationMapper;
        this.textEntryRepository = textEntryRepository;

    }

    @Override
    public List<TextEntryDto> getAllTextEntries(String patientId) {
        ApplicationUser user = userContext.getUser();
        return documentationMapper.toDtoTextEntries(
            textEntryRepository.getAllDocumentationEntries(
                user,
                patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new)
            )
        );
    }

    @Override
    public TextEntryDto createTextEntry(String patientId, TextEntryCreationDto dto) {
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new);

        DocumentationEntry entry = new DocumentationEntry();
        entry.setPatient(patient);
        entry.setCreatedAt(LocalDateTime.now());
        entry.setText(dto.text());

        textEntryRepository.save(entry);
        return documentationMapper.toTextEntryDto(entry);
    }

    @Override
    @Transactional
    public TextEntryDto updateTextEntry(String patientId, String entryId, TextEntryCreationDto dto) {
        ApplicationUser user = userContext.getUser();
        DocumentationEntry entry = textEntryRepository.getDocumentationEntry(entryId, user, patientId).orElseThrow(NotFoundException::new);
        entry.setText(dto.text());
        entry.setUpdatedAt(LocalDateTime.now());
        documentationRepository.flush();
        return documentationMapper.toTextEntryDto(entry);
    }

    @Override
    @Transactional
    public void deleteTextEntry(String patientId, String entryId) {
        ApplicationUser user = userContext.getUser();
        DocumentationEntry entry = textEntryRepository.getDocumentationEntry(entryId, user, patientId).orElseThrow(NotFoundException::new);
        documentationRepository.delete(entry);
    }

    @Override
    public List<MedicationDto> getAllMedicationEntries(String patientId) {
        ApplicationUser user = userContext.getUser();
        return documentationMapper.toMedicationDtos(documentationRepository.getAllMedicationEntries(
            user,
            patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new)
        ));
    }

    @Override
    public MedicationDto createMedication(String patientId, MedicationCreationDto dto) {
        MedicationEntry entity = documentationMapper.toEntityMedication(dto);
        entity.setCreatedAt(LocalDate.now());
        ApplicationUser user = userContext.getUser();
        Patient patient = patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new);
        entity.setPatient(patient);

        documentationRepository.save(entity);
        return documentationMapper.toMedicationDto(entity);
    }

    @Override
    @Transactional
    public void deleteMedication(String patientId, String medicationId) {
        ApplicationUser user = userContext.getUser();
        MedicationEntry entity = documentationRepository.getMedicationEntry(medicationId, user, patientId).orElseThrow(NotFoundException::new);
        documentationRepository.delete(entity);
    }

    @Override
    public List<DiagnosisDto> getAllDiagnoses(String patientId) {
        ApplicationUser user = userContext.getUser();
        return documentationMapper.toDiagnosisDtos(
            documentationRepository.getAllValidDiagnoses(
                user,
                patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new),
                false)
        );
    }

    @Override
    public List<DiagnosisDto> getAllPastDiagnoses(String patientId) {
        ApplicationUser user = userContext.getUser();
        return documentationMapper.toDiagnosisDtos(
            documentationRepository.getAllValidDiagnoses(
                user,
                patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new),
                true)
        );
    }

    @Override
    @Transactional
    public DiagnosisDto setDiagnosisToPast(String patientId, String diagnosisId, DiagnosisToPastDto diagnosisDto) {
        ApplicationUser user = userContext.getUser();
        DiagnosisEntry diagnosisEntry = documentationRepository.getDiagnosis(
            diagnosisId, user, patientId
        ).orElseThrow(NotFoundException::new);

        if (diagnosisEntry.isPastDiagnosis()) {
            throw new IllegalStateException("Past diagnosis cannot be set to past diagnosis");
        }

        diagnosisEntry.setPastDiagnosis(true);
        diagnosisEntry.setValidUntil(diagnosisDto.validUntil());

        documentationRepository.flush();
        return documentationMapper.toDiagnosisDto(diagnosisEntry);
    }

    @Override
    public DiagnosisDto createDiagnosis(String patientId, DiagnosisCreationDto diagnosisDto) {
        DiagnosisEntry entity = documentationMapper.toEntityDiagnosis(diagnosisDto);
        entity.setCreatedAt(LocalDate.now());

        ApplicationUser user = userContext.getUser();
        entity.setPatient(patientRepository.getPatientById(user, patientId).orElseThrow(NotFoundException::new));

        documentationRepository.save(entity);

        return documentationMapper.toDiagnosisDto(entity);
    }

    @Override
    @Transactional
    public void deleteDiagnosis(String patientId, String diagnosisId) {
        ApplicationUser user = userContext.getUser();
        DiagnosisEntry diagnosisEntry = documentationRepository.getDiagnosis(
            diagnosisId, user, patientId
        ).orElseThrow(NotFoundException::new);
        documentationRepository.delete(diagnosisEntry);
    }

}
