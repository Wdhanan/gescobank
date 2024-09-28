package com.gescobank.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String prenom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String telephone;
    private String email;
    private String address;
    @OneToMany(mappedBy = "client")
    private Collection<CompteBancaire> comptes = new ArrayList<>();
}
