package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.Email;
import com.mmbeautyschool.mmapi.entity.enums.UserRole;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.exception.ClientAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.ClientUnauthorizedException;
import com.mmbeautyschool.mmapi.exception.ClientNotFoundException;
import com.mmbeautyschool.mmapi.model.ClientModel;
import com.mmbeautyschool.mmapi.repository.ClientRepository;
import com.mmbeautyschool.mmapi.service.ClientService;
import com.mmbeautyschool.mmapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    @Value("${password.length}")
    private int passwordLength;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailService emailService;

    public static String passwordGenetator(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789()!#*_^";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rndIndex = secureRandom.nextInt(chars.length());
            stringBuilder.append(chars.charAt(rndIndex));
        }
        return stringBuilder.toString();
    }

    @Override
    public List<Client> getAllClients() throws ClientNotFoundException {
        List<Client> clientList = clientRepository.findAll();
        if (clientList.isEmpty()) {
            throw new ClientNotFoundException("NO_REGISTER_CLIENT");
        }
        return clientList;
    }

    @Override
    public long newClient(Client client) throws ClientAlreadyExistException {
        Client clientInfo = clientRepository.getClientByEmail(client.getEmail());
        if (clientInfo != null) {
            throw new ClientAlreadyExistException("EMAIL_EXISTS");
        }
        client.setRegistered(new Date());
        client.setRole(UserRole.ROLE_USER);
        client.setStatus(UserStatus.USER_ENABLE);
        clientRepository.save(client);
        return clientRepository.getClientByEmail(client.getEmail()).getId();
    }

    @Override
    public ClientModel clientLogin(String email, String password) throws ClientUnauthorizedException {
        Client clientInfo = clientRepository.getClientByEmail(email);
        if (clientInfo == null) {
            throw new ClientUnauthorizedException("CLIENT_NOT_FOUND");
        }
        if (!clientInfo.getPassword().contentEquals(password)) {
            throw new ClientUnauthorizedException("BAD_PASSWORD");
        }
        return ClientModel.toModel(clientInfo);
    }

    @Override
    public ClientModel getClientInfo(String email) throws ClientNotFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if (client == null) {
            throw new ClientNotFoundException("CLIENT_NOT_FOUND");
        }
        System.out.println(client);
        System.out.println(ClientModel.toModel(client));
        return ClientModel.toModel(client);
    }


    @Override
    public ClientModel modifyClient(Client client) {
        client.setLastEdit(new Date());
        clientRepository.save(client);
        return ClientModel.toModel(client);
    }

    @Override
    public Boolean resetPassword(String email) throws ClientNotFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if (client == null) {
            throw new ClientNotFoundException("EMAIL_NOT_FOUND");
        }
        String newPassword = passwordGenetator(passwordLength);
        String encodePassword = Base64.getEncoder().encodeToString(newPassword.getBytes());
        client.setPassword(encodePassword);
        client.setLastEdit(new Date());
        clientRepository.save(client);
        Email mail = new Email();
        mail.setRecipient(client.getEmail());
        mail.setSubject("Ваш оновлений пароль M&M Beauty School");
        String mailText = "Доброго дня. \n\nВам було згенерований новий пароль для входу у свій обліковий запис. \nПрохання, після успішного входу змінити його у своєму профілі\n\nВаш логін:  "
                + client.getEmail() + "\nВаш новий пароль:  "
                + newPassword + "\n\nГарного Вам дня.";
        mail.setMsgBody(mailText);
        emailService.sendSimpleMail(mail);
        return true;
    }

    @Override
    public void changeClientStatus(Long id, UserStatus status) {
        Client client = clientRepository.getClientById(id);
        client.setStatus(status);
        client.setLastEdit(new Date());
        clientRepository.save(client);
    }
}

