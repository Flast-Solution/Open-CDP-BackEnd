package vn.flast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.flast.dao.UserProfileDao;
import vn.flast.models.User;
import vn.flast.pagination.Ipage;
import vn.flast.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserProfileDao userProfileDao;

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
    public void updateUser(User user, int id) {
        User entity = userRepository.findById(id).orElseThrow(
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


//    public List<User> findByType(String type) {
//        List<User> sUser = userRepository.findByType(type);
//        List<User> res = new ArrayList<>();
//        for (User element : sUser) {
//            User newUser = new User();
//            newUser.setId(element.getId());
//            newUser.setSsoId(element.getSsoId());
//            newUser.setFullName(element.getFullName());
//            res.add(newUser);
//        }
//        return res;
//    }
//
//    public Ipage<User> findAllUsers(User filter, int page) {
//        return userRepository.findAllUsers(filter, page);
//    }
//
//
//    public List<User> findAll(User filter) {
//        List<User> sUser = userRepository.findAll(filter);;
//        List<User> res = new ArrayList<>();
//        for (User element : sUser) {
//            User newUser = new User();
//            newUser.setId(element.getId());
//            newUser.setSsoId(element.getSsoId());
//            newUser.setFullName(element.getFullName());
//            newUser.setLayout(element.getLayout());
//            res.add(newUser);
//        }
//        return res;
//    }
//
//
//    public void deleteById(int id) {
//        userRepository.deleteById(id);
//    }
//
//
//    public boolean isAdmin(int uId) {
//        var user = this.findById(uId);
//        return checkRuleAdmin(user);
//    }
//
//    public boolean isPercharging(int uId) {
//        var user = this.findById(uId);
//        return user.checkRule(User.RULE_PERCHARING);
//    }
//
//    private boolean checkRuleAdmin(User user) {
//        if(user == null){
//            return false;
//        }
//        return user.checkRule(User.RULE_ADMIN);
//    }
//
//
//    public boolean isSaleManager(int id) {
//        var user = this.findById(id);
//        return user.ruleSaleManager();
//    }
//
//
//    public boolean isCustomerService(int id) {
//        var user = this.findById(id);
//        return user.ruleCskh();
//    }
//
//
//    public boolean isSaleMember(User user) {
//        return user.checkRule(User.RULE_SALE_MENBER);
//    }
//
//
//    public void changePass(ChangPass changPass) {
//        User user = dao.findById(changPass.getUId());
//        user.setPassword(passwordEncoder.encode(changPass.getNewPass()));
//        dao.updateUser(user);
//    }
//
//
//    public void changeInfo(ChangeInfo changeInfo) {
//        User userFetch = dao.findById(changeInfo.getId());
//        CopyProperties.CopyIgnoreNullWithZero(changeInfo, userFetch);
//        dao.updateUser(userFetch);
//    }

}
