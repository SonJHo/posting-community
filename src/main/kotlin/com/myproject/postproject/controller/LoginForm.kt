package com.myproject.postproject.controller

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

class LoginForm {
    @NotEmpty(message = "id를 입력하세요")
    var accountId : String? = null


    @NotEmpty(message = "pw를 입력하세요")
    var password : String? = null
}