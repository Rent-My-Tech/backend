package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String usertype; //Owner or Renter

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userroles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Useremail> useremails = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Item> items = new ArrayList<>();

    public User()
    {

    }


    public User(String username, String password, List<UserRoles> userroles)
    {
        setUsername(username);
        setPassword(password);
        for (UserRoles ur : userroles)
        {
            ur.setUser(this);
        }
        this.userroles = userroles;
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

    public List<UserRoles> getUserroles()
    {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles)
    {
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

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", usertype='" + usertype + '\'' +
                ", userroles=" + userroles +
                ", useremails=" + useremails +
                ", items=" + items +
                '}';
    }
}
