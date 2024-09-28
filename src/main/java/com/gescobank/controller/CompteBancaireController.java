package com.gescobank.controller;

import com.gescobank.dto.CompteDto;
import com.gescobank.entities.CompteBancaire;
import com.gescobank.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1")
public class CompteBancaireController {

    @Autowired
    private CompteService compteService;

    @PostMapping("/comptes")
    void createAccount(@RequestBody CompteDto compteDto){
        compteService.createAccount(compteDto);
    }

    @GetMapping("/comptes/type/{type}")
    List<?> findAll(@PathVariable("type") String type){
        if(type.equals("CC")){
            return compteService.findComptesCourant();
        }
        if(type.equals("CE")){
            return compteService.findComptesEpargne();
        }
        return null;

    }
    @GetMapping("/comptes/{numCompte}/{type}")
    ResponseEntity<?> findCompte(@PathVariable("numCompte") String numCompte, @PathVariable("type") String type) {
        if(type.equals("CC"))
            return ResponseEntity.ok(findCompteCourant(numCompte));
        if (type.equals("CE"))
            return ResponseEntity.ok(findCompteEpargne(numCompte));
        return null;

    }

    @GetMapping("/comptes/courant/{numCompte}")
    ResponseEntity<?> findCompteCourant(@PathVariable("numCompte") String numCompte) {
        return ResponseEntity.ok(compteService.findCompteCourant(numCompte));
    }

    @GetMapping("/comptes/epargne/{numCompte}")
    ResponseEntity<?> findCompteEpargne(@PathVariable("numCompte") String numCompte) {
        return ResponseEntity.ok(compteService.findCompteEpargne(numCompte));
    }

    @GetMapping("/comptes/active/{numCompte}")
    ResponseEntity<?> activeCompte(@PathVariable("numCompte") String numCompte) {
        boolean success = compteService.activeCompte(numCompte);
        if (success){
            return  ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/comptes/suspendre/{numCompte}")
    ResponseEntity<?> suspendreCompte(@PathVariable("numCompte") String numCompte) {

        boolean success = compteService.activeCompte(numCompte);
        if (success){
            return  ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }



}
