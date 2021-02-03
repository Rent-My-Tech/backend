package com.rentmytech.demo.controllers;


import com.rentmytech.demo.models.User;
import com.rentmytech.demo.models.UserRoles;
import com.rentmytech.demo.services.RoleService;
import com.rentmytech.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class SigninContoller
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/createnewuser/{roleName}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest httpServletRequest, @PathVariable String roleName, @Valid @RequestBody User user) throws URISyntaxException
    {
        //Creating a user

        User newuser = new User();
        newuser.setUsername(newuser.getUsername());
        newuser.setPassword(newuser.getPassword());
        newuser.setEmail(newuser.getEmail());

        // Creating the admin, owner and renter roles
        Set<UserRoles> newRoles = new HashSet<>();

        UserRoles adminRole = new UserRoles(newuser, roleService.findByName("admin"));
        UserRoles ownerRole = new UserRoles(newuser, roleService.findByName("owner"));
        UserRoles renterRole = new UserRoles(newuser, roleService.findByName("renter"));

        Set<UserRoles> newUserRoles = new HashSet<>();
        newRoles.add(new UserRoles(newuser,roleService.findByName("admin")));
        newuser.setUserroles(newUserRoles);

        newuser = userService.save(newuser);

        switch (roleName)
        {
            case "admin" : newRoles.add(adminRole);

            break;

            case "owner" : newRoles.add(ownerRole);

            break;

            case "renter" : newRoles.add(renterRole);

            break;
        }

        newuser.setUserroles(newRoles);
        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}").buildAndExpand(newuser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        //returning the access token

        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"), System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("scope", "read write trust");
        map.add("username", newuser.getUsername());
        map.add("password", newuser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String myToken = restTemplate.postForObject(requestURI, request, String.class);

        return new ResponseEntity<>(myToken, responseHeaders, HttpStatus.CREATED);
    }

}
