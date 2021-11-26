package ub.tp.reservaPeluquerias.Model;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>
{
    Appointment findById(int Id);
    Appointment findTop1ByCustomerIdOrderByIdDesc(int customer);
    Appointment[] findByCustomerId(int customer);
    Appointment[] findByConfirmed(Boolean b);
    Appointment[] findByBranchIdAndConfirmedEquals(Integer id, Boolean confirmed);
    boolean existsByBranchId(Integer id);
    boolean existsByServiceId(Integer id);
	boolean existsByCustomerId(Integer id);
	void deleteByDateBefore(LocalDate date);
}
