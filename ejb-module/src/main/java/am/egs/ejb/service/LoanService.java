package am.egs.ejb.service;

import am.egs.ejb.model.dto.TopFiveDTO;
import am.egs.ejb.model.pojo.Loan;

import javax.ejb.Local;
import java.util.List;

@Local
public interface LoanService {

    List<Loan> findAll();

    void save(final Loan loan);

    void delete(final Long id);

    List<TopFiveDTO> findTopFive(final String groupBy);
}
