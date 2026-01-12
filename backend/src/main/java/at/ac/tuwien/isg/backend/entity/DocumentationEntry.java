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

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documentation_entry")
public class DocumentationEntry extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "patient")
    private Patient patient;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 2056)
    @NotNull
    private String text;

}
