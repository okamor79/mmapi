package com.mmbeautyschool.mmapi.service.impl;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.Email;
import com.mmbeautyschool.mmapi.entity.enums.UserRole;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import com.mmbeautyschool.mmapi.exception.ClientAlreadyExistException;
import com.mmbeautyschool.mmapi.exception.ClientUnauthorizedException;
import com.mmbeautyschool.mmapi.exception.NotClientFoundException;
import com.mmbeautyschool.mmapi.repository.ClientRepository;
import com.mmbeautyschool.mmapi.service.ClientService;
import com.mmbeautyschool.mmapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

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
    public List<Client> getAllClients() throws NotClientFoundException {
        List<Client> clientList = clientRepository.findAll();
        if (clientList == null) {
            throw new NotClientFoundException("No clients register");
        }
        return clientList;
    }

    @Override
    public long newClient(Client client) throws ClientAlreadyExistException {
        Client clientInfo = clientRepository.getClientByEmail(client.getEmail());
        if (clientInfo != null) {
            throw new ClientAlreadyExistException("E-mail already registered");
        }
        Client newClient = client;
        newClient.setRegistered(new Date());
        newClient.setRole(UserRole.ROLE_USER);
        newClient.setStatus(UserStatus.USER_ENABLE);
        clientRepository.save(newClient);
        return clientRepository.getClientByEmail(newClient.getEmail()).getId();
    }

    @Override
    public Client clientLogin(String email, String password) throws ClientUnauthorizedException {
        Client clientInfo = clientRepository.getClientByEmail(email);
        if (clientInfo == null) {
            throw new ClientUnauthorizedException("Client not found");
        }
        if (!clientInfo.getPassword().contentEquals(password)) {
            throw new ClientUnauthorizedException("Bad password");
        }
        return clientInfo;
    }

    @Override
    public Client getClientInfo(String email) throws NotClientFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if (client == null) {
            throw new NotClientFoundException("Client not found");
        }
        return client;
    }

    @Override
    public Client getClientByID(Long id) {
        return clientRepository.getClientById(id);
    }

    @Override
    public Client modifyClient(Client client) {
        client.setLastEdit(new Date());
        return clientRepository.save(client);
    }

    @Override
    public Boolean resetPassword(String email) throws NotClientFoundException {
        Client client = clientRepository.getClientByEmail(email);
        if (client == null) {
            throw new NotClientFoundException("Email does not registered");
        }
        String newPassword = passwordGenetator(passwordLength);
        String encodePassword = Base64.getEncoder().encodeToString(newPassword.getBytes());
        client.setPassword(encodePassword);
        client.setLastEdit(new Date());
        clientRepository.save(client);
        Email mail = new Email();
        mail.setRecipient(client.getEmail());
        mail.setSubject("Ваш оновлений пароль M&M Beauty School");
        String mailText = "Доброго дня. \n\nВам було згенерований новий пароль для входу у свій обліковий запис. \nПрохання, після успішного входу змінити його у своєму профілі\n\nВаш логін:" + client.getEmail() + "\nВаш новий пароль: " + newPassword;
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

