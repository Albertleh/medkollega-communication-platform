package at.ac.tuwien.isg.backend.datagenerator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("generateData")
@Component
public class DataGenerator {

    private final UserDataGenerator userDataGenerator;
    private final PatientDataGenerator patientDataGenerator;


    DataGenerator(UserDataGenerator userDataGenerator, PatientDataGenerator patientDataGenerator) {
        this.userDataGenerator = userDataGenerator;
        this.patientDataGenerator = patientDataGenerator;
    }
}
