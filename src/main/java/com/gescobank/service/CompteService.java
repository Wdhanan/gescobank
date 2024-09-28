package com.gescobank.service;

import com.gescobank.dto.CompteDto;
import com.gescobank.entities.CompteBancaire;
import com.gescobank.entities.CompteCourant;
import com.gescobank.entities.CompteEpargne;

import java.util.List;
import com.gescobank.dto.CompteDto;
import com.gescobank.entities.Client;
import com.gescobank.entities.CompteBancaire;
import com.gescobank.entities.CompteCourant;
import com.gescobank.entities.CompteEpargne;
import com.gescobank.enums.AccountStatus;
import com.gescobank.repository.ClientRepository;
import com.gescobank.repository.CompteBancaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class CompteService {
    @Autowired
    CompteBancaireRepository compteBancaireRepository;
    @Autowired
    ClientRepository clientRepository;

    public void createAccount(CompteDto compteDto){
        Optional<Client> clientOpt = clientRepository.findById(compteDto.getClientId());
        //check if it is a courant compte
        if(clientOpt.isPresent() && (compteDto.getDecouvert() > 0 && compteDto.getTauxInteret() == 0)){
            CompteCourant compteCourant = new CompteCourant();
            compteCourant.setCreatedAt(new Date());
            compteCourant.setBalance(compteDto.getBalance());
            //difference between compte Epargne and Compte Courant
            compteCourant.setDecouvert(compteDto.getDecouvert());
            compteCourant.setClient(clientOpt.get());
            compteCourant.setDevis(compteDto.getDevis());
            compteCourant.setNumCompte(generateAccountNumber());
            //active the account
            compteCourant.setStatus(AccountStatus.ACTIVATED);
            compteBancaireRepository.save(compteCourant);


        }

        //check if it is a epargne compte
        if(clientOpt.isPresent() && (compteDto.getDecouvert() == 0 && compteDto.getTauxInteret() > 0)){
            CompteEpargne compteEpargne = new CompteEpargne();
            compteEpargne.setCreatedAt(new Date());
            compteEpargne.setBalance(compteDto.getBalance());
            //difference between compte Epargne and Compte Courant
            compteEpargne.setTauxInteret(compteDto.getTauxInteret());
            compteEpargne.setClient(clientOpt.get());
            compteEpargne.setDevis(compteDto.getDevis());
            compteEpargne.setNumCompte(generateAccountNumber());
            //active the account
            compteEpargne.setStatus(AccountStatus.ACTIVATED);
            compteBancaireRepository.save(compteEpargne);


        }

    }

    // Method to generate Account Number Randomly
    private static String generateAccountNumber(){

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // the first four number are zero
        for (int i= 0; i < 4; i++){
            sb.append("0");
        }
        //the four following numbers are 0 or 1
        for (int i= 0; i < 4; i++){
            sb.append(random.nextInt(2));
        }
        // the last ten number are randomly generated
        for (int i= 0; i < 10; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public List<CompteEpargne> findComptesEpargne(){
        List<CompteEpargne> list = new ArrayList<>();
        for( CompteBancaire c: compteBancaireRepository.findAll()){
            if(c instanceof  CompteEpargne){
                list.add( (CompteEpargne)c);
            }
        }
        return list;
    }
    public List<CompteCourant> findComptesCourant(){
        List<CompteCourant> list = new ArrayList<>();
        for( CompteBancaire c: compteBancaireRepository.findAll()){
            if(c instanceof  CompteCourant){
                list.add( (CompteCourant)c);
            }
        }
        return list;

    }

    public  CompteBancaire findOne(String numCompte){
        return  this.compteBancaireRepository.findByNumCompte(numCompte).get();
    }


    public CompteEpargne findCompteEpargne(String numCompte) {
        try {
            return (CompteEpargne)compteBancaireRepository.findByNumCompte(numCompte).get();
        }catch (Exception e){
            throw new RuntimeException("Ce compte n'existe pas !");
        }

    }


    public CompteCourant findCompteCourant(String numCompte) {
        try{
            return (CompteCourant)compteBancaireRepository.findByNumCompte(numCompte).get();
        }catch (Exception e){
            throw new RuntimeException("Ce compte n'existe pas !");
        }
    }


    public boolean suspendCompte(String numCompte) {
        Optional<CompteBancaire>  compte = compteBancaireRepository.findByNumCompte(numCompte);
        if(compte.isPresent() && compte.get().getStatus().equals(AccountStatus.ACTIVATED)){
            CompteBancaire c = compte.get();
            c.setStatus(AccountStatus.SUSPENDED);
            compteBancaireRepository.save(c);
            return true;
        }
        return false;
    }


    public boolean activeCompte(String numCompte) {
        Optional<CompteBancaire>  compte = compteBancaireRepository.findByNumCompte(numCompte);
        if(compte.isPresent() && compte.get().getStatus().equals(AccountStatus.SUSPENDED)){
            CompteBancaire c = compte.get();
            c.setStatus(AccountStatus.ACTIVATED);
            compteBancaireRepository.save(c);
            return true;
        }
        return false;
    }
}
