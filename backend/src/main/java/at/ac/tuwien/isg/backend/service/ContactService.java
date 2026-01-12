package at.ac.tuwien.isg.backend.service;

import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactDto;
import at.ac.tuwien.isg.backend.endpoint.dto.user.ContactUpdateDto;

import java.util.List;

public interface ContactService {

    ContactDto getPersonalSettings();

    ContactDto updatePersonalSettings(ContactUpdateDto contactDto);

    List<ContactDto> getAllUsersOfTheSystem(String search, String patientId);
}


