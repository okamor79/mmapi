package com.mmbeautyschool.mmapi.repository;

import com.mmbeautyschool.mmapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT cl FROM Client cl WHERE cl.email = :client")
    Client getClientByEmail(@Param("client") String email);

    @Query("SELECT cl FROM Client cl WHERE cl.id = :id")
    Client getClientById(@Param("id") long id);

}
