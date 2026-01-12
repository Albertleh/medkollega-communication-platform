package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.DocumentationEntry;
import at.ac.tuwien.isg.backend.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.entity.QApplicationUser.applicationUser;
import static at.ac.tuwien.isg.backend.entity.QDocumentationEntry.documentationEntry;
import static at.ac.tuwien.isg.backend.entity.QPatient.patient;

@Repository
public class TextEntryRepository extends AbstractBaseRepository {

    public List<DocumentationEntry> getAllDocumentationEntries(ApplicationUser user, Patient pat) {
        return getResults(query(documentationEntry)
            .leftJoin(documentationEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.eq(pat))
            .orderBy(documentationEntry.createdAt.desc()));
    }

    public Optional<DocumentationEntry> getDocumentationEntry(String entryId, ApplicationUser user, String patientId) {
        return getResult(query(documentationEntry)
            .leftJoin(documentationEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.id.eq(patientId), documentationEntry.id.eq(entryId)));
    }
}
