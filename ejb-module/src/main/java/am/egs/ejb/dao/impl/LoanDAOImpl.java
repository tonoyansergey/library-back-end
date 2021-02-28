package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.LoanDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.PeriodDTO;
import am.egs.ejb.model.dto.TopFiveDTO;
import am.egs.ejb.model.entity.BookEntity;
import am.egs.ejb.model.entity.LoanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class LoanDAOImpl implements LoanDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanDAOImpl.class);

    @Override
    public List<LoanEntity> findAll() {
        final String SELECT_ALL = "SELECT l from LoanEntity l";
        Query query = entityManager.createQuery(SELECT_ALL);

        LOGGER.info("attempt to get all loans from database");
        try {
            List<LoanEntity> loans = query.getResultList();
            if (!loans.isEmpty()) {
                return loans;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no loan data found in database", Response.Status.NOT_FOUND, "No loans found");
    }

    @Override
    public void save(final LoanEntity loanEntity) {
        LOGGER.info("attempt to save loan in database | book title: {}", loanEntity.getBook().getTitle());
        try {
            entityManager.persist(loanEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public LoanEntity findOneById(Long id) {
        LOGGER.info("attempt to get booking record from database by id: {}", id);
        try {
            LoanEntity loanEntity = entityManager.find(LoanEntity.class, id);
            if (loanEntity != null) {
                return loanEntity;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no loan record found in database", Response.Status.NOT_FOUND, "No loan record found");
    }


    @Override
    public void delete(LoanEntity loanEntity) {
        LOGGER.info("attempt to remove loan record in database for book | title: {}", loanEntity.getBook().getTitle());
        try {
            entityManager.remove(loanEntity);
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }



    @Override
    public List<TopFiveDTO> findTopFive(final String groupBy) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TopFiveDTO> criteriaQuery = cb.createQuery(TopFiveDTO.class);
        Root<LoanEntity> root = criteriaQuery.from(LoanEntity.class);
        Expression<BookEntity> groupByExp;
        System.out.println(groupBy);
        if (groupBy != null) {
            groupByExp = root.get("book").get(groupBy);
        } else {
            groupByExp = root.get("book");
        }
        Expression<Long> countExp = cb.count(groupByExp);
        CriteriaQuery<TopFiveDTO> select = criteriaQuery.multiselect(groupByExp, countExp);

        criteriaQuery.groupBy(groupByExp);
        criteriaQuery.having(cb.gt(cb.count(root),0));
        criteriaQuery.orderBy(cb.desc(countExp));

        TypedQuery<TopFiveDTO> query = entityManager.createQuery(select);

        try {
            List<TopFiveDTO> resultList = query.setMaxResults(5).getResultList();
            if (!resultList.isEmpty()) {
                return resultList;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no loan data found in database", Response.Status.NOT_FOUND, "No loans found");
        }

    @Override
    public List<TopFiveDTO> findByPeriod(PeriodDTO periodDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<TopFiveDTO> criteriaQuery = criteriaBuilder.createQuery(TopFiveDTO.class);
        Root<LoanEntity> root = criteriaQuery.from(LoanEntity.class);
        Expression<BookEntity> groupByExp = root.get("book");

        Expression<Long> countExp = criteriaBuilder.count(groupByExp);
        CriteriaQuery<TopFiveDTO> select = criteriaQuery.multiselect(groupByExp, countExp);

        ParameterExpression<java.util.Date> startParameter = criteriaBuilder.parameter(java.util.Date.class);
        ParameterExpression<java.util.Date> endParameter = criteriaBuilder.parameter(java.util.Date.class);

        Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("loanDate").as(java.util.Date.class), startParameter);
        Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("loanDate").as(java.util.Date.class), endParameter);

        Predicate and = criteriaBuilder.and(startDatePredicate, endDatePredicate);
        criteriaQuery.where(and);


        criteriaQuery.groupBy(groupByExp);
        criteriaQuery.having(criteriaBuilder.gt(criteriaBuilder.count(root),0));
        criteriaQuery.orderBy(criteriaBuilder.desc(countExp));

        System.out.println(periodDTO);
        TypedQuery<TopFiveDTO> query = entityManager.createQuery(select).setParameter(startParameter, periodDTO.getStartDate(), TemporalType.DATE)
                .setParameter(endParameter, periodDTO.getEndDate(), TemporalType.DATE);

        try {
            List<TopFiveDTO> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                return resultList;
            }
        } catch (RuntimeException e) {
            throw new RestException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        throw new RestException("no loan data found in database", Response.Status.NOT_FOUND, "No loans found");
    }
}

