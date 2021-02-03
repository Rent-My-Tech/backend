package com.rentmytech.demo;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import com.rentmytech.demo.models.Role;
import com.rentmytech.demo.models.User;
import com.rentmytech.demo.models.UserRoles;
import com.rentmytech.demo.models.Useremail;
import com.rentmytech.demo.services.RoleService;
import com.rentmytech.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData
        implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
            Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        Role r1 = new Role("admin");
        Role r2 = new Role("owner");
        Role r3 = new Role("renter");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        // admin, data, user
        User u1 = new User("newuser", "password", "email@email.com", "admin");
        u1.getUserroles()
                .add(new UserRoles(u1,
                        r1));
        u1.getUserroles()
                .add(new UserRoles(u1,
                        r2));
        u1.getUserroles()
                .add(new UserRoles(u1,
                        r3));
        u1.getUseremails()
                .add(new Useremail("admin@email.local",
                        u1));
        u1.getUseremails()
                .add(new Useremail("admin@mymail.local",
                        u1));

        userService.save(u1);

        // data, owner
        User u2 = new User("kayode", "password", "email2@email.com", "owner");
        u2.getUserroles()
                .add(new UserRoles(u2,
                        r2));
        u2.getUserroles()
                .add(new UserRoles(u2,
                        r3));
        u2.getUseremails()
                .add(new Useremail("email2@email.com",
                        u2));
        u2.getUseremails()
                .add(new Useremail("email3@email.com",
                        u2));
        u2.getUseremails()
                .add(new Useremail("email4@email.com",
                        u2));
        userService.save(u2);

        // user renter
        User u3 = new User("Benjamin", "password1", "Benjamin@email.com", "renter");
        u3.getUserroles()
                .add(new UserRoles(u3,
                        r2));
        u3.getUseremails()
                .add(new Useremail("Benjamin@email.com",
                        u3));
        userService.save(u3);

        User u4 = new User("Martha", "password2", "Martha@email.com", "renter");
        u4.getUserroles()
                .add(new UserRoles(u4,
                        r2));
        userService.save(u4);

        User u5 = new User("Phil", "password3", "email2@email.com", "renter");
        u5.getUserroles()
                .add(new UserRoles(u5,
                        r2));
        userService.save(u5);

        if (false)
        {
            // using JavaFaker create a bunch of regular users
            // https://www.baeldung.com/java-faker
            // https://www.baeldung.com/regular-expressions-java

            FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-US"),
                    new RandomService());
            Faker nameFaker = new Faker(new Locale("en-US"));

            for (int i = 0; i < 25; i++)
            {
                new User();
                User fakeUser;

                fakeUser = new User("kayode", "password", "email2@email.com", "owner");
                fakeUser.getUserroles()
                        .add(new UserRoles(fakeUser,
                                r2));
                fakeUser.getUseremails()
                        .add(new Useremail(fakeValuesService.bothify("????##@gmail.com"),
                                fakeUser));
                userService.save(fakeUser);
            }
        }
    }
}