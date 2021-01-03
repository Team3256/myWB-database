package com.bk1031.wbdatabase.service;

import com.bk1031.wbdatabase.model.User;
import java.util.List;

/**
 * User: bharat
 * Date: 1/1/21
 * Time: 11:05 PM
 */
public interface  UserService {

    public void addUser (User user);

    public List<User> getUsers ();
    public User getUser (String id);

    public User editUser (User user);

    public void deleteUser (String id);

    public boolean userExist (String id);
}
