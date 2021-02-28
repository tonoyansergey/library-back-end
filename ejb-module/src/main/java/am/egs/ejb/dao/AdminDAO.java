package am.egs.ejb.dao;

import am.egs.ejb.model.dto.AdminLoginDTO;
import am.egs.ejb.model.entity.AdminEntity;
import javax.ejb.Local;
import java.util.List;

@Local
public interface AdminDAO {

    List<AdminEntity> findAll();

    AdminEntity findOneByUserNameAndPassword(final AdminLoginDTO adminLoginDTO);
}
