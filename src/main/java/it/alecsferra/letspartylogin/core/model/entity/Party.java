package it.alecsferra.letspartylogin.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Party {

    @Id
    @GeneratedValue
    @Column
    private String id;

    @NotNull
    @Column
    private Date date;

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

}
