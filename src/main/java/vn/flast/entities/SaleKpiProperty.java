package vn.flast.entities;
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




import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.flast.models.UserKpi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class SaleKpiProperty {
    private Integer id;
    private int userId = 0;
    private int type = 0;
    private int kpiTotal;
    private int kpiRevenue = 0;
    private int fee = 0;
    private List<FreeOfChannel> listFee = new ArrayList<>();
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date inTime = new Date();
    private int department = 0;

    public Long calculatorTotalFee () {
        if(listFee.isEmpty()) {
            return 0L;
        }
        return listFee.stream().reduce(0L, (partialFeeResult, item) -> partialFeeResult + item.fee, Long::sum);
    }
}
