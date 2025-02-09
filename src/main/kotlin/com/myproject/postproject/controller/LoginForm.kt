package com.myproject.postproject.controller

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

class LoginForm {
    @NotEmpty
    var accountId : String? = null


    @NotEmpty
    var password : String? = null
}