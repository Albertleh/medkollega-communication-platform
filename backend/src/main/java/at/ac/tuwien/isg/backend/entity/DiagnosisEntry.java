package at.ac.tuwien.isg.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diagnosis_entry")
public class DiagnosisEntry extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(nullable = false)
    @NotNull
    private LocalDate createdAt;

    @Column(nullable = false)
    @NotNull
    private String diagnosisText;

    @Column
    private String icdCode;

    @Column
    private boolean pastDiagnosis;

    @Column
    private LocalDate validUntil;
}
