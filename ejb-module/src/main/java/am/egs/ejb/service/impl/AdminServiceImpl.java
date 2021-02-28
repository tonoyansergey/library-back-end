package am.egs.ejb.service.impl;

import am.egs.ejb.dao.AdminDAO;
import am.egs.ejb.model.dto.AdminLoginDTO;
import am.egs.ejb.model.pojo.Admin;
import am.egs.ejb.service.AdminService;
import am.egs.ejb.util.NullValidator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.lang.reflect.Type;
import java.util.List;

@Stateless
public class AdminServiceImpl implements AdminService {

    @EJB
    private AdminDAO adminDAO;
    @EJB
    private NullValidator validator;
    private static final ModelMapper mapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public List<Admin> findAll() {
        final Type targetListType = new TypeToken<List<Admin>>() {}.getType();
        final List<Admin> admins = mapper.map(adminDAO.findAll(), targetListType);

        LOGGER.info("got all admins from database, quantity: {}", admins.size());
        return admins;
    }

    @Override
    public Admin findOneByUserNameAndPassword(final AdminLoginDTO adminLoginDTO) {
        validator.constraintValidation(adminLoginDTO);
        final Admin admin = mapper.map(adminDAO.findOneByUserNameAndPassword(adminLoginDTO), Admin.class);

        LOGGER.info("got admin from database with username: {} and password", adminLoginDTO.getUserName());
        return admin;
    }
}
