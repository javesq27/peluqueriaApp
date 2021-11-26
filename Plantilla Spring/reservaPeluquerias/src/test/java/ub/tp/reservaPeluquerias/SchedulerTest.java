package ub.tp.reservaPeluquerias;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ub.tp.reservaPeluquerias.Model.Appointment;
import ub.tp.reservaPeluquerias.Model.Scheduler;

public class SchedulerTest {

    @BeforeEach
    void SetUp(){

        scheduler = new Scheduler();
        appointment = new Appointment(); 
        comparative = new Appointment();
        array = new ArrayList<Appointment>();
        
    }

    @Test

    void noWorkHoursConflictTest() {
        
        scheduler.setOpeningTime(LocalTime.of(10, 00));
        scheduler.setClosingTime(LocalTime.of(20, 00));
        appointment.setTime(LocalTime.of(16, 00).toString());
        assertTrue(scheduler.noWorkHoursConflict(appointment));
    }

    @Test

    void noWorkHoursConflictOutOfTime() {

        scheduler.setOpeningTime(LocalTime.of(10, 00));
        scheduler.setClosingTime(LocalTime.of(20, 00));
        appointment.setTime(LocalTime.of(23, 00).toString());
        assertFalse(scheduler.noWorkHoursConflict(appointment));
    }

    @Test

    void noWorkHoursConflictNegativeTimeTest() {

        scheduler.setOpeningTime(LocalTime.of(10, 00));
        scheduler.setClosingTime(LocalTime.of(20, 00));
        appointment.setTime(LocalTime.of(-15, 00).toString());
        assertFalse(scheduler.noWorkHoursConflict(appointment));
    }


    @Test

    void isAppointmentAvailableTest() {
       
       scheduler.setOpeningTime(LocalTime.of(10, 00));
       scheduler.setClosingTime(LocalTime.of(20, 00));
       appointment.setTime(LocalTime.of(11, 00).toString());
       appointment.setDuration(25);
       appointment.setDate(LocalDate.of(2021, 03, 10).toString());
       array.add(appointment);
       appointment.setTime(LocalTime.of(11, 30).toString());
       appointment.setDuration(25);
       appointment.setDate(LocalDate.of(2021, 03, 10).toString());
       array.add(appointment);
       assertFalse(scheduler.isAppointmentAvailable(array, appointment));

    }

    @Test

    void isAppointmentNotAvailableTest() {

       scheduler.setOpeningTime(LocalTime.of(10, 00));
       scheduler.setClosingTime(LocalTime.of(20, 00));
       appointment.setTime(LocalTime.of(17, 00).toString());
       appointment.setDuration(25);
       appointment.setDate(LocalDate.of(2021, 06, 17).toString());
       array.add(appointment);
       comparative.setTime(LocalTime.of(17, 00).toString());
       comparative.setDuration(25);
       comparative.setDate(LocalDate.of(2021, 06, 17).toString());
       array.add(comparative);
       assertTrue(scheduler.isAppointmentAvailable(array, comparative));


    }

    @Test

    void isAppointmentAvailableIsSlotTakenTest() {

        scheduler.setOpeningTime(LocalTime.of(10, 00));
        scheduler.setClosingTime(LocalTime.of(20, 00));
        appointment.setTime(LocalTime.of(16, 00).toString());
        appointment.setDuration(25);
        appointment.setDate(LocalDate.of(2021, 02, 20).toString());
        array.add(appointment);
        appointment.setTime(LocalTime.of(16, 00).toString());
        appointment.setDuration(40);
        appointment.setDate(LocalDate.of(2021, 02, 20).toString());
        array.add(appointment);
        assertFalse(scheduler.isAppointmentAvailable(array, appointment));

    }

    @Test

    void isAppointmentAvailableWorkHoursConflict() {

        scheduler.setOpeningTime(LocalTime.of(10, 00));
        scheduler.setClosingTime(LocalTime.of(20, 00));
        appointment.setTime(LocalTime.of(23, 00).toString());
        appointment.setDuration(25);
        appointment.setDate(LocalDate.of(2021, 05, 16).toString());
        array.add(appointment);
        appointment.setTime(LocalTime.of(6, 30).toString());
        appointment.setDuration(25);
        appointment.setDate(LocalDate.of(2021, 07, 11).toString());
        array.add(appointment);
        assertFalse(scheduler.isAppointmentAvailable(array, appointment));
    }

    @Test

    void isSlotTakenTest() {
        
        appointment.setBranchId(Integer.valueOf(30));
        comparative.setBranchId(Integer.valueOf(30));
        appointment.setDate(LocalDate.of(2021, 03, 10).toString());
        comparative.setDate(LocalDate.of(2021, 03, 10).toString());
        appointment.setTime(LocalTime.of(16, 00).toString());
        comparative.setTime(LocalTime.of(16, 00).toString());
        appointment.setDuration(30);
        comparative.setDuration(30);
        array.add(appointment);
        array.add(comparative);
        assertTrue(scheduler.isSlotTaken(array, appointment));
    }

    @Test

    void isSlotTakenDifferentDateTest() {

        appointment.setBranchId(Integer.valueOf(30));
        comparative.setBranchId(Integer.valueOf(30));
        appointment.setDate(LocalDate.of(2021, 06, 11).toString());
        comparative.setDate(LocalDate.of(2021, 10, 20).toString());
        appointment.setTime(LocalTime.of(16, 00).toString());
        comparative.setTime(LocalTime.of(16, 00).toString());
        appointment.setDuration(30);
        comparative.setDuration(30);
        array.add(appointment);
        array.add(comparative);
        assertFalse(scheduler.isSlotTaken(array, appointment));

    }

    @Test

    void isSlotTakenDifferentBranchTest() {

        appointment.setBranchId(Integer.valueOf(20));
        comparative.setBranchId(Integer.valueOf(30));
        appointment.setDate(LocalDate.of(2021, 02, 20).toString());
        comparative.setDate(LocalDate.of(2021, 02, 20).toString());
        appointment.setTime(LocalTime.of(16, 00).toString());
        comparative.setTime(LocalTime.of(16, 00).toString());
        appointment.setDuration(30);
        comparative.setDuration(30);
        array.add(appointment);
        array.add(comparative);
        assertTrue(scheduler.isSlotTaken(array, appointment));
    }

    @Test

    void isSlotTakenMistakeParametersTest() {

        appointment.setBranchId(Integer.valueOf(84892));
        comparative.setBranchId(Integer.valueOf(32334));
        appointment.setDate(LocalDate.of(9999, 9988, 999).toString());
        comparative.setDate(LocalDate.of(5556, 888888, 293847).toString());
        appointment.setTime(LocalTime.of(8888, 4444).toString());
        comparative.setTime(LocalTime.of(33455, 56774).toString());
        appointment.setDuration(34567);
        comparative.setDuration(3054322);
        array.add(appointment);
        array.add(comparative);
        assertFalse(scheduler.isSlotTaken(array, appointment));
    }

    private Scheduler scheduler;
    private Appointment appointment;
    private Appointment comparative;
    private List<Appointment> array; 
    
}
