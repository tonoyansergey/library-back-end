package am.egs.ejb.dao;

import am.egs.ejb.model.dto.UserLoginDTO;
import am.egs.ejb.model.entity.UserEntity;
import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDAO {

    List<UserEntity> findAll();

    UserEntity findOneById(final Long id);

    UserEntity findOneByUserName(final String userName);

    boolean isExist(final String userName);

    UserEntity findOneByUserNameAndPassword(final UserLoginDTO userLoginDTO);

    void update(final UserEntity userEntity);

    void updatePassword(final Long userId, final String newPassword);

    void save(final UserEntity userEntity);

    void delete(final UserEntity userEntity);
}
