package com.rentmytech.demo.services;

import com.rentmytech.demo.exceptions.ResourceNotFoundException;
import com.rentmytech.demo.models.*;
import com.rentmytech.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ValidationHelper validationHelper;

    @Override
    public List<User> findAll()
    {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(userList::add);
        return userList;
    }

    @Override
    public User findUserById(long userid) throws ResourceNotFoundException
    {
        return userRepository.findById(userid).orElseThrow(()-> new EntityNotFoundException("User id " + userid + " not found"));
    }

    @Override
    public List<User> findByNameContaining(String username)
    {
        return userRepository.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public User findByName(String name)
    {
        User username = userRepository.findByUsername(name.toLowerCase());
        if (username == null)
        {
            throw new ResourceNotFoundException("Username " + name + " not found!");
        }
        return username;
    }

    @Transactional
    @Override
    public void delete(long userid)
    {
        userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User id " + userid + " not found"));
        userRepository.deleteById(userid);
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        if(user.getUserid() !=0)
        {
            userRepository.findById(user.getUserid())
                    .orElseThrow(()-> new ResourceNotFoundException("User id " + user.getUserid() + " not found"));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());
        newUser.setUsertype(user.getUsertype());

        newUser.getUserroles().clear();
        for (UserRoles userRoles : user.getUserroles())
        {
            Role addRole = roleService.findRoleById(userRoles.getRole().getRoleid());
            newUser.getUserroles().add(new UserRoles(newUser,addRole));
        }

        newUser.getUseremails().clear();
        for (Useremail ue :user.getUseremails())
        {
            newUser.getUseremails()
                    .add(new Useremail(ue.getUseremail(),
                            newUser));
        }

        for(ItemList itemList : user.getMyItemList())
        {
            newUser.getMyItemList()
                    .add(new ItemList(newUser, itemList.getItem()));
        }
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long userid)
    {
        User currentUser = findUserById(userid);
        if(validationHelper.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            if (user.getUsername() !=null)
            {
                currentUser.setUsername(user.getUsername().toLowerCase());
            }
        }

        if (user.getPassword() !=null)
        {
            currentUser.setPasswordNoEncrypt(user.getPassword());
        }

        if (user.getEmail()!=null)
        {
            currentUser.setEmail(user.getEmail().toLowerCase());
        }



        if (user.getUserroles().size() > 0)
        {
            currentUser.getUserroles().clear();
            for (UserRoles ur : user.getUserroles())
            {
                Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
                currentUser.getUserroles().add(new UserRoles(currentUser, addRole));
            }
        }
        if (user.getUseremails().size() > 0)
        {
            currentUser.getUseremails().clear();
            for (Useremail ue : user.getUseremails())
            {
                currentUser.getUseremails().add(new Useremail(ue.getUseremail(), currentUser));
            }
        }

        if (user.getMyItemList().size() > 0)
        {
            currentUser.getMyItemList().clear();
            for (ItemList itemList1 : user.getMyItemList())
            {
                Item addItem = itemService.findItemById(itemList1.getItem().getItemid());
                currentUser.getMyItemList().add(new ItemList(currentUser,addItem));
            }
        }
        return userRepository.save(currentUser);
    }

    @Transactional
    @Override
    public void deleteAll()
    {
        userRepository.deleteAll();
    }
}
