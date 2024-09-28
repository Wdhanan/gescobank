package com.gescobank.dto;

import com.gescobank.enums.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationDto {
    private long compteID;
    private Date dateOperation;
    private double amount;
    private TypeOperation type;

    private String  numCompte;
    private String  numCompteD;
    private String  numCompteS;


    public OperationDto(String numCompteSource, String numCompteDestination, double amount) {
        this.numCompteS = numCompteSource;
        this.numCompteD = numCompteDestination;
        this.amount = amount;

    }
}
