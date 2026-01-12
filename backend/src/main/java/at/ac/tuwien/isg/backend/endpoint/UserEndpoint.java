package at.ac.tuwien.isg.backend.endpoint;

import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactDto;
import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactUpdateDto;
import at.ac.tuwien.isg.backend.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserEndpoint {

    private final ContactService contactService;

    public UserEndpoint(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("contact")
    @Operation(description = "Get the contact information for the user logged in.")
    public ContactDto getContactInformation() {
        return contactService.getPersonalSettings();
    }

    @PutMapping("contact")
    @Operation(description = "Update the contact information for the user logged in.")
    public ContactDto updateContactInformation(@RequestBody ContactUpdateDto contactUpdateDto) {
        return contactService.updatePersonalSettings(contactUpdateDto);
    }


    @GetMapping
    @Operation(description = "Get all users of the system.")
    public List<ContactDto> getAllUsers(@RequestParam(required = false, name = "search") String search,
                                        @RequestParam(required = false, name = "patientId") String patientId) {
        return contactService.getAllUsersOfTheSystem(search, patientId);
    }
}
