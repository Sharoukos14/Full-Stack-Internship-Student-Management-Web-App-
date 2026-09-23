package App.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import App.domain.*;


public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);

	
    
}

