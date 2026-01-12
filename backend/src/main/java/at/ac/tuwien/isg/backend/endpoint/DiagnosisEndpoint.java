package at.ac.tuwien.isg.backend.endpoint;


import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.DiagnosisToPastDto;
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
@RequestMapping(value = "/api/v1/patient/{patientId}/diagnosis")
public class DiagnosisEndpoint {

    private final DocumentationService documentationService;

    public DiagnosisEndpoint(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @GetMapping
    @Operation(description = "Get all diagnosis entries of a patient")
    public List<DiagnosisDto> getAllDiagnoses(@PathVariable(value = "patientId") String patientId) {
        return documentationService.getAllDiagnoses(patientId);
    }

    @GetMapping("past")
    @Operation(description = "Get all old diagnosis entries of a patient")
    public List<DiagnosisDto> getAllPastDiagnoses(@PathVariable(value = "patientId") String patientId) {
        return documentationService.getAllPastDiagnoses(patientId);
    }

    @PostMapping("{diagnosisId}/past")
    @Operation(description = "Set diagnosis to past.")
    public DiagnosisDto setDiagnosisToPastDiagnis(@PathVariable(value = "patientId") String patientId,
                                                  @PathVariable(value = "diagnosisId") String diagnosisId,
                                                  @RequestBody DiagnosisToPastDto diagnosisDto) {
        return documentationService.setDiagnosisToPast(patientId, diagnosisId, diagnosisDto);
    }

    @PostMapping
    @Operation(description = "Create new diagnosis.")
    public DiagnosisDto createDiagnosis(@PathVariable(value = "patientId") String patientId,
                                        @RequestBody DiagnosisCreationDto diagnosisDto) {
        return documentationService.createDiagnosis(patientId, diagnosisDto);
    }

    @DeleteMapping("{diagnosisId}")
    @Operation(description = "Delete diagnosis.")
    public void deleteDiagnosisEntry(@PathVariable(value = "patientId") String patientId,
                                      @PathVariable(value = "diagnosisId") String diagnosisId) {
        documentationService.deleteDiagnosis(patientId, diagnosisId);

    }
}
