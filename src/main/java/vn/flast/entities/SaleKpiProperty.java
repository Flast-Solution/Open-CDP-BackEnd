package vn.flast.entities;

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
    private Date inTime;
    private int department = UserKpi.DEPARTMENT_SALE;

    public Long calculatorTotalFee () {
        if(listFee.isEmpty()) {
            return 0L;
        }
        return listFee.stream().reduce(0L, (partialFeeResult, item) -> partialFeeResult + item.fee, Long::sum);
    }
}
