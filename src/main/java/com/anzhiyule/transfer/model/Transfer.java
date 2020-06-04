package com.anzhiyule.transfer.model;

import lombok.Data;

@Data
public class Transfer {

    private String id;
    private String filename;
    private String extension;
    private Long size;
    private Long createTime;
    private String uid;
    private String targetUser;
    private String targetFolder;
}
