package com.gescobank.service;

import com.gescobank.dto.OperationDto;
import com.gescobank.entities.CompteBancaire;
import com.gescobank.entities.Operation;

import java.util.List;

import com.gescobank.dto.OperationDto;
import com.gescobank.entities.CompteBancaire;
import com.gescobank.entities.Operation;
import com.gescobank.enums.AccountStatus;
import com.gescobank.enums.TypeOperation;
import com.gescobank.repository.CompteBancaireRepository;
import com.gescobank.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OperationService {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    CompteBancaireRepository compteBancaireRepository;

    public CompteBancaire effectuerVersement(OperationDto dto){

        //optional to see if the Account does exist in our database
        Optional<CompteBancaire> compteOpt = compteBancaireRepository.findByNumCompte(dto.getNumCompte());
        if(compteOpt.isPresent()){
            // get the account when the optional is present
            CompteBancaire compte = compteOpt.get();
            //can the account make a transaction(means the Account is activated)
            if(compte.getStatus().equals(AccountStatus.ACTIVATED)){
                compte.setBalance(compte.getBalance() + dto.getAmount()); // new Balance after inserting money
                Operation operation = new Operation();
                operation.setDateOperation(new Date());
                operation.setAmount(dto.getAmount());
                operation.setCompte(compte);
                operation.setTypeOperation(TypeOperation.CREDIT);
                operation.setNumOperation(generateAccountNumber());

                // save the operation
                operationRepository.save(operation);
                //update the account infos
                compteBancaireRepository.save(compte);
                return compte;

            }


            else {
                throw new RuntimeException("This Account is suspended");
            }
        }
        else {
            throw new RuntimeException("This Account does not exists");
        }
    }


    public CompteBancaire effectuerRetrait(OperationDto dto){
        //optional to see if the Account does exist in our database
        Optional<CompteBancaire> compteOpt = compteBancaireRepository.findByNumCompte(dto.getNumCompte());
        if(compteOpt.isPresent()){
            // get the account when the optional is present
            CompteBancaire compte = compteOpt.get();
            //can the account make a transaction(means the Account is activated) & the Balance > Amount to withdraw"
            if(compte.getStatus().equals(AccountStatus.ACTIVATED) && (compte.getBalance() > dto.getAmount())){
                compte.setBalance(compte.getBalance() - dto.getAmount()); // new Balance after withdrawing money
                Operation operation = new Operation();
                operation.setDateOperation(new Date());
                operation.setAmount(dto.getAmount());
                operation.setCompte(compte);
                operation.setTypeOperation(TypeOperation.DEBIT);
                operation.setNumOperation(generateAccountNumber());

                // save the operation
                operationRepository.save(operation);
                //update the account infos
                compteBancaireRepository.save(compte);
                return compte;

            } else if ((compte.getStatus().equals(AccountStatus.ACTIVATED) && (compte.getBalance() < dto.getAmount()))) {
                throw new RuntimeException("Your Balance is lower than the Amount you want to withdraw");

            } else {
                throw new RuntimeException("This Account is suspended");
            }
        }
        else {
            throw new RuntimeException("This Account does not exists");
        }

    }
    public boolean effectuerVirement(OperationDto dto){
        // it two operations between a "source" and a "destination" Account

        String numCompteSource = dto.getNumCompteS();

        OperationDto dtoSource = new OperationDto(numCompteSource,null, dto.getAmount());
        // make a withdraw from the source account
        CompteBancaire compteBancaireSource = effectuerRetrait(dtoSource);


        if(compteBancaireSource != null){
            String numCompteDestination = dto.getNumCompteD();
            OperationDto dtoDestination = new OperationDto(null,numCompteDestination, dto.getAmount());
            // insert Money into the  Destination Account
            effectuerVersement(dtoDestination);

            return true;


        }
        return false;

    }

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

    public List<Operation> findByClientNumCompte(String numCompte) {
        List<Operation> list =  new ArrayList<>();
        for(Operation o:operationRepository.findAll()){
            if(o.getCompte().getNumCompte().equals(numCompte)){
                list.add(o);
            }
        }
        return list;
    }

}
