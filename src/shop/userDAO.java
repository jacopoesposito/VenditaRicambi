package shop;


import shop.model.UserModel;

import java.util.List;

public interface userDAO {

    public void insertUser(UserModel user);
    public UserModel selectUser(String mail);
    public List<UserModel> select();
}
