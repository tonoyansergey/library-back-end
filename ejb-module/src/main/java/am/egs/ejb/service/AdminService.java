package am.egs.ejb.service;

import am.egs.ejb.model.dto.AdminLoginDTO;
import am.egs.ejb.model.pojo.Admin;
import javax.ejb.Local;
import java.util.List;

@Local
public interface AdminService {

    List<Admin> findAll();

    Admin findOneByUserNameAndPassword(final AdminLoginDTO adminLoginDTO);
}
