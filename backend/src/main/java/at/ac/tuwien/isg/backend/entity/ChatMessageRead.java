package at.ac.tuwien.isg.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "chat_message_read")
public class ChatMessageRead extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "chat_message")
    private ChatMessage chatMessage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @Column
    private LocalDateTime readDate;
}
