package am.egs.ejb.dao;

import am.egs.ejb.model.dto.PeriodDTO;
import am.egs.ejb.model.dto.TopFiveDTO;
import am.egs.ejb.model.entity.LoanEntity;
import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface LoanDAO  {

    List<LoanEntity> findAll();

    void save(final LoanEntity loanEntity);

    LoanEntity findOneById(final Long id);

    void delete(final LoanEntity loanEntity);

    List<TopFiveDTO>  findTopFive(final String groupBy);

    List<TopFiveDTO> findByPeriod(final PeriodDTO periodDTO);
}
