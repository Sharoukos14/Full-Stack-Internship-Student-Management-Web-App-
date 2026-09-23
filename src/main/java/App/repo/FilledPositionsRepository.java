package App.repo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import App.domain.*;

public interface FilledPositionsRepository extends JpaRepository<FilledPosition, Long> {
    List<FilledPosition> findByCompanyUsername(String companyUsername);
    List<FilledPosition> findByProfessorUsername(String professorUsername);
    List<FilledPosition> findByStudentUsername(String studentUsername);
    
}

