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

import java.util.HashMap;
import java.util.Map;

public class BuilderParams {

    private final Map<String, Object> maps;
    public BuilderParams() {
        maps = new HashMap<>();
    }

    public static BuilderParams create() {
        return new BuilderParams();
    }

    public BuilderParams addParam(String key, Object obj) {
        this.maps.put(key, obj);
        return this;
    }

    public Map<String, Object> getParams() {
        return maps;
    }

    public String toJson() {
        return JsonUtils.toJson(maps);
    }
}
