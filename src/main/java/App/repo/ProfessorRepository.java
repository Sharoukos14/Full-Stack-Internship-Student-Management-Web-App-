package App.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import App.domain.*;



public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByUsername(String username);
    
}

