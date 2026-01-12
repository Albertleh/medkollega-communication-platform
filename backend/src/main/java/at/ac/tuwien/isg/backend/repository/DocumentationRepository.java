package at.ac.tuwien.isg.backend.repository;

import at.ac.tuwien.isg.backend.entity.ApplicationUser;
import at.ac.tuwien.isg.backend.entity.DiagnosisEntry;
import at.ac.tuwien.isg.backend.entity.MedicationEntry;
import at.ac.tuwien.isg.backend.entity.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static at.ac.tuwien.isg.backend.entity.QApplicationUser.applicationUser;
import static at.ac.tuwien.isg.backend.entity.QDiagnosisEntry.diagnosisEntry;
import static at.ac.tuwien.isg.backend.entity.QMedicationEntry.medicationEntry;
import static at.ac.tuwien.isg.backend.entity.QPatient.patient;

@Repository
public class DocumentationRepository extends AbstractBaseRepository {

    public List<MedicationEntry> getAllMedicationEntries(ApplicationUser user, Patient pat) {
        return getResults(query(medicationEntry)
            .leftJoin(medicationEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.eq(pat))
            .orderBy(medicationEntry.createdAt.desc()));
    }


    public Optional<MedicationEntry> getMedicationEntry(String entryId, ApplicationUser user, String patientId) {
        return getResult(query(medicationEntry)
            .leftJoin(medicationEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.id.eq(patientId), medicationEntry.id.eq(entryId)));
    }

    public List<DiagnosisEntry> getAllValidDiagnoses(ApplicationUser user, Patient pat, boolean past) {
        return getResults(query(diagnosisEntry)
            .leftJoin(diagnosisEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.eq(pat), diagnosisEntry.pastDiagnosis.eq(past))
            .orderBy(diagnosisEntry.createdAt.desc()));
    }

    public Optional<DiagnosisEntry> getDiagnosis(String entryId, ApplicationUser user, String patientId) {
        return getResult(query(diagnosisEntry)
            .leftJoin(diagnosisEntry.patient, patient)
            .leftJoin(patient.user, applicationUser)
            .where(applicationUser.eq(user), patient.id.eq(patientId), diagnosisEntry.id.eq(entryId)));
    }
}
