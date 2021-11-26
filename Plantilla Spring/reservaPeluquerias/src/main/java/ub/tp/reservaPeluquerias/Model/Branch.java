package ub.tp.reservaPeluquerias.Model;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;


@Entity
public class Branch 
{
    public Branch() 
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

    public String getAddress() 
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public LocalTime getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = LocalTime.parse(open);
    }

    public LocalTime getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = LocalTime.parse(closed);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String address;

    private LocalTime open;

    private LocalTime closed;
}
