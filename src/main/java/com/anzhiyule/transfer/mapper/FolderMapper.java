package com.anzhiyule.transfer.mapper;

import com.anzhiyule.transfer.model.Folder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public interface FolderMapper {

    @Results(
            id = "folder",
            value = {
                    @Result(property = "id", column = "folder_id"),
                    @Result(property = "name", column = "folder_name"),
                    @Result(property = "comment", column = "folder_comment"),
                    @Result(property = "uid", column = "folder_uid"),
                    @Result(property = "createTime", column = "folder_createTime")
            }
    )
    @Select("select * from folder where folder_id = #{id}")
    Folder selectFolderById(@Param("id") String id);

    @ResultMap("folder")
    @Select("select * from folder where folder_uid = #{uid} order by folder_createTime desc")
    Folder[] selectFoldersBySrcUid(@Param("uid") String uid);

    @ResultMap("folder")
    @Select("select folder.* from folder left join folder_user on folder.folder_id = folder_user.folder_id where folder_user.user_id = #{uid} order by folder_createTime desc")
    Folder[] selectFoldersByDstUid(@Param("uid") String uid);

    @Transactional
    @Insert("insert into folder (folder_id, folder_name, folder_comment, folder_uid, folder_createTime)" +
            " values(#{folder.id}, #{folder.name}, #{folder.comment}, #{folder.uid}, #{folder.createTime})")
    void insertFolder(@Param("folder") Folder folder);

    @Insert("insert into folder_user(folder_id, user_id) values (#{folderId}, #{uid})")
    void insertFolderUser(@Param("folderId") String folderId, @Param("uid") String uid);

    @Delete("delete from folder where folder_id = #{id}")
    void deleteFolder(@Param("id") String id);

    @Delete("delete from folder_user where folder_id = #{id}")
    void deleteFolderUser(@Param("id") String id);
}
