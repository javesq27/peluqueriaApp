package ub.tp.reservaPeluquerias.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer>
{
    Service findById(int id);
    Service findByName(String name);
}
