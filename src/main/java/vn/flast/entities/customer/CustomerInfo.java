package vn.flast.entities.customer;

import vn.flast.models.*;
import vn.flast.records.CustomerSummary;
import java.util.List;

public class CustomerInfo {
    public CustomerPersonal iCustomer;
    public String saleName;
    public Data lead;
    public CustomerSummary summary;
    public List<CustomerTags> tags;
    public List<FlastNote> notes;
    public List<CustomerActivities> activities;
    public List<CustomerOrder> orders;
    public List<CustomerOrder> opportunities;
}
