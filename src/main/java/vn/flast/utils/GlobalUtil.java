package vn.flast.utils;
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

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Log4j2
public class GlobalUtil {

    public static int getFolderUpload(Integer objectId) {
        String _dateStr = new SimpleDateFormat("MM-01-yyyy").format(new Date(objectId * 1000L));
        try {
            Date _date = new SimpleDateFormat("MM-01-yyyy").parse(_dateStr);
            return (int) Math.ceil((double) dateToInt(_date)/2048);
        } catch (ParseException e) {
            log.info("ParseException Date : {}", e.getMessage());
            return 0;
        }
    }

    public static int dateToInt() {
        return (int) (new Date().getTime()/1000);
    }

    public static int dateToInt(Date _d) {
        return (int) (_d.getTime()/1000);
    }
}
