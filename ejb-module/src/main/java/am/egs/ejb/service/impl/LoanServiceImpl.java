package am.egs.ejb.service.impl;

import am.egs.ejb.dao.LoanDAO;
import am.egs.ejb.model.dto.TopFiveDTO;
import am.egs.ejb.model.entity.LoanEntity;
import am.egs.ejb.model.pojo.Loan;
import am.egs.ejb.service.BookService;
import am.egs.ejb.service.LoanService;
import am.egs.ejb.util.NullValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.lang.reflect.Type;
import java.util.List;

@Stateless
public class LoanServiceImpl implements LoanService {

    @EJB
    private LoanDAO loanDAO;
    @EJB
    private BookService bookService;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanServiceImpl.class);


    @Override
    public List<Loan> findAll() {
        final Type targetListType = new TypeToken<List<Loan>>() {}.getType();
        final List<Loan> loans = mapper.map(loanDAO.findAll(), targetListType);
        LOGGER.info("got all loans from database, quantity: {}", loans.size());
        return loans;
    }

    @Override
    public void save(final Loan loan) {
        validator.constraintValidation(loan);
        loanDAO.save(mapper.map(loan, LoanEntity.class));
        bookService.decrementQuantityByOne(loan.getBook().getId());
        LOGGER.info("successfully saved new loan for book,  title: {}", loan.getBook().getTitle());
    }

    @Override
    public void delete(Long id) {
        LoanEntity loanEntity = loanDAO.findOneById(id);
        loanDAO.delete(loanEntity);
        bookService.incrementQuantityByOne(loanEntity.getBook().getId());
        LOGGER.info("successfully deleted loan record in database");
    }

    @Override
    public List<TopFiveDTO> findTopFive(final String groupBy) {
        LOGGER.info("got top five loaned books from database");
        return loanDAO.findTopFive(groupBy);
    }
}
