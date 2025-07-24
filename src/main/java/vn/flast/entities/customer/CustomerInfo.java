package vn.flast.entities.customer;

import vn.flast.models.*;
import java.util.List;

public class CustomerInfo {
    public CustomerPersonal iCustomer;
    public String saleName;
    public List<CustomerActivities> activities;
    public List<CustomerOrderWithoutDetails> orders;
    public List<DataCare> dataCares;
}
