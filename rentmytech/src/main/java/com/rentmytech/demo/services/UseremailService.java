package com.rentmytech.demo.services;

import com.rentmytech.demo.models.User;
import com.rentmytech.demo.models.Useremail;

import java.util.List;

public interface UseremailService
{
    List<Useremail> findAll();

    Useremail findUseremailById(long id);

    void delete(long id);

    Useremail update(long emailid, String emailaddress);

    Useremail save(long emailid, String emailaddress);
}
