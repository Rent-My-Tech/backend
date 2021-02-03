package com.rentmytech.demo.controllers;

import com.rentmytech.demo.models.Role;
import com.rentmytech.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController
{
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<?> listRoles()
    {
        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    @GetMapping(value = "/role/name/{roleName}", produces = "application/json")
    public ResponseEntity<?> getRoleByName(@PathVariable String roleName)
    {
        Role r = roleService.findByName(roleName);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping(value = "/role", consumes = "application/json")
    public ResponseEntity<?>addNewRole(@Valid @RequestBody Role newRole)
    {
        newRole.setRoleid(0);
        newRole = roleService.save(newRole);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{roleid}").buildAndExpand(newRole.getRoleid()).toUri();
        responseHeaders.setLocation(newRoleURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/role/{roleid}", consumes = {"application/json"})
    public ResponseEntity<?> updateRole(@PathVariable long roleid, @Valid @RequestBody Role newRole)
    {
        newRole = roleService.update(roleid, newRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
