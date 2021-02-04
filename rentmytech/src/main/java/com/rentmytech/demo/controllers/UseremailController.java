package com.rentmytech.demo.controllers;

import com.rentmytech.demo.models.Useremail;
import com.rentmytech.demo.services.UseremailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/useremails")
public class UseremailController
{
    @Autowired
    private UseremailService useremailService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/useremails", produces = "application/json") // working as of 2/4
    public ResponseEntity<?> listAllUseremails()
    {
        List<Useremail> allUserEmails = useremailService.findAll();
        return new ResponseEntity<>(allUserEmails, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/useremail/{emailId}", produces = "application/json") // working as of 2/4
    public ResponseEntity<?> getUserEmailById(@PathVariable long emailId)
    {
        Useremail email = useremailService.findUseremailById(emailId);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @DeleteMapping(value = "/useremail/{emailid}") // working as of 2/4
    public ResponseEntity<?> deleteUserEmailById(@PathVariable long emailid)
    {
        useremailService.delete(emailid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/useremail/{emailid}/email/{emailaddress}") // working as of 2/4
    public ResponseEntity<?> updateUserEmail(@PathVariable long emailid, @PathVariable String emailaddress)
    {
        useremailService.update(emailid, emailaddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/user/{userid}/email/{emailaddress}") //working as of 2/4
    public ResponseEntity<?> addNewEmail(@PathVariable long userid, @PathVariable String emailaddress) throws URISyntaxException
    {
        Useremail newUserEmail = useremailService.save(userid, emailaddress);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserEmailURI = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/useremails/useremail/{emailid}")
                .buildAndExpand(newUserEmail.getEmailid())
                .toUri();
        responseHeaders.setLocation(newUserEmailURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
}
