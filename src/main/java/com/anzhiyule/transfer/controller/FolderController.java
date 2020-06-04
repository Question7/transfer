package com.anzhiyule.transfer.controller;

import com.anzhiyule.transfer.model.Folder;
import com.anzhiyule.transfer.model.dto.FolderDTO;
import com.anzhiyule.transfer.service.IFolderService;
import com.anzhiyule.transfer.tools.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user/")
public class FolderController {

    private final IFolderService folderService;

    @Autowired
    public FolderController(IFolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping(value = "folder/dst", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Folder[]> listDstFolders() {
        String uid = CurrentUser.getUser();
        Folder[] folders = folderService.listFoldersByDst(uid);
        return ResponseEntity.ok(folders);
    }

    @GetMapping(value = "folder/src", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Folder[]> listSrcFolders() {
        String uid = CurrentUser.getUser();
        Folder[] folders = folderService.listFoldersBySrc(uid);
        return ResponseEntity.ok(folders);
    }

    @PostMapping(value = "folder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addFolder(@RequestBody FolderDTO folder) {
        Folder folder1 = new Folder();
        folder1.setName(folder.getName());
        folder1.setComment(folder.getComment());
        System.out.println(folder.getUid().length);
        String id = folderService.addFolder(folder1, folder.getUid());
        return ResponseEntity.ok(id);
    }

    @DeleteMapping(value = "folder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFolder(@RequestParam String id) {
        folderService.deleteFolder(id);
        return ResponseEntity.ok("success");
    }

}
