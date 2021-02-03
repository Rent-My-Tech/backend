package com.rentmytech.demo.services;

import com.rentmytech.demo.exceptions.ResourceNotFoundException;
import com.rentmytech.demo.models.User;
import com.rentmytech.demo.models.Useremail;
import com.rentmytech.demo.repository.UseremailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "useremailService")
public class UseremailServiceImpl implements UseremailService
{
    @Autowired
    private UseremailRepository useremailRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidationHelper validationHelper;

    @Override
    public List<Useremail> findAll()
    {
        List<Useremail> emailList = new ArrayList<>();
        useremailRepository.findAll().iterator().forEachRemaining(emailList::add);
        return emailList;
    }

    @Override
    public Useremail findUseremailById(long id)
    {
        return useremailRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Email with id " + id + " not found!"));
    }

    @Override
    public void delete(long id)
    {
        if(useremailRepository.findById(id).isPresent())
        {
            if(validationHelper.isAuthorizedToMakeChange(useremailRepository.findById(id)
                    .get()
                    .getUser()
                    .getUsername()))
            {
                useremailRepository.deleteById(id);
            }
        }
    }

    @Override
    public Useremail update(long emailid, String emailaddress)
    {
        if(useremailRepository.findById(emailid).isPresent())
        {
            if(validationHelper.isAuthorizedToMakeChange(useremailRepository.findById(emailid)
                    .get()
                    .getUser()
                    .getUsername()))
            {
                Useremail useremail = findUseremailById(emailid);
                useremail.setUseremail(emailaddress.toLowerCase());
                return useremailRepository.save(useremail);
            } else
            {
                throw new ResourceNotFoundException("User not authorized to make change");
            }
        }else
        {
            throw new ResourceNotFoundException("Email with id " + emailid + " not found");
        }

    }

    @Override
    public Useremail save(long userid, String emailaddress)
    {
        User currentUser = userService.findUserById(userid);
        if(validationHelper.isAuthorizedToMakeChange(currentUser.getUsername()))
        {
            Useremail newUserEmail = new Useremail(emailaddress, currentUser);
            return useremailRepository.save(newUserEmail);
        } else
        {
            throw new ResourceNotFoundException("User not authorized to make changes");
        }
    }
}

