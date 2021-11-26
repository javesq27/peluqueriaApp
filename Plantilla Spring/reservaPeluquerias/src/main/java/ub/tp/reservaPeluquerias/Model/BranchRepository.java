package ub.tp.reservaPeluquerias.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer>
{
    Branch findById(int id);
    Branch findByName(String name);
}
