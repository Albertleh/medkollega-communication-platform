package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.Patient;
import at.ac.tuwien.isg.backend.entity.QDocumentationEntry;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.entity.QDocumentationEntry.documentationEntry;
import static at.ac.tuwien.isg.backend.entity.QPatient.patient;

@Repository
public class PatientRepository extends AbstractBaseRepository {

    public List<Patient> getPatients(ApplicationUser applicationUser, String search) {
        return getResults(query(patient).where(patient.user.eq(applicationUser))
            .where(patient.lastName.containsIgnoreCase(search).or(patient.firstName.containsIgnoreCase(search)))
            .orderBy(patient.lastName.asc()));
    }

    public Optional<Patient> getPatientBySozNr(ApplicationUser user, int sozNr, LocalDate dateOfBirth) {
        return getResult(
            query(patient)
                .where(patient.user.eq(user), patient.insuranceNumber.eq(sozNr),
            patient.dateOfBirth.eq(dateOfBirth)));
    }

    public Optional<Patient> getPatientById(ApplicationUser user, String id) {
        return getResult(query(patient).where(patient.user.eq(user), patient.id.eq(id)));
    }

    public List<Patient> getLastModifiedPatients(ApplicationUser applicationUser) {
        QDocumentationEntry documentation1 = new QDocumentationEntry("documentation1");

        return getResults(query(patient)
                .leftJoin(documentationEntry).on(documentationEntry.patient.eq(patient))
            .where(
                patient.user.eq(applicationUser),
                query(documentation1).where(
                    documentation1.patient.eq(patient),
                    documentation1.createdAt.gt(documentationEntry.createdAt)
                ).notExists())
            .groupBy(patient)
            .orderBy(documentationEntry.createdAt.desc()));
    }
}
