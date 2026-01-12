package at.ac.tuwien.isg.backend.datagenerator;

import at.ac.tuwien.isg.backend.common.ProfessionalRoleUser;
import at.ac.tuwien.isg.backend.common.UserRole;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.UserContact;
import at.ac.tuwien.isg.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@Component
public class UserDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final List<UserContact> contacts = List.of(
        new UserContact(ProfessionalRoleUser.DOCTOR, "Gruppe1", "ISG", "1040", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "Max", "Hauer", "1010", "Wien", false),
        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Anna", "Kaltenegger", "1020", "Wien", false),
        new UserContact(ProfessionalRoleUser.NURSE, "Lukas", "Bergmann", "1030", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "Sarah", "Steiner", "1040", "Wien", false),
        new UserContact(ProfessionalRoleUser.DIETOLOGIST, "Tim", "Reisinger", "1050", "Wien", false),

        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Marie", "Gruber", "1060", "Wien", false),
        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Jan", "Walcher", "1070", "Wien", false),
        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Lea", "Aigner", "1080", "Wien", false),
        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Paul", "Riegler", "1090", "Wien", false),
        new UserContact(ProfessionalRoleUser.PSYCHOTHERAPIST, "Nina", "Schwaiger", "1100", "Wien", false),

        new UserContact(ProfessionalRoleUser.DOCTOR, "Felix", "Koller", "1110", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "Laura", "Pfister", "1120", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "Jonas", "Schindler", "1130", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "Eva", "Wieser", "1140", "Wien", false),
        new UserContact(ProfessionalRoleUser.DOCTOR, "David", "Fuchs", "1150", "Wien", false),

        new UserContact(ProfessionalRoleUser.NURSE, "Julia", "Brandstetter", "3400", "Klosterneuburg", false),
        new UserContact(ProfessionalRoleUser.DIETOLOGIST, "Thomas", "Schadl", "2320", "Schwechat", false),
        new UserContact(ProfessionalRoleUser.DIETOLOGIST, "Mia", "Toman", "2380", "Perchtoldsdorf", false),
        new UserContact(ProfessionalRoleUser.DIETOLOGIST, "Elias", "Hofbauer", "2340", "Mödling", false),
        new UserContact(ProfessionalRoleUser.DIETOLOGIST, "Sophie", "Lackner", "2201", "Gerasdorf bei Wien", false)
    );

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataGenerator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void generateUsers() {
        if (userRepository.findUserByEmail("doctor@email.com").isPresent()) {
            LOGGER.debug("users already generated");
        } else {
            LOGGER.debug("generating {} user entries", 2);
            userRepository.save(new ApplicationUser("admin@email.com", "test", passwordEncoder.encode("password"), UserRole.ADMIN, saveNewContact(0)));

            userRepository.save(new ApplicationUser("doctor@email.com", "doctor", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(1)));
            userRepository.save(new ApplicationUser("therapist@email.com", "therapist", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(2)));
            userRepository.save(new ApplicationUser("nurse@email.com", "nurse", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(3)));
            userRepository.save(new ApplicationUser("internist@email.com", "internist", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(4)));
            userRepository.save(new ApplicationUser("tim.reisinger@email.com", "timReisinger", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(5)));

            userRepository.save(new ApplicationUser("marie.gruber@email.com", "marieGruber", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(6)));
            userRepository.save(new ApplicationUser("jan.walcher@email.com", "janWalcher", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(7)));
            userRepository.save(new ApplicationUser("lea.aigner@email.com", "leaAigner", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(8)));
            userRepository.save(new ApplicationUser("paul.riegler@email.com", "paulRiegler", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(9)));
            userRepository.save(new ApplicationUser("nina.schwaiger@email.com", "ninaSchwaiger", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(10)));

            userRepository.save(new ApplicationUser("felix.koller@email.com", "felixKoller", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(11)));
            userRepository.save(new ApplicationUser("laura.pfister@email.com", "lauraPfister", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(12)));
            userRepository.save(new ApplicationUser("jonas.schindler@email.com", "jonasSchindler", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(13)));
            userRepository.save(new ApplicationUser("eva.wieser@email.com", "evaWieser", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(14)));
            userRepository.save(new ApplicationUser("david.fuchs@email.com", "davidFuchs", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(15)));

            userRepository.save(new ApplicationUser("julia.brandstetter@email.com", "juliaBrandstetter", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(16)));
            userRepository.save(new ApplicationUser("thomas.schadl@email.com", "thomasSchadl", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(17)));
            userRepository.save(new ApplicationUser("mia.toman@email.com", "miaToman", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(18)));
            userRepository.save(new ApplicationUser("elias.hofbauer@email.com", "eliasHofbauer", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(19)));
            userRepository.save(new ApplicationUser("sophie.lackner@email.com", "sophieLackner", passwordEncoder.encode("password"), UserRole.PROFESSIONAL, saveNewContact(20)));


        }
    }


    public ApplicationUser generateOrFindUserByMail(@Email String email, UserRole role) {
        Optional<ApplicationUser> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            LOGGER.debug("users already generated");
            return user.get();
        } else {
            LOGGER.debug("generating {} user entry", email);
            ApplicationUser newUser = new ApplicationUser();
            newUser.setEmail(email);
            newUser.setUsername(email.split("@")[0]);
            newUser.setRole(role);
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setContact(saveNewContact(1));
            userRepository.save(newUser);
            return newUser;
        }
    }

    private UserContact saveNewContact(int number) {
        UserContact contact = contacts.get(number);
        userRepository.save(contact);
        return contact;
    }

}
