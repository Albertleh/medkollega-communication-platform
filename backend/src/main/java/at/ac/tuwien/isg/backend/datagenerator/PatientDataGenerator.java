package at.ac.tuwien.isg.backend.datagenerator;

import at.ac.tuwien.isg.backend.common.UserRole;
import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.DiagnosisEntry;
import at.ac.tuwien.isg.backend.entity.DocumentationEntry;
import at.ac.tuwien.isg.backend.entity.MedicationEntry;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.repository.DocumentationRepository;
import at.ac.tuwien.isg.backend.repository.PatientRepository;
import at.ac.tuwien.isg.backend.repository.TextEntryRepository;
import at.ac.tuwien.isg.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static at.ac.tuwien.isg.backend.datagenerator.PatientData.ACUTE_DIAGNOSIS;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.ADDRESSES_LIST;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.DIAGNOSES;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.FIRST_NAMES;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.LAST_NAMES;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.MEDICATION_MAP;
import static at.ac.tuwien.isg.backend.datagenerator.PatientData.TEXT_MAP;

@Component
public class PatientDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final Random RANDOM = new Random();


    private final UserDataGenerator userDataGenerator;
    private final PatientRepository patientRepository;
    private final TextEntryRepository textEntryRepository;
    private final DocumentationRepository documentationRepository;
    private final UserRepository userRepository;

    public PatientDataGenerator(UserDataGenerator userDataGenerator, PatientRepository patientRepository,
                                TextEntryRepository textEntryRepository, DocumentationRepository documentationRepository1, UserRepository userRepository) {
        this.userDataGenerator = userDataGenerator;
        this.patientRepository = patientRepository;
        this.textEntryRepository = textEntryRepository;
        this.documentationRepository = documentationRepository1;

        PatientData data = new PatientData();
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void generatePatients() {
        ApplicationUser applicationUser = userDataGenerator
            .generateOrFindUserByMail("doctor@email.com", UserRole.PROFESSIONAL);
        generatePatients(applicationUser);

        applicationUser = userDataGenerator
            .generateOrFindUserByMail("therapist@email.com", UserRole.PROFESSIONAL);
        generatePatients(applicationUser);

        applicationUser = userDataGenerator
            .generateOrFindUserByMail("internist@email.com", UserRole.PROFESSIONAL);
        generatePatients(applicationUser);

        applicationUser = userDataGenerator
            .generateOrFindUserByMail("nurse@email.com", UserRole.PROFESSIONAL);
        generatePatients(applicationUser);


    }

    private void generatePatients(ApplicationUser applicationUser) {
        if (!patientRepository.getPatients(applicationUser, "").isEmpty()) {
            LOGGER.debug("patients already generated");
        } else {
            List<Patient> patients = new ArrayList<>(List.of(
                new Patient(2344, LocalDate.of(2002, 2, 22), "Stella", "Demopatient",
                    "1040", "Wien", "Wiedner Hauptstraße", "3", null, applicationUser),
                new Patient(2345, LocalDate.of(1998, 7, 12), "Markus", "Beispiel",
                    "1010", "Wien", "Kärntner Straße", "10", null, applicationUser),
                new Patient(2346, LocalDate.of(1985, 11, 3), "Julia", "Testperson",
                    "1020", "Wien", "Praterstraße", "5", null, applicationUser),
                new Patient(2347, LocalDate.of(1974, 4, 19), "Thomas", "Mustermann",
                    "1030", "Wien", "Landstraßer Hauptstraße", "77", null, applicationUser)
            ));

            //todo: insert set of fixed diseases for these patients!


            for (int i = 0; i < 15; i++) {
                PatientData.Address address = ADDRESSES_LIST.get(RANDOM.nextInt(ADDRESSES_LIST.size()));
                patients.add(new Patient(
                    RANDOM.nextInt(1000) + 3000,
                    LocalDate.of(RANDOM.nextInt(80) + 1930, RANDOM.nextInt(11) + 1, RANDOM.nextInt(27) + 1),
                    FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size())),
                    LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size())),
                    address.zipCode(),
                    "Wien",
                    address.streetName(),
                    address.streetNumber(),
                    null,
                    applicationUser
                ));
            }


            for (Patient p : patients) {
                patientRepository.save(p);
                generateDiseasesAndMedication(p);

            }

            patientRepository.flush();
        }
    }

    private void generateDiseasesAndMedication(Patient patient) {
        createChronicDiseaseData(patient);
        createPastDisease(patient);
    }

    private void createPastDisease(Patient patient) {
        for (int i = 0; i < RANDOM.nextInt(3); i++) {
            PatientData.Diagnosis pastDiagnosis = ACUTE_DIAGNOSIS.get(RANDOM.nextInt(ACUTE_DIAGNOSIS.size()));

            DiagnosisEntry diagnosisEntry = new DiagnosisEntry();
            diagnosisEntry.setPatient(patient);
            diagnosisEntry.setIcdCode(pastDiagnosis.icd10());
            diagnosisEntry.setDiagnosisText(pastDiagnosis.description());
            diagnosisEntry.setCreatedAt(LocalDate.of(2024, RANDOM.nextInt(11) + 1, RANDOM.nextInt(27) + 1));
            diagnosisEntry.setValidUntil(diagnosisEntry.getCreatedAt().plusDays(RANDOM.nextInt(7)));
            diagnosisEntry.setPastDiagnosis(true);

            documentationRepository.save(diagnosisEntry);
        }
    }

    private Set<PatientData.Diagnosis> createChronicDiseaseData(Patient patient) {
        Set<PatientData.Diagnosis> picked = new HashSet<>();
        for (int i = 0; i < RANDOM.nextInt(4); i++) {
            picked.add(DIAGNOSES.get(RANDOM.nextInt(DIAGNOSES.size())));
        }

        List<String> texts = createMedications(patient, picked);
        createDocEntries(patient, texts);

        return picked;
    }

    private void createDocEntries(Patient patient, List<String> texts) {
        for (String text : texts) {
            DocumentationEntry entry = new DocumentationEntry();
            entry.setPatient(patient);
            entry.setCreatedAt(LocalDateTime.now().minusHours(RANDOM.nextInt(26298)));
            entry.setText(text);

            textEntryRepository.save(entry);
        }
    }

    private List<String> createMedications(Patient patient, Set<PatientData.Diagnosis> picked) {
        Set<PatientData.Medication> meds = new HashSet<>();
        List<String> texts = new ArrayList<>();

        for (PatientData.Diagnosis diagnosis : picked) {
            DiagnosisEntry entry = new DiagnosisEntry();
            entry.setPatient(patient);
            entry.setCreatedAt(LocalDate.now());
            entry.setDiagnosisText(diagnosis.description());
            entry.setIcdCode(diagnosis.icd10());

            documentationRepository.save(entry);

            meds.addAll(MEDICATION_MAP.get(diagnosis));
            texts.addAll(TEXT_MAP.get(diagnosis));
        }

        for (PatientData.Medication medication : meds) {
            MedicationEntry entry = new MedicationEntry();
            entry.setPatient(patient);
            entry.setCreatedAt(LocalDate.now());
            entry.setMedication(medication.description());
            entry.setMorning(medication.morning());
            entry.setNight(medication.night());
            entry.setEvening(medication.evening());
            entry.setMidday(medication.midday());
            entry.setNote(medication.note());
            documentationRepository.save(entry);
        }
        return texts;
    }

}


