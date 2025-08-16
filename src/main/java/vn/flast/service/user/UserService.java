package vn.flast.service.user;
/**************************************************************************/
/*  app.java                                                              */
/**************************************************************************/
/*                       Tệp này là một phần của:                         */
/*                             Open CDP                                   */
/*                        https://flast.vn                                */
/**************************************************************************/
/* Bản quyền (c) 2025 - này thuộc về các cộng tác viên Flast Solution     */
/* (xem AUTHORS.md).                                                      */
/* Bản quyền (c) 2024-2025 Long Huu, Thành Trung                          */
/*                                                                        */
/* Bạn được quyền sử dụng phần mềm này miễn phí cho bất kỳ mục đích nào,  */
/* bao gồm sao chép, sửa đổi, phân phối, bán lại…                         */
/*                                                                        */
/* Chỉ cần giữ nguyên thông tin bản quyền và nội dung giấy phép này trong */
/* các bản sao.                                                           */
/*                                                                        */
/* Đội ngũ phát triển mong rằng phần mềm được sử dụng đúng mục đích và    */
/* có trách nghiệm                                                        */
/**************************************************************************/

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.flast.dao.UserProfileDao;
import vn.flast.entities.user.ChangPass;
import vn.flast.entities.user.ChangeInfo;
import vn.flast.models.User;
import vn.flast.models.UserGroup;
import vn.flast.repositories.UserRepository;
import vn.flast.utils.Common;
import vn.flast.utils.CopyProperty;
import vn.flast.utils.EntityQuery;
import vn.flast.utils.GlobalUtil;
import vn.flast.utils.SqlBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserService {

    public static String UPLOAD_PATH =  "/uploads/user/";

    public static String folderUpload() {
        String fd = System.getProperty("user.dir") + UPLOAD_PATH + GlobalUtil.getFolderUpload(GlobalUtil.dateToInt())  + "/";
        return Common.makeFolder(fd);
    }

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
        entity.setSsoId(user.getSsoId());
        if (!user.getPassword().equals(entity.getPassword())) {
            entity.setPassword(user.getPassword());
        }
        entity.setFullName(user.getFullName());
        entity.setEmail(user.getEmail());
        entity.setUserProfiles(user.getUserProfiles());
        userProfileDao.updateLinkProfile(user.permissions, user.getId());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isAdmin(int uId) {
        var user = this.findById(uId);
        return checkRuleAdmin(user);
    }

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

    public List<User> findBySale() {
        String initQuery = "FROM user u LEFT JOIN user_link_profile p ON u.id = p.user_id";
        SqlBuilder sqlBuilder = SqlBuilder.init(initQuery);
        sqlBuilder.addIntegerEquals("u.status", 1);
        sqlBuilder.addIn("p.user_profile_id", List.of("5","13"));
        String finalQuery = sqlBuilder.builder();
        var users = entityManager.createNativeQuery("SELECT u.* " + finalQuery, User.class);
        return EntityQuery.getListOfNativeQuery(users, User.class);
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        var folderUpload = folderUpload();
        String fileName = multipartFile.getOriginalFilename();
        assert fileName != null : "File name not extract .!";

        String filePath = folderUpload + fileName;
        InputStream fileStream = multipartFile.getInputStream();
        File targetFile = new File(filePath);
        FileUtils.copyInputStreamToFile(fileStream, targetFile);
        return UPLOAD_PATH + fileName;
    }
}
