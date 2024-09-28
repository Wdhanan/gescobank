package com.gescobank.controller;

import com.gescobank.dto.OperationDto;
import com.gescobank.entities.Operation;
import com.gescobank.service.OperationService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @PostMapping("/operations/versement")
    void effectuerVersement(@RequestBody OperationDto operationDto){
        operationService.effectuerVersement(operationDto);
    }
    @PostMapping("/operations/retrait")
    void effectuerRetrait(@RequestBody OperationDto operationDto){
        operationService.effectuerRetrait(operationDto);
    }
    @PostMapping("/operations/virement")
    void virement(@RequestBody OperationDto operationDto){
        operationService.effectuerVirement(operationDto);

    }

    @GetMapping ("/operations/client/{numCompte}")
    ResponseEntity<List<Operation>> findAllOperationByClient(@PathVariable("numCompte") String numCompte){

        return ResponseEntity.ok(operationService.findByClientNumCompte(numCompte));
    }
}
