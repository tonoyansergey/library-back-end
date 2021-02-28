package am.egs.ejb.service.impl;

import am.egs.ejb.dao.GenreDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.entity.GenreEntity;
import am.egs.ejb.model.pojo.Genre;
import am.egs.ejb.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    @EJB
    private GenreDAO genreDAO;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreServiceImpl.class);


    @Override
    public List<Genre> findAll() {
        final Type targetListType = new TypeToken<List<Genre>>() {}.getType();
        final List<Genre> genres = mapper.map(genreDAO.findAll(), targetListType);

        LOGGER.info("got all genres from database, quantity: {}", genres.size());
        return genres;
    }

    @Override
    public void save(final Genre genre) {
        validator.constraintValidation(genre);
        final String genreName = genre.getGenreName();

        if (!genreDAO.isExist(genreName)) {
            genreDAO.save(mapper.map(genre, GenreEntity.class));
            LOGGER.info("successfully saved new genre with name: {}", genreName);
        } else {
            throw new RestException("genre already exists", Response.Status.BAD_REQUEST, "Genre already exists !");
        }
    }
}
