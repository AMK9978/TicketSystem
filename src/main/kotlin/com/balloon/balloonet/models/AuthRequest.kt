package com.balloon.balloonet.models


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
class AuthRequest {
    var email: String = ""
    var password: String = ""
}