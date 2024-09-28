package com.gescobank.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//for enheritance
@DiscriminatorValue("2")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CompteEpargne extends  CompteBancaire implements Serializable {
    private double tauxInteret;
}
