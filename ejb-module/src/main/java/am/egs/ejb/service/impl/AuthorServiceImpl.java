package am.egs.ejb.service.impl;

import am.egs.ejb.dao.AuthorDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.AuthorEntity;
import am.egs.ejb.model.pojo.Author;
import am.egs.ejb.service.AuthorService;
import am.egs.ejb.util.NullValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;

@Stateless
public class AuthorServiceImpl implements AuthorService {

    @EJB
    private AuthorDAO authorDAO;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);


    @Override
    public List<Author> findAll() {
        final Type targetListType = new TypeToken<List<Author>>() {}.getType();
        final List<Author> authors = mapper.map(authorDAO.findAll(), targetListType);

        LOGGER.info("got all authors from database, quantity: {}", authors.size());
        return authors;
    }

    @Override
    public void save(final Author author) {
        validator.constraintValidation(author);
        final String authorName = author.getAuthorName();

        if (!authorDAO.isExist(authorName)) {
            authorDAO.save(mapper.map(author, AuthorEntity.class));
            LOGGER.info("successfully saved new genre with name: {}", authorName);
        } else {
            throw new RestException("author already exists", Response.Status.BAD_REQUEST, "Author already exists !");
        }
    }
}
