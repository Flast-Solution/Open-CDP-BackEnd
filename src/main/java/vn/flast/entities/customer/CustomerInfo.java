package vn.flast.entities.customer;

import vn.flast.models.CustomerOrder;
import vn.flast.models.CustomerPersonal;
import vn.flast.models.Data;
import vn.flast.models.DataCare;
import vn.flast.models.User;
import vn.flast.service.customer.CustomerService;

import java.util.List;

public class CustomerInfo {

    public CustomerPersonal iCustomer;
    public User saleTakeCare;
    public List<Data> lichSuTuongTac;
    public List<CustomerOrder> donChuaHoanThanh;
    public List<CustomerOrder> baDonGanNhat;
    public List<CustomerService> customerService;
    public List<DataCare> dataCares;
}
