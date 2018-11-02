package it.alecsferra.letspartylogin.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Party {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @NotNull
    @Column
    private String date;

    @ManyToOne
    @NotNull
    private User creator;

    @NotBlank
    @Column
    private String title;

    @NotBlank
    @Column
    private String description;

    @NotBlank
    @Column
    private String province;

    @ManyToMany
    @JoinTable(name = "partecipants",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "party_id") })
    private Set<User> participants;

}
