package com.balloon.balloonet.controllers

import com.balloon.balloonet.repos.UserRepo
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {


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
    fun make_admin_ordinary_user() {
        val user = userRepo.findByEmail("a@gmail.com")
        mockMvc.perform(post("/users/change").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("user_id", "${user.id}")
            .param("role_id", "1").header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhQGdtYWlsLmNvbSIsImlhdCI6MTYyMDUzMDY2MiwiZXhwIjoxNjIwNTY2NjYyfQ.Xtp8bbloB5wzblfgWly36zQKP-Yxu7ErTrYBxMCBtgA"
            )).andExpect(status().isOk)
    }


}