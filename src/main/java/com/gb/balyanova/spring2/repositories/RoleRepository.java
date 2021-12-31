package com.gb.balyanova.spring2.repositories;

import com.gb.balyanova.spring2.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
