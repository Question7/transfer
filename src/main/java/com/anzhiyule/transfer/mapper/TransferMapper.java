package com.anzhiyule.transfer.mapper;

import com.anzhiyule.transfer.model.Transfer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TransferMapper {

    @Results(
            id = "transfer",
            value = {
                    @Result(property = "id", column = "transfer_id"),
                    @Result(property = "filename", column = "transfer_filename"),
                    @Result(property = "extension", column = "transfer_extension"),
                    @Result(property = "size", column = "transfer_size"),
                    @Result(property = "createTime", column = "transfer_createTime"),
                    @Result(property = "uid", column = "transfer_uid"),
                    @Result(property = "targetUser", column = "transfer_targetUser"),
                    @Result(property = "targetFolder", column = "transfer_targetFolder")
            }
    )
    @Select("select * from transfer where transfer_id = #{id}")
    Transfer selectTransferById(@Param("id") String id);

    @ResultMap("transfer")
    @Select("select * from transfer order by transfer_createTime desc")
    Transfer[] selectTransfers();

    @ResultMap("transfer")
    @Select("select * from transfer where transfer_uid = #{uid} order by transfer_createTime desc")
    Transfer[] selectTransferBySrc(@Param("uid") String uid);

    @ResultMap("transfer")
    @Select("<script>select * from transfer where transfer_targetUser = #{uid} <if test='folders != null'> or transfer_targetFolder in" +
            " <foreach collection='folders' item='item' open='(' separator=',' close=')'>#{item}</foreach></if> order by transfer_createTime desc</script>")
    Transfer[] selectTransferByDst(@Param("uid") String uid, @Param("folders") String[] folders);

    @ResultMap("transfer")
    @Select("select * from transfer where transfer_targetFolder = #{folderId} order by transfer_createTime desc")
    Transfer[] selectTransferByFolder(@Param("folderId") String folderId);

    @Insert("insert into transfer(transfer_id,transfer_filename,transfer_extension,transfer_size,transfer_createTime,transfer_uid,transfer_targetUser, transfer_targetFolder)" +
            "values (#{transfer.id}, #{transfer.filename}, #{transfer.extension}, #{transfer.size}, #{transfer.createTime}, #{transfer.uid}, #{transfer.targetUser},#{transfer.targetFolder})")
    void insertTransfer(@Param("transfer") Transfer transfer);

    @Delete("delete from transfer where transfer_id = #{id}")
    void deleteTransfer(@Param("id") String id);

    @Delete("delete from transfer where transfer_targetFolder = #{folderId}")
    void deleteTransferByFolderId(@Param("folderId") String folderId);
}
