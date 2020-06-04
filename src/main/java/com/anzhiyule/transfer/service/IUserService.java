package com.anzhiyule.transfer.service;

import com.anzhiyule.transfer.model.User;

public interface IUserService {

    User getUserByUsername(String username);
    User getUserById(String id);
    User getUserByFolderAndId(String folderId, String userId);
    User[] listUsers();
    User[] listUsersByFolderId(String folderId);
    String addUser(User user);
    String updatePassword(String opd, String npd);
}
