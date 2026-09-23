package App.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import App.domain.*;



public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByUsername(String username);
    
}

