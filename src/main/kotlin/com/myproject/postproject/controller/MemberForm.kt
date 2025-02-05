package com.myproject.postproject.controller

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

class MemberForm {

    @NotEmpty(message = "Id를 입력하세요")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{7,13}$", message = "id는 영문자와 숫자의 조합으로 7 ~ 13자 사이여야 합니다.")
    var accountId :String? = null

    @NotEmpty(message = "pw는 필수입니다")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{7,13}$", message = "pw는 영문자와 숫자의 조합으로 7 ~ 13자 사이여야 합니다.")
    var password :String? = null

    @NotEmpty(message = "이름은 필수입니다")
    var name :String? = null


    @NotNull(message = "나이는 필수입니다")
    var age :Int? = null

    @NotEmpty(message = "전화번호는 필수입니다")
    var tel :String? = null

    @NotEmpty(message = "email은 필수입니다")
    var emailAddress :String? = null

}