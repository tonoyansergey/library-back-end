package am.egs.ejb.dao.impl;

import am.egs.ejb.dao.BookDAO;
import am.egs.ejb.dao.UserDAO;
import am.egs.ejb.model.UserRole;
import am.egs.ejb.model.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {

    @Mock
    private EntityManager mockedEntityManager;

    private BookDAO bookDAO;




    @Test
    public void findOneById() {

        UserDAO userDAO = mock(UserDAOImpl.class);

        UserEntity user = UserEntity.builder()
                .firstName("Tom")
                .lastName("Smith")
                .userName("tommy")
                .email("somemail@mail.com")
                .password("1111")
                .role(UserRole.USER)
                .build();

        userDAO.save(user);

//        when(userDAO.findOneById(1L)).thenReturn(user);

        System.out.println(userDAO.isExist("tommy"));

//        assertEquals("Smith", userDAO.findOneById(1L).getLastName());
        verify(userDAO).isExist("tommy");
    }
}
