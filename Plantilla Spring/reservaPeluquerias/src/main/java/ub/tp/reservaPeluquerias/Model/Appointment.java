package ub.tp.reservaPeluquerias.Model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Appointment 
{
    public Appointment()
    {
        this.confirmed = false;        
    }
    
    public boolean isConfirmed()
    {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed)
    {
        this.confirmed = confirmed;
    }

    public boolean sameBranch(Appointment appointment)
    {
        return branchId == appointment.getBranchId();
    }

    public boolean sameDate(Appointment appointment)
    {
        return date.equals(appointment.getDate());
    }

    public boolean sameTime(Appointment appointment)
    {
        return time == appointment.getTime();
    }

    public LocalTime getEndTime()
    {
        return time.plusMinutes(Long.valueOf(duration));
    }

    public boolean happensDuring(Appointment app)
    {
        return ((time.isBefore(app.getTime()) && getEndTime().isAfter(app.getTime())) || (time.isBefore(app.getEndTime()) && getEndTime().isAfter(app.getEndTime())));
    }

    public Integer getId() 
    {
        return id;
    }

    public LocalTime getTime() 
    {
        return time;
    }

    public void setTime(String time) 
    {
        this.time = LocalTime.parse(time);
    }

    public LocalDate getDate() 
    {
        return date;
    }

    public void setDate(String date) 
    {
        this.date = LocalDate.parse(date);
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getCustomerId() 
    {
        return customerId;
    }

    public void setCustomerId(Integer customerId) 
    {
        this.customerId = customerId;
    }

    public Integer getServiceId() 
    {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) 
    {
        this.serviceId = serviceId;
    }

    public Integer getBranchId() 
    {
        return branchId;
    }

    public void setBranchId(Integer branchId) 
    {
        this.branchId = branchId;
    }

    public int getDuration() 
    {
        return duration;
    }

    public void setDuration(int duration) 
    {
        this.duration = duration;
    }

    public Appointment confirmedAppointment(Boolean b){
        if (b != null){
            setConfirmed(b);
        }
        return this;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private Integer customerId;
    
    private Integer serviceId;
    
    private boolean confirmed;
    
    private Integer branchId;

    private LocalTime time;
    
    private LocalDate date;

    private int duration;
}