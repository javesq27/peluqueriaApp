package ub.tp.reservaPeluquerias.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sun.istack.NotNull;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User 
{
    public User()
    {
        this.status = true;
    }
    
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getRole() 
    {
        return role;
    }

    public void setRole(String role) 
    {
        this.role = "ROLE_"+role;
    }

    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Boolean getStatus() 
    {
        return status;
    }

    public void setStatus(Boolean status) 
    {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String name;

    @NotNull
    private String role;

    private Boolean status;
}
