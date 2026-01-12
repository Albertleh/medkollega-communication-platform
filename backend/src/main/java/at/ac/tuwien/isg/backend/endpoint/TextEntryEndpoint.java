package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryCreationDto;
import at.ac.tuwien.isg.backend.endpoint.dto.documentation.TextEntryDto;
import at.ac.tuwien.isg.backend.service.DocumentationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patient/{patientId}/textEntry")
public class TextEntryEndpoint {

    private final DocumentationService documentationService;

    public TextEntryEndpoint(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @GetMapping
    @Operation(description = "Get all documentation entries of a patient")
    public List<TextEntryDto> getAllTextEntries(@PathVariable(value = "patientId") String patientId) {
        return documentationService.getAllTextEntries(patientId);
    }

    @PostMapping
    @Operation(description = "Create a new documentation entry.")
    public TextEntryDto createTextEntry(@PathVariable(value = "patientId") String patientId, @RequestBody TextEntryCreationDto dto) {
        return documentationService.createTextEntry(patientId, dto);
    }

    @PutMapping("{entryId}")
    @Operation(description = "Update a documentation.")
    public TextEntryDto updateTextEntry(@PathVariable(value = "patientId") String patientId,
                                        @PathVariable(value = "entryId") String entryId,
                                        @RequestBody TextEntryCreationDto dto) {
        return documentationService.updateTextEntry(patientId, entryId, dto);
    }

    @DeleteMapping("{entryId}")
    @Operation(description = "Deletes a documentation entry.")
    public void deleteTextEntry(@PathVariable(value = "patientId") String patientId,
                                @PathVariable(value = "entryId") String entryId) {
        documentationService.deleteTextEntry(patientId, entryId);
    }

}
