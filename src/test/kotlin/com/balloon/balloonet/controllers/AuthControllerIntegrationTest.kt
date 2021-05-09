package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.AuthRequest
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIntegrationTest {


    lateinit var mockMvc: MockMvc


    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Before
    public fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Autowired
    lateinit var userRepo: UserRepo


    @Throws(JsonProcessingException::class)
    protected fun mapToJson(obj: Any?): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(obj)
    }

    @Test
    fun register_ordinary_user() {
        val user = User(email = "a@gmail.com", password = "amir")

        val inputJson: String = mapToJson(user)
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(inputJson))
            .andExpect(status().isOk)
    }

    @Test
    fun register_repetitive_user() {
        val user = User(email = "a@gmail.com", password = "amir")

        val inputJson: String = mapToJson(user)
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(inputJson))
        mockMvc.perform(post("/auth/signup").contentType(MediaType.APPLICATION_JSON).content(inputJson))
            .andExpect(status().isBadRequest)
    }


    @Test
    fun test_ordinary_user_login() {
        val authRequest = AuthRequest("a@gmail.com", "amir")
        val inputJson: String = mapToJson(authRequest)
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(inputJson))
            .andExpect(status().isOk)
    }

    @Test
    fun test_unknown_email_user_login() {
        val authRequest = AuthRequest("a32323@gmail.com", "amir")
        val inputJson: String = mapToJson(authRequest)
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(inputJson))
            .andExpect(status().isUnauthorized)
    }
    @Test
    fun test_unknown_password_user_login() {
        val authRequest = AuthRequest("a@gmail.com", "ami322r")
        val inputJson: String = mapToJson(authRequest)
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(inputJson))
            .andExpect(status().isUnauthorized)
    }



}