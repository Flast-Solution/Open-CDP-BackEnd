package vn.flast.controller.marketting;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.flast.service.SaleReportService;

@RestController
@RequestMapping("/sale-report")
@RequiredArgsConstructor
public class SaleReportController {

    private final SaleReportService saleReportService;
}
