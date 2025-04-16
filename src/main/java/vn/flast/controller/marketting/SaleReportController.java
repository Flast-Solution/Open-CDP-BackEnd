package vn.flast.controller.marketting;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.entities.MyResponse;
import vn.flast.entities.report.DataSaleFilter;
import vn.flast.service.SaleReportService;

@RestController
@RequestMapping("/sale-report")
@RequiredArgsConstructor
public class SaleReportController {

    private final SaleReportService saleReportService;

    @GetMapping("/data-lead")
    public MyResponse<?> getReportDataLead(DataSaleFilter filter){
        var data = saleReportService.reportDataLeadSale(filter);
        return MyResponse.response(data);
    }
}
