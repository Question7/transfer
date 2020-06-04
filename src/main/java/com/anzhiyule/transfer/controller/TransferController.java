package com.anzhiyule.transfer.controller;

import com.anzhiyule.transfer.model.Target;
import com.anzhiyule.transfer.model.Transfer;
import com.anzhiyule.transfer.model.User;
import com.anzhiyule.transfer.service.ITransferService;
import com.anzhiyule.transfer.service.IUserService;
import com.anzhiyule.transfer.tools.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping(value = "/api/user/")
public class TransferController {

    private final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final ITransferService transferService;

    private final IUserService userService;

    private final String filePath;

    @Autowired
    public TransferController(ITransferService transferService, @Value("${file.temp.location}") String filePath, IUserService userService) {
        this.transferService = transferService;
        this.filePath = filePath;
        this.userService = userService;
    }

    @GetMapping(value = "transfer/src", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transfer[]> querySrcTransfers() {
        String uid = CurrentUser.getUser();
        Transfer[] transfers = transferService.listTransfersBySrc(uid);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping(value = "transfer/dst", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transfer[]> queryDstTransfers() {
        String uid = CurrentUser.getUser();
        Transfer[] transfers = transferService.listTransfersByDst(uid);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping(value = "transfer/folder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transfer[]> queryTransfersByFolder(@RequestParam String folderId) {
        Transfer[] transfers = transferService.selectTransferByFolder(folderId);
        return ResponseEntity.ok(transfers);
    }

    @PostMapping(value = "transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addTransfer(@RequestParam Target.Type type, @RequestParam String target, @RequestParam MultipartFile file) {
        String id = transferService.addTransfers(type, target, file);
        return ResponseEntity.ok(id);
    }

    @GetMapping(value = "transfer/download")
    public void download(@RequestParam String id, HttpServletResponse response) {
        String uid = CurrentUser.getUser();
        Transfer transfer = transferService.getTransferById(id);
        if (!this.validateOwnFile(uid, transfer)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        String filename = transfer.getFilename();
        File file = new File(filePath + transfer.getUid() + File.separator + filename);
        try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            response.setHeader("Content-Length", file.length() + "");
            byte[] bs = new byte[1024];
            int len;
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private boolean validateOwnFile(String uid, Transfer transfer) {
        if (ObjectUtils.isEmpty(transfer)) {
            return false;
        }
        if (uid.equals(transfer.getUid())) {
            return true;
        }
        if (transfer.getTargetUser() != null && !"".equals(transfer.getTargetUser())) {
            return uid.equals(transfer.getTargetUser());
        }
        if (transfer.getTargetFolder() == null || "".equals(transfer.getTargetFolder())) {
            return false;
        }
        User user = userService.getUserByFolderAndId(transfer.getTargetFolder(), uid);
        return user != null && user.getId() != null;
    }
}
