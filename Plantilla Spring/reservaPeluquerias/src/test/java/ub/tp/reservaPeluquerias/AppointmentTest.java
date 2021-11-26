package ub.tp.reservaPeluquerias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ub.tp.reservaPeluquerias.Model.*;

public class AppointmentTest {
    
    @BeforeEach
    void setUp(){
        appointment =  new Appointment();
        comparative = new Appointment();

    }

    @Test
    void getEndTimeTest(){
        appointment.setDuration(40);
        appointment.setTime(LocalTime.of(14, 00).toString());
        assertEquals(appointment.getEndTime(), LocalTime.of(14, 40));
    }

    @Test 

    void getEndTimeMistakeTest(){
        appointment.setDuration(30);
        appointment.setTime(LocalTime.of(16, 30).toString());
        assertNotEquals(appointment.getEndTime(), LocalTime.of(18, 00));

    }

    @Test

    void sameBranchTest(){

        appointment.setBranchId(Integer.valueOf(20));
        comparative.setBranchId(Integer.valueOf(20));
        assertTrue(appointment.sameBranch(comparative));
    }

    @Test

    void differentBranchTest(){
        appointment.setBranchId(Integer.valueOf(35));
        comparative.setBranchId(Integer.valueOf(43));
        assertFalse(appointment.sameBranch(comparative));
    }

    @Test

    void sameDateTest(){

        appointment.setDate("01/02/2021");
        comparative.setDate("01/03/2021");
        assertFalse(appointment.sameDate(comparative));
    }

    @Test

    void differentDateTest(){

        appointment.setDate("20/06/2021");
        comparative.setDate("10/08/2021");
        assertTrue(appointment.sameDate(comparative));
    }

    @Test

    void sameDateNullTest() {

        appointment.setDate(null);
        comparative.setDate(null);
        assertTrue(appointment.sameDate(comparative));

    }

    @Test

    void sameDateLocalDateTest(){

        appointment.setDate(LocalDate.of(2020, 02, 20).toString());
        comparative.setDate(LocalDate.of(2020, 02, 20).toString());
        assertTrue(appointment.sameDate(comparative));
    }

    @Test

    void sameDateLocalDiferentDateTest(){

        appointment.setDate(LocalDate.of(2021, 03, 15).toString());
        comparative.setDate(LocalDate.of(2021, 04, 19).toString());
        assertFalse(appointment.sameDate(comparative));
    }

    @Test

    void happensDuringTest(){
        appointment.setTime(LocalTime.of(16, 00).toString());
        comparative.setTime(LocalTime.of(14, 00).toString());
        appointment.setDuration(45);
        comparative.setDuration(30);
        assertFalse(appointment.happensDuring(comparative));
    }

    @Test

    void happensDuringNegativeTest(){
        appointment.setTime(LocalTime.of(11, 30).toString());
        comparative.setTime(LocalTime.of(11, 30).toString());
        appointment.setDuration(-10);
        comparative.setDuration(-20);
        assertFalse(appointment.happensDuring(comparative));
    }

    @Test

    void happensDuringLongDurationTest() {

        appointment.setTime(LocalTime.of(13, 00).toString());
        comparative.setTime(LocalTime.of(14, 00).toString());
        appointment.setDuration(2000);
        comparative.setDuration(10);
        assertTrue(appointment.happensDuring(comparative));


    }

    @Test

    void sameTimeTest(){

        appointment.setTime(LocalTime.of(15, 30).toString());
        comparative.setTime(LocalTime.of(15, 30).toString());
        assertTrue(appointment.sameTime(comparative));
    }

    @Test

    void sameTimeDiferentTest() {

        appointment.setTime(LocalTime.of(15, 30).toString());
        comparative.setTime(LocalTime.of(16, 30).toString());
        assertFalse(appointment.sameTime(comparative));

    }



    private Appointment appointment;
    private Appointment comparative;
}