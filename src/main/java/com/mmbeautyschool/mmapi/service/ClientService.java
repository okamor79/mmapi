package com.mmbeautyschool.mmapi.service;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.exception.ClientAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.ClientUnauthorizedException;
import com.mmbeautyschool.mmapi.exception.NotClientFoundException;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients() throws NotClientFoundException;

    long newClient(Client client) throws ClientAlreadyExistException;

    Client clientLogin(String email, String password) throws ClientUnauthorizedException;

    Client getClientInfo(String email) throws NotClientFoundException;

    Client getClientByID(Long id);

    Client modifyClient(Client client);

    Boolean resetPassword(String email) throws NotClientFoundException;

    void changeClientStatus(Long id, UserStatus status);

}