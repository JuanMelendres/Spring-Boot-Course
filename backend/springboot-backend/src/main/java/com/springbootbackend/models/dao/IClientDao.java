package com.springbootbackend.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbootbackend.models.entity.Client;

public interface IClientDao extends JpaRepository<Client, Long> {

}
