package com.mmbeautyschool.mmapi.service;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.exception.ClientAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.ClientUnauthorizedException;
import com.mmbeautyschool.mmapi.exception.ClientNotFoundException;
import com.mmbeautyschool.mmapi.model.ClientModel;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients() throws ClientNotFoundException;

    long newClient(Client client) throws ClientAlreadyExistException;

    ClientModel clientLogin(String email, String password) throws ClientUnauthorizedException;

    ClientModel getClientInfo(String email) throws ClientNotFoundException;


    ClientModel modifyClient(Client client);

    Boolean resetPassword(String email) throws ClientNotFoundException;

    void changeClientStatus(Long id, UserStatus status);

}