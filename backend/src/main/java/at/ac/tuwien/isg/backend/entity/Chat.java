package at.ac.tuwien.isg.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "creator")
    private ApplicationUser professionalOne;

    @ManyToOne
    @JoinColumn(name = "creator_patient")
    private Patient patient;

    @Column
    private boolean groupDocumentation;

    @Column
    private String groupName;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true,
        fetch = FetchType.EAGER)
    private List<ChatParticipant> combinations = new ArrayList<>();

    public void addCombination(ApplicationUser user, Patient userPatient, LocalDate createdAt) {
        ChatParticipant comb = new ChatParticipant(this, user, userPatient, createdAt, false, false, false);
        combinations.add(comb);
    }
}
