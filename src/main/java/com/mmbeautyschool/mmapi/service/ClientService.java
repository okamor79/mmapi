package com.mmbeautyschool.mmapi.service;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    long newClient(Client client);

    Client clientLogin(String email, String password);

    Client getClientInfo(String email);

    Client getClientByID(Long id);

    Client modifyClient(Client client);

    Boolean resetPassword(String email);

    void changeClientStatus(Long id, UserStatus status);

}