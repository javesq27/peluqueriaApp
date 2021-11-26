package ub.tp.reservaPeluquerias.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sun.istack.NotNull;

@Entity
public class Service 
{
    public Service()
    {
        
    }
    
    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public int getPrice() 
    {
        return price;
    }

    public void setPrice(int price) 
    {
        this.price = price;
    }

    public int getServiceTime() 
    {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) 
    {
        this.serviceTime = serviceTime;
    }

    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private int price;
    @NotNull
    private int serviceTime;
}
