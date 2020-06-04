package com.anzhiyule.transfer.service;

import com.anzhiyule.transfer.model.Target;
import com.anzhiyule.transfer.model.Transfer;
import org.springframework.web.multipart.MultipartFile;

public interface ITransferService {

    Transfer getTransferById(String id);
    Transfer[] listTransfers();
    Transfer[] listTransfersBySrc(String uid);
    Transfer[] listTransfersByDst(String uid);
    Transfer[] selectTransferByFolder(String folderId);
    String addTransfers(Target.Type type, String target, MultipartFile file);
    void deleteTransfer(Transfer transfer);
}
