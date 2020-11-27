package com.example.demo.crudrepos;

import com.example.demo.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, UUID> {
}
