package com.mmbeautyschool.mmapi.controller;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.exception.ClientAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.ClientUnauthorizedException;
import com.mmbeautyschool.mmapi.exception.NotClientFoundException;
import com.mmbeautyschool.mmapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity getAllClients() {
        try {
            return ResponseEntity.ok().body(clientService.getAllClients());
        }
        catch (NotClientFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info/{email}")
    public ResponseEntity getClientInfo(@PathVariable("email") String email) {
        try {
          return ResponseEntity.ok().body(clientService.getClientInfo(email));
        }
        catch (NotClientFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerNewClient(@RequestBody Client client) {
        try {
            return ResponseEntity.ok().body(clientService.newClient(client));
        }
        catch (ClientAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reset/{email}")
    public ResponseEntity resetPassword(@PathVariable("email") String email) {
        try {
            return ResponseEntity.ok().body(clientService.resetPassword(email));
        } catch (NotClientFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity login(@PathVariable("email") String email,
                                @PathVariable("password") String password) {
        try {
            return ResponseEntity.ok().body(clientService.clientLogin(email, password));
        }
        catch (ClientUnauthorizedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/edit")
    public Client editClientInfo(@RequestBody Client client) {
        return clientService.modifyClient(client);
    }

    @PostMapping("/change_status/{id}/{status}")
    public void changeUserStatus(@PathVariable("id") Long id, @PathVariable("status") UserStatus status) {
        clientService.changeClientStatus(id, status);
    }

}
