package at.ac.tuwien.isg.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {

    @ManyToOne
    @JoinTable(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinTable(name = "sender")
    private ApplicationUser sender;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime sent;

    @Column(nullable = false, length = 2056)
    @NotNull
    private String text;
}
