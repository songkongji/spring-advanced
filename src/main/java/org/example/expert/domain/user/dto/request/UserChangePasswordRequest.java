package org.example.expert.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "새 비밀번호는 8자 이상이어야 하고, 숫자와 대문자를 포함해야 합니다."
    )
    private String newPassword;
}
// ^ : 문자열의 시작
// (?=.*[A-Z]) : 적어도 하나의 대문자를 포함하는지 확인
// (?=.*\d) : 적어도 하나의 숫자를 포함하는지 확인
// [A-Za-z\d]{8,} : 대문자, 소문자, 숫자로 구성된 최소 8자 이상의 문자열
// $ : 문자열의 끝
