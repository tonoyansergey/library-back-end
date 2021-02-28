package am.egs.ejb.dao.impl;

import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.AuthorEntity;
import am.egs.ejb.model.entity.BookEntity;
import am.egs.ejb.model.entity.GenreEntity;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BookDAOlTest {

    @Mock
    private EntityManager mockedEntityManager;
    @Mock
    private Query query;

    private BookDAOImpl bookDAO;

    @Before
    public void setUp() throws Exception {
        bookDAO = new BookDAOImpl();
        bookDAO.setEntityManager(mockedEntityManager);
    }

    @Test
    public void findAll() {

        List<BookEntity> books = new ArrayList<>();
        when(mockedEntityManager.createQuery("SELECT b from BookEntity b")).thenReturn(query);

        Integer booksSize = null;

        try {
            booksSize = bookDAO.findAll().size();
        } catch (RestException e) {
            if (e.getStatus().equals(Response.Status.NOT_FOUND)) {
                booksSize = 0;
            }
        }
        System.out.println(booksSize);

        when(query.getResultList()).thenReturn(books);
        System.out.println(query.getResultList());

        BookEntity first = BookEntity.builder()
                .title("First")
                .author(new AuthorEntity("First Author"))
                .genre(new GenreEntity("First Genre"))
                .quantity(5L)
                .build();

        bookDAO.save(first);
        verify(mockedEntityManager).persist(any());

        books.add(first);
        assertThat(books).hasSize(1);

    }
}
