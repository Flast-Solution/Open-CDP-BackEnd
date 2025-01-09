package vn.flast.dao;

import vn.flast.models.DataOwner;

public interface DataOwnerDao {

    DataOwner findByPhone(String phone);
}
