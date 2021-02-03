package com.rentmytech.demo.services;

import com.rentmytech.demo.exceptions.ResourceNotFoundException;
import com.rentmytech.demo.models.Role;
import com.rentmytech.demo.repository.RoleRepository;
import com.rentmytech.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserAuditing userAuditing;

    @Override
    public List<Role> findAll()
    {
        List<Role> roleList = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(roleList::add);
        return roleList;
    }

    @Override
    public Role findRoleById(long id)
    {
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Role id " + id + " not found!"));
    }

    @Override
    public Role findByName(String name)
    {
        Role rolerepos = roleRepository.findByNameIgnoreCase(name);
        if (rolerepos != null)
        {
            return rolerepos;
        } else
        {
            throw new ResourceNotFoundException(name);
        }
    }

    @Transactional
    @Override
    public Role save(Role role)
    {
        if(role.getUserroles().size() > 0)
        {
            throw new ResourceNotFoundException("User Role are not update throught the Role");
        }
        return roleRepository.save(role);
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        roleRepository.deleteAll();
    }

    @Transactional
    @Override
    public Role update(long id, Role role)
    {
        if(role.getName() == null)
        {
            throw new ResourceNotFoundException("No Role Found");
        }

        if (role.getUserroles().size() > 0)
        {
            throw new ResourceNotFoundException("Roles are not updated through Role. See endpoint POST: users/user/{userid}/role/{roleid}");
        }

        Role newRole = findRoleById(id);

        roleRepository.updateRoleName(userAuditing.getCurrentAuditor().get(), id, role.getName());
        return findRoleById(id);
    }
}
