package com.anzhiyule.transfer.service.impl;

import com.anzhiyule.transfer.mapper.TransferMapper;
import com.anzhiyule.transfer.model.Folder;
import com.anzhiyule.transfer.model.Target;
import com.anzhiyule.transfer.model.Transfer;
import com.anzhiyule.transfer.service.IFolderService;
import com.anzhiyule.transfer.service.ITransferService;
import com.anzhiyule.transfer.tools.CurrentUser;
import com.anzhiyule.transfer.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class TransferServiceImpl implements ITransferService {

    private final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    private final TransferMapper transferMapper;

    private final IFolderService folderService;

    private final String filePath;

    @Autowired
    public TransferServiceImpl(TransferMapper transferMapper, @Value("${file.temp.location}") String filePath, IFolderService folderService) {
        this.transferMapper = transferMapper;
        this.filePath = filePath;
        this.folderService = folderService;
    }


    @Override
    public Transfer getTransferById(String id) {
        return transferMapper.selectTransferById(id);
    }

    @Override
    public Transfer[] listTransfers() {
        return transferMapper.selectTransfers();
    }

    @Override
    public Transfer[] listTransfersBySrc(String uid) {
        return transferMapper.selectTransferBySrc(uid);
    }

    @Override
    public Transfer[] listTransfersByDst(String uid) {
        Folder[] folders = folderService.listFoldersByDst(uid);
        String[] folderIds = null;
        if (folders != null && folders.length > 0) {
            folderIds = new String[folders.length];
            for (int i = 0; i < folderIds.length; i ++) {
                folderIds[i] = folders[i].getId();
            }
        }
        return transferMapper.selectTransferByDst(uid, folderIds);
    }

    @Override
    public Transfer[] selectTransferByFolder(String folderId) {
        return transferMapper.selectTransferByFolder(folderId);
    }

    @Override
    public String addTransfers(Target.Type type, String target, MultipartFile file) {
        String id = UUID.randomUUID().toString();
        String uid = CurrentUser.getUser();
        long now = System.currentTimeMillis();

        Transfer transfer = new Transfer();
        if (type == Target.Type.FOLDER) {
            Folder folder = folderService.getFolderById(target);
            if (folder == null || !uid.equals(folder.getUid())) {
                return "error";
            }
            transfer.setTargetFolder(target);
        } else {
            transfer.setTargetUser(target);
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            return "error";
        }
        long size = file.getSize();
        String extension = FileUtils.extension(filename);

        File directory = new File(filePath + uid);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File local = new File(directory.getPath() + File.separator + filename);
        try {
            file.transferTo(local);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return "error";
        }

        transfer.setId(id);
        transfer.setCreateTime(now);
        transfer.setUid(uid);
        transfer.setFilename(filename);
        transfer.setSize(size);
        transfer.setExtension(extension);

        transferMapper.insertTransfer(transfer);
        return id;
    }

    @Override
    public void deleteTransfer(Transfer transfer) {
        if (transfer == null) {
            return;
        }
        String id = transfer.getId();
        String uid = transfer.getUid();
        String filename = transfer.getFilename();
        File local = new File(filePath + uid + File.separator + filename);
        if (local.exists()) {
            local.delete();
        }
        transferMapper.deleteTransfer(id);
    }
}
