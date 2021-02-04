package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
//User is the main table
public class User extends Auditable
{
    //primary key for User
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(nullable = false)
    private String email;

    private String usertype; //Owner or Renter

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserRoles> userroles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnoreProperties(value = "user", allowSetters = true)
//    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<ItemList> myItemList = new ArrayList<>();

//    @OneToMany(mappedBy = "user")
//    @JsonIgnoreProperties(value = "user", allowSetters = true)
//    private List<ItemRating> itemRatings = new ArrayList<>();

    public User()
    {

    }


    public User(String username, String password,  String email, String usertype) {
        this.username = username;
        setPassword(password);
        this.email = email;
        this.usertype = usertype;

    }

    public User(String usertype)
    {
        this.usertype = usertype;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> returnList = new ArrayList<>();
        for (UserRoles r : this.userroles)
        {
            String myRole = "ROLE_" + r.getRole().getName().toUpperCase();
            returnList.add(new SimpleGrantedAuthority(myRole));
        }
        return returnList;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public String getUsertype()
    {
        return usertype;
    }

    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
    }


    public Set<UserRoles> getUserroles() {
        return userroles;
    }

    public void setUserroles(Set<UserRoles> userroles) {
        this.userroles = userroles;
    }

    public List<Useremail> getUseremails()
    {
        return useremails;
    }

    public void setUseremails(List<Useremail> useremails)
    {
        this.useremails = useremails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }

    public List<ItemList> getMyItemList() {
        return myItemList;
    }

    public void setMyItemList(List<ItemList> myItemList) {
        this.myItemList = myItemList;
    }

//    public List<ItemRating> getItemRatings() {
//        return itemRatings;
//    }
//
//    public void setItemRatings(List<ItemRating> itemRatings) {
//        this.itemRatings = itemRatings;
//    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", usertype='" + usertype + '\'' +
                ", userroles=" + userroles +
                ", useremails=" + useremails +
                '}';
    }
}
