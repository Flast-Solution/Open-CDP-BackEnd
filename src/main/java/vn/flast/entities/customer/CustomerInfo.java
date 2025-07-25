package vn.flast.entities.customer;

import vn.flast.models.*;
import java.util.List;

public class CustomerInfo {
    public CustomerPersonal iCustomer;
    public String saleName;
    public Data lead;
    public List<CustomerActivities> activities;
    public List<CustomerOrder> orders;
    public List<CustomerOrder> opportunities;
    public List<DataCare> dataCares;
}
