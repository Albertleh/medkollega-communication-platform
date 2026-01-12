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
@Table(name = "chat_participant")
public class ChatParticipant extends BaseEntity {

    @JoinColumn(name = "chat")
    @ManyToOne
    @NotNull
    private Chat chat;

    @JoinColumn(name = "user_id")
    @ManyToOne
    @NotNull
    private ApplicationUser user;

    @JoinColumn(name = "patient")
    @ManyToOne
    private Patient patient;

    @Column
    private LocalDate createdAt;

    @Column
    private boolean accepted;

    @Column
    private boolean rejected;

    @Column
    private boolean seen;

}
