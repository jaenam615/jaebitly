package com.example.jaebitly

import com.example.jaebitly.JaebitlyApplication
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class RedirectIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `invalid shortKey returns 404`() {
        mockMvc.get("/invalid-key")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `valid shortKey redirects with 302`(){
        // given: create link
        val createResponse = mockMvc.post("/links"){
            contentType = MediaType.APPLICATION_JSON
            content = """{"originalUrl" : "https://example.com"}"""
        }.andExpect {
            status { isOk() }
        }.andReturn()

        println(createResponse)
        println(createResponse.response.contentAsString)

        val responseBody = createResponse.response.contentAsString
        val shortKey = JsonPath.read<String>(responseBody, "$.shortKey")

        // when & then: redirect
        mockMvc.get("/$shortKey")
            .andExpect {
                status { isFound() }
                header { string(HttpHeaders.LOCATION, "https://example.com") }
            }
    }
}