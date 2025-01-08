package vn.flast.dao;

import vn.flast.models.Data;

import java.util.List;

public interface DataDao {

    List<Data> lastInteracted(String phone);
}
