package com.mmbeautyschool.mmapi.entity;

import com.mmbeautyschool.mmapi.entity.enums.UserRole;
import com.mmbeautyschool.mmapi.entity.enums.UserStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.USER_ENABLE;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_USER;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date registered;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date lastEdit;

}