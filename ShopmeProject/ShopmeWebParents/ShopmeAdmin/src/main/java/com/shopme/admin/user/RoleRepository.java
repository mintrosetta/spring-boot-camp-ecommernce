package com.shopme.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.shopme.commons.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

}
