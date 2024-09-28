package com.gescobank.entities;

import com.gescobank.enums.TypeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private Date dateOperation;
    @Column(nullable = false)
    private String numOperation;

    @ManyToOne
    private CompteBancaire compte;
    // enum to get the type of operation you are doing
    @Column(nullable = false)
    private TypeOperation typeOperation;
}
