package com.mmbeautyschool.mmapi.model;

import com.mmbeautyschool.mmapi.entity.Client;
import com.mmbeautyschool.mmapi.entity.enums.UserRole;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


@Getter
@Setter
@ToString
public class ClientModel {

    private Long id;

    private String email;

    private String phone;

    private String name;

    private UserStatus status;

    private UserRole role;

    public static ClientModel toModel(Client client) {
        ClientModel model = new ClientModel();
        model.setId(client.getId());
        model.setEmail(client.getEmail());
        model.setPhone(client.getPhone());
        model.setName(client.getName());
        model.setStatus(client.getStatus());
        model.setRole(client.getRole());
        return model;
    }

}
