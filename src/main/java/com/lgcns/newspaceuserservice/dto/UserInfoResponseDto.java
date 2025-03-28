package com.lgcns.newspaceuserservice.dto;

import com.lgcns.newspaceuserservice.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private String name;
    private String nickname;
    private String birth;
    private String role;
    private String profileImage;
    
    public UserInfoResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.birth = user.getBirth();
        this.role = String.valueOf(user.getUserRole());
        this.profileImage = user.getProfileImage();
    }
}
