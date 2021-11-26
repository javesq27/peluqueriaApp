package ub.tp.reservaPeluquerias.Model;

import java.time.LocalTime;
import java.util.List;

public class Scheduler 
{

    public Scheduler() {
    }

    public boolean isAppointmentAvailable(List<Appointment> array, Appointment appointment)
    {
        return (noWorkHoursConflict(appointment) && !isSlotTaken(array, appointment));
    }

    public boolean isAppointmentAvailable(Appointment[] array, Appointment appointment)
    {
        return (noWorkHoursConflict(appointment) && !isSlotTaken(array, appointment));
    }

    public boolean isSlotTaken(List<Appointment> array, Appointment appointment)
    {
        for (Appointment a : array)
        {
            if (a.sameBranch(appointment) && a.sameDate(appointment) && a.happensDuring(appointment))
            {
                return true;
            }
        }
        return false;   
    }

    public boolean isSlotTaken(Appointment[] array, Appointment appointment)
    {
        for (Appointment a : array)
        {
            if (a.sameBranch(appointment) && a.sameDate(appointment) && (a.sameDate(appointment) || a.happensDuring(appointment)))
            {
                return true;
            }
        }
        return false;   
    }
    
    public boolean noWorkHoursConflict(Appointment appointment)
    {
        return !(appointment.getTime().isBefore(getOpeningTime()) || appointment.getTime().isAfter(getClosingTime()) || appointment.getEndTime().isAfter(getClosingTime()) || appointment.getEndTime().isBefore(getOpeningTime()));
    }

    public LocalTime getOpeningTime() 
    {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) 
    {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) 
    {
        this.closingTime = closingTime;
    }

    private LocalTime openingTime;
    private LocalTime closingTime;
}
