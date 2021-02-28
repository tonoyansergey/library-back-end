package am.egs.ejb.service;

import am.egs.ejb.model.dto.UserDTO;
import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.model.dto.UserPasswordUpdateDTO;
import am.egs.ejb.model.dto.UserSignUpDTO;
import am.egs.ejb.model.pojo.User;
import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {

    List<UserDTO> findAll();

    UserDTO findOneById(final Long id);

    User findOneByUserName(final String userName);

    UserDTO findOneByUserNameAndPassword(final UserLoginDTO userLoginDTO);

    void update(final Long id, final UserDTO userDTO);

    void updatePassword(final Long id, final UserPasswordUpdateDTO passwordUpdateDTO);

    void save(final User user);

    void delete(final Long id);
}
