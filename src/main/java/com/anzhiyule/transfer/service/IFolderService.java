package com.anzhiyule.transfer.service;

import com.anzhiyule.transfer.model.Folder;

public interface IFolderService {

    Folder getFolderById(String id);
    Folder[] listFoldersBySrc(String uid);
    Folder[] listFoldersByDst(String uid);
    String addFolder(Folder folder, String[] uid);
    void deleteFolder(String id);
}
