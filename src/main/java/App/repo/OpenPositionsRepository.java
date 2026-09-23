package App.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import App.domain.*;



public interface OpenPositionsRepository extends JpaRepository<OpenPosition, Long> {
    List<OpenPosition> findByCompanyUsername(String companyUsername);
}

