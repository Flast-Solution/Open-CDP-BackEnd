package vn.flast.user;
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




import vn.flast.models.User;
import vn.flast.models.UserProfile;
import vn.flast.repositories.UserRepository;
import vn.flast.security.UserPrinciple;
import vn.flast.utils.SqlBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null ) {
            throw new UsernameNotFoundException("User Not Found with : " + username);
        }
        user.setUserProfiles(findProfile(user.getId()));
        return UserPrinciple.build(user);
    }

    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found !")
        );
        user.setUserProfiles(findProfile(id));
        return UserPrinciple.build(user);
    }

    public Set<UserProfile> findProfile(Integer uId) {
        String q = "FROM `user_profile` a LEFT JOIN `user_link_profile` b ON a.id = b.`user_profile_id`";
        SqlBuilder sqlBuilder = SqlBuilder.init(q);
        sqlBuilder.addIntegerEquals("b.`user_id`", uId);
        String fq = "SELECT a.* " + sqlBuilder.builder();
        var query = entityManager.createNativeQuery(fq, UserProfile.class);
        List<UserProfile> lists = sqlBuilder.getListOfNativeQuery(query, UserProfile.class);
        return new HashSet<>(lists);
    }
}
