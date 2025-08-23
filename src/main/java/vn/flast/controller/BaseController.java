package vn.flast.controller;
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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.flast.security.UserPrinciple;
import java.util.Optional;

@Component
public class BaseController {

    protected String getUsername() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getName).orElse(null);
    }

    protected int getUserId() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getId).orElse(0);
    }

    protected String getUserSsoId() {
        return Optional.ofNullable(getInfo()).map(UserPrinciple::getSsoId).orElse(null);
    }

    public UserPrinciple getInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple) {
            return (UserPrinciple) principal;
        }
        return null;
    }
}
