package ub.tp.reservaPeluquerias.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Customer
{
    public Customer()
    {

    }

    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getEmail()
     {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public Customer emailSetting(String email)
    {
        this.email = email;
        return this;
    }

    public Customer nameSetting(String name)
    {
        this.name = name;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    
    private String phoneNumber;
}
