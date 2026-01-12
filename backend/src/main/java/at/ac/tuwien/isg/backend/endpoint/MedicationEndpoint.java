package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.MedicationDto;
import at.ac.tuwien.isg.backend.service.DocumentationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patient/{patientId}/medication")
public class MedicationEndpoint {

    private final DocumentationService documentationService;

    public MedicationEndpoint(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @GetMapping
    @Operation(description = "Get all medication entries of a patient")
    public List<MedicationDto> getAllMedicationEntries(@PathVariable(value = "patientId") String patientId) {
        return documentationService.getAllMedicationEntries(patientId);
    }

    @PostMapping
    @Operation(description = "Create a new medication entry.")
    public MedicationDto createNewMedicationEntry(@PathVariable(value = "patientId") String patientId, @RequestBody MedicationCreationDto dto) {
        return documentationService.createMedication(patientId, dto);
    }

    @DeleteMapping("{medicationId}")
    @Operation(description = "Delete medication.")
    public void deleteMedicationEntry(@PathVariable(value = "patientId") String patientId,
                                      @PathVariable(value = "medicationId") String medicationId) {
        documentationService.deleteMedication(patientId, medicationId);

    }
}
