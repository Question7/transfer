package com.anzhiyule.transfer.service.impl;

import com.anzhiyule.transfer.mapper.FolderMapper;
import com.anzhiyule.transfer.mapper.TransferMapper;
import com.anzhiyule.transfer.model.Folder;
import com.anzhiyule.transfer.service.IFolderService;
import com.anzhiyule.transfer.tools.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class FolderServiceImpl implements IFolderService {

    private final FolderMapper folderMapper;

    private final TransferMapper transferMapper;

    @Autowired
    public FolderServiceImpl(FolderMapper folderMapper, TransferMapper transferMapper) {
        this.folderMapper = folderMapper;
        this.transferMapper = transferMapper;
    }

    @Override
    public Folder getFolderById(String id) {
        return folderMapper.selectFolderById(id);
    }

    @Override
    public Folder[] listFoldersBySrc(String uid) {
        return folderMapper.selectFoldersBySrcUid(uid);
    }

    @Override
    public Folder[] listFoldersByDst(String uid) {
        return folderMapper.selectFoldersByDstUid(uid);
    }

    @Override
    public String addFolder(Folder folder, String[] uid) {
        System.out.println(uid.length);
        long now = System.currentTimeMillis();
        String id = UUID.randomUUID().toString();
        String own = CurrentUser.getUser();
        folder.setId(id);
        folder.setUid(own);
        folder.setCreateTime(now);
        folderMapper.insertFolder(folder);
        Arrays.stream(uid).forEach(u -> folderMapper.insertFolderUser(id, u));
        return id;
    }

    @Override
    public void deleteFolder(String id) {
        transferMapper.deleteTransferByFolderId(id);
        folderMapper.deleteFolderUser(id);
        folderMapper.deleteFolder(id);
    }
}
