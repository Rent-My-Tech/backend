package com.rentmytech.demo.services;

import com.rentmytech.demo.models.Role;

import java.util.List;

public interface RoleService 
{

    List<Role> findAll();

    Role save(Role newRole);

    Role findByName(String roleName);

    Role update(long roleid, Role newRole);

    Role findRoleById(long id);

    public void deleteAll();
}
