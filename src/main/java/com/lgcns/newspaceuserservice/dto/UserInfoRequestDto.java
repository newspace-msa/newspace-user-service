package com.lgcns.newspaceuserservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoRequestDto {
    private String nickname;
    private String newPassword;
    private String newPasswordConfirm;
}
