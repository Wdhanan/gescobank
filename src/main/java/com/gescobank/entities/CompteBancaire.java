package com.gescobank.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gescobank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
//abstract because two entities(CompteEpargne,CompteCourant) enherits his Attributs
public abstract class CompteBancaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private String numCompte;
    @Column(nullable = false)
    private String devis ="Euros";
    @Column(nullable = false)
    private AccountStatus status;
    private Date createdAt = new Date();

    @ManyToOne
    private Client client;
    @JsonBackReference
    @OneToMany(mappedBy = "compte")
    Collection<Operation> operations = new ArrayList<>();

}
