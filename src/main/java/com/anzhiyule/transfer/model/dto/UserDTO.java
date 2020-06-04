package com.anzhiyule.transfer.model.dto;

import com.anzhiyule.transfer.model.User;
import lombok.Data;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String nick;
    private String role;

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setNick(user.getNick());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public static UserDTO[] toDTO(User[] users) {
        if (users == null) {
            return null;
        }
        UserDTO[] userDTOS = new UserDTO[users.length];
        for (int i = 0; i < users.length; i ++) {
            userDTOS[i] = toDTO(users[i]);
        }
        return userDTOS;
    }
}
