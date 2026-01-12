package at.ac.tuwien.isg.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "patient")
public class Patient extends BaseEntity {

    @Column(length = 4)
    @NotNull
    private int insuranceNumber;

    @Column
    @NotNull
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @NotNull
    private String firstName;

    @Column(nullable = false)
    @NotNull
    private String lastName;

    @Column
    private String zipCode;

    @Column
    private String city;

    @Column
    private String streetName;

    @Column
    private String streetNumber;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    ApplicationUser user;

}
