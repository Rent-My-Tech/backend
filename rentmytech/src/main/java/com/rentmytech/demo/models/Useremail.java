package com.rentmytech.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "useremails")
public class Useremail extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long emailid;

    @Column
    @Email
    private String useremail;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "useremails", allowSetters = true)
    private User user;

    public Useremail()
    {

    }

    public Useremail(@Email String useremail, User user)
    {
        this.useremail = useremail;
        this.user = user;
    }

    public long getEmailid() {
        return emailid;
    }

    public void setEmailid(long emailid) {
        this.emailid = emailid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Useremail{" +
                "emailid=" + emailid +
                ", useremail='" + useremail + '\'' +
                ", user=" + user +
                '}';
    }
}
