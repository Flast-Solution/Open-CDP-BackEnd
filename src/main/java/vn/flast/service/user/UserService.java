package vn.flast.service.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.dao.UserProfileDao;
import vn.flast.entities.user.ChangPass;
import vn.flast.entities.user.ChangeInfo;
import vn.flast.models.User;
import vn.flast.models.UserGroup;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserRepository;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProfileDao userProfileDao;

    @PersistenceContext
    protected EntityManager entityManager;

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Không tồn tại user này")
        );
    }

    public User findBySSO(String sso) {
        return userRepository.findBySsoId(sso);
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(user.getPassword());
        userRepository.save(user);
        userProfileDao.updateLinkProfile(user.permissions, user.getId());
    }


    @Transactional
    public void updateUser(User user) {
        User entity = userRepository.findById(user.getId()).orElseThrow(
                () -> new RuntimeException("Không tìm thấy bản ghi")
        );
        if (entity != null) {
            entity.setSsoId(user.getSsoId());
            if (!user.getPassword().equals(entity.getPassword())) {
                entity.setPassword(user.getPassword());
            }
            entity.setFullName(user.getFullName());
            entity.setEmail(user.getEmail());
            entity.setUserProfiles(user.getUserProfiles());
//            dao.updateUser(user);
            userProfileDao.updateLinkProfile(user.permissions, user.getId());
        }
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }




    public void deleteById(int id) {
        userRepository.deleteById(id);
    }


    public boolean isAdmin(int uId) {
        var user = this.findById(uId);
        return checkRuleAdmin(user);
    }
//
//    public boolean isPercharging(int uId) {
//        var user = this.findById(uId);
//        return user.checkRule(User.RULE_PERCHARING);
//    }
//
    private boolean checkRuleAdmin(User user) {
        if(user == null){
            return false;
        }
        return user.checkRule(User.RULE_ADMIN);
    }

    public boolean isSaleManager(int id) {
        var user = this.findById(id);
        return user.ruleSaleManager();
    }


    public boolean isCustomerService(int id) {
        var user = this.findById(id);
        return user.ruleCskh();
    }


    public boolean isSaleMember(User user) {
        return user.checkRule(User.RULE_SALE_MENBER);
    }

    public void changePass(ChangPass changPass) {
        User user = userRepository.findById(changPass.getUId()).orElseThrow(
                () -> new RuntimeException("Không tồn tại ban ghi này")
        );
        user.setPassword(passwordEncoder.encode(changPass.getNewPass()));
        updateUser(user);
    }


    public void changeInfo(ChangeInfo changeInfo) {
        User userFetch = userRepository.findById(changeInfo.getId()).orElseThrow(
                () -> new RuntimeException("Không tồn tại ban ghi này")
        );
        CopyProperty.CopyIgnoreNull(changeInfo, userFetch);
        updateUser(userFetch);
    }

    public UserGroup findByLeaderId(int leaderId) {
        EntityQuery<UserGroup> et = EntityQuery.create(entityManager, UserGroup.class);
        return et.integerEqualsTo("leaderId", leaderId).uniqueResult();
    }

}
