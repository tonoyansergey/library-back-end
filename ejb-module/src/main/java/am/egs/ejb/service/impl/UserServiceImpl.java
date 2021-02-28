package am.egs.ejb.service.impl;

import am.egs.ejb.dao.UserDAO;
import am.egs.ejb.exception.RestException;
import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.model.dto.UserPasswordUpdateDTO;
import am.egs.ejb.model.entity.UserEntity;
import am.egs.ejb.model.pojo.User;
import am.egs.ejb.service.UserService;
import am.egs.ejb.util.NullValidator;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.List;

@NoArgsConstructor
@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    private UserDAO userDAO;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<UserDTO> findAll() {
        final Type targetListType = new TypeToken<List<UserDTO>>() {}.getType();
        final List<UserDTO> users = mapper.map(userDAO.findAll(), targetListType);

        LOGGER.info("got all users from database, quantity: {}", users.size());
        return users;
    }

    @Override
    public UserDTO findOneById(final Long id) {
        final UserDTO user = mapper.map(userDAO.findOneById(id), UserDTO.class);
        LOGGER.info("got user from database with id: {}", id);
        return user;
    }

    @Override
    public User findOneByUserName(String userName) {
        final User user = mapper.map(userDAO.findOneByUserName(userName),User.class);
        LOGGER.info("got user from database with username: {}", userName);
        return user;
    }

    @Override
    public void save(final User user) {
        validator.constraintValidation(user);
        final String userName = user.getUserName();

        if (!userDAO.isExist(userName)) {
            userDAO.save(mapper.map(user, UserEntity.class));
            LOGGER.info("successfully saved new user with username: {}", userName);
        } else {
            throw new RestException("user with specified username already exists",Response.Status.BAD_REQUEST, "Username is already taken !");
        }
    }

    @Override
    public void delete(final Long id) {
        userDAO.delete(userDAO.findOneById(id));
        LOGGER.info("successfully deleted user from database");
    }

    @Override
    public UserDTO findOneByUserNameAndPassword(final UserLoginDTO userLoginDTO) {
        validator.constraintValidation(userLoginDTO);
        final UserDTO user = mapper.map(userDAO.findOneByUserNameAndPassword(userLoginDTO), UserDTO.class);

        LOGGER.info("got user from database with username: {} and password", userLoginDTO.getUserName());
        return user;
    }

    @Override
    public void update(final Long id, final UserDTO userDTO) {
        validator.constraintValidation(userDTO);
        UserEntity userEntity = userDAO.findOneById(id);
        userEntity = mapper.map(userDTO, UserEntity.class);
        userDAO.update(userEntity);

        LOGGER.info("successfully updated user by given id: {}", userDTO.getId());
    }

    @Override
    public void updatePassword(final Long id, final UserPasswordUpdateDTO passwordUpdateDTO) {
        validator.constraintValidation(passwordUpdateDTO);

        final String newPassword = passwordUpdateDTO.getNewPassword();
        final Long userId = findOneByUserNameAndPassword(new UserLoginDTO(passwordUpdateDTO.getUserName(), passwordUpdateDTO.getOldPassword())).getId();

        if (id.equals(userId)) {
            userDAO.updatePassword(userId, newPassword);
        } else {
            throw new RestException("Wrong user",Response.Status.BAD_REQUEST, "Wrong user details");
        }
        LOGGER.info("successfully updated password for user id: {}", userId);
    }
}
