package at.ac.tuwien.isg.backend.endpoint.mapper;

import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryDto;
import at.ac.tuwien.isg.backend.entity.DiagnosisEntry;
import at.ac.tuwien.isg.backend.entity.DocumentationEntry;
import at.ac.tuwien.isg.backend.entity.MedicationEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface DocumentationMapper {

    TextEntryDto toTextEntryDto(DocumentationEntry documentationEntry);

    List<TextEntryDto> toDtoTextEntries(List<DocumentationEntry> documentationEntries);

    MedicationDto toMedicationDto(MedicationEntry medicationEntry);

    List<MedicationDto> toMedicationDtos(List<MedicationEntry> medicationEntries);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "patient", ignore = true)
    MedicationEntry toEntityMedication(MedicationCreationDto textEntryDto);

    DiagnosisDto toDiagnosisDto(DiagnosisEntry diagnosisEntry);

    List<DiagnosisDto> toDiagnosisDtos(List<DiagnosisEntry> diagnosisEntries);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "patient", ignore = true)
    DiagnosisEntry toEntityDiagnosis(DiagnosisCreationDto diagnosisCreationDto);


}
