package com.fastfood.adapters.out.repositories;

import com.fastfood.adapters.out.entities.JpaClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaClienteRepository extends JpaRepository<JpaClienteEntity, UUID> {

    @Query("SELECT c FROM JpaClienteEntity c WHERE c.cpf = :cpf")
    JpaClienteEntity findByCPF(@Param("cpf") String cpf);

}
