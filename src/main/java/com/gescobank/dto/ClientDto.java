package com.gescobank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String nom;
    private String prenom;
    private Date birthday;
    private String telephone;
    private String email;
    private String address;
}
