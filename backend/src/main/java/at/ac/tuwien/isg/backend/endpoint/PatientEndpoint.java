package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDetailDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientDto;
import at.ac.tuwien.isg.backend.endpoint.dto.patient.PatientModificationDto;
import at.ac.tuwien.isg.backend.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/patients")
public class PatientEndpoint {

    private final PatientService patientService;

    public PatientEndpoint(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDto> getAllPatient(
        @RequestParam(required = false, name = "search") String search
    ) {
        System.out.println("Patients received" + patientService.getPatients(search));
        return patientService.getPatients(search);
    }

    @GetMapping(path = "{patientId}")
    public PatientDetailDto getPatientById(@PathVariable("patientId") String patientId) {
        return patientService.getPatientById(patientId);
    }

    //example sozNr: "23442002-02-22"
    @GetMapping(path = "bySozNr")
    public PatientDetailDto getPatientBySozNr(@RequestParam(name = "sozNr") String sozNr) {
        return patientService.getPatientBySozNr(sozNr);
    }

    @PostMapping
    public PatientDetailDto createPatient(@Valid @NotNull @RequestBody PatientModificationDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @PutMapping(path = "{patientId}")
    public PatientDetailDto updatePatient(@PathVariable("patientId") String patientId,
                                          @RequestBody PatientModificationDto patientDto) {
        return patientService.updatePatient(patientId, patientDto);
    }

    @GetMapping(path = "last")
    public List<PatientDto> getLastPatients() {
        return patientService.getLastPatients();
    }

}
