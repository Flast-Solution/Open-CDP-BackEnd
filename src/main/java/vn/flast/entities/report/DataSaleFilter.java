package vn.flast.entities.report;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataSaleFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;
    private List<Integer> saleId;
    private Integer leaderId;
    private String type;
}
