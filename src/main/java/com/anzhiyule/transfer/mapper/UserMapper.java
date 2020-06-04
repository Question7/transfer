package com.anzhiyule.transfer.mapper;

import com.anzhiyule.transfer.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Results(
            id = "user",
            value = {
                    @Result(property = "id", column = "user_id"),
                    @Result(property = "username", column = "user_username"),
                    @Result(property = "nick", column = "user_nick"),
                    @Result(property = "password", column = "user_password"),
                    @Result(property = "role", column = "user_role")
            }
    )
    @Select("select * from user where user_username = #{username}")
    User selectUserByUsername(@Param("username") String username);

    @ResultMap("user")
    @Select("select * from user where user_id = #{id}")
    User selectUserById(@Param("id") String id);

    @ResultMap("user")
    @Select("select * from user order by user_username")
    User[] selectUsers();

    @ResultMap("user")
    @Select("select user.* from folder_user left join user on folder_user.user_id = user.user_id where folder_user.folder_id = #{folderId}")
    User[] selectUserByFolderId(@Param("folderId") String folderId);

    @ResultMap("user")
    @Select("select user_id from folder_user where folder_id = #{folderId} and user_id = #{userId}")
    User selectUserByFolderIdAndUserId(@Param("folderId") String folderId, @Param("userId") String userId);

    @Insert("insert into user(user_id, user_username, user_nick, user_password, user_role) " +
            "values (#{user.id}, #{user.username}, #{user.nick}, #{user.password}, #{user.role})")
    void insertUser(@Param("user") User user);

    @Update("update user set user_password = #{password} where user_id = #{id}")
    void updateUserPassword(@Param("password") String password, @Param("id") String id);

}
