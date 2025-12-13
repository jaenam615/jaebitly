package com.example.jaebitly

import com.example.jaebitly.application.RedirectEventPublisher
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.timeout
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class RedirectAsyncPublishTest {
    @MockBean
    lateinit var redirectEventPublisher: RedirectEventPublisher

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `redirect triggers event publishing async and consume`() {
        // given: create link
        val createResponse =
            mockMvc
                .post("/links") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"originalUrl" : "https://example.com"}"""
                }.andExpect {
                    status { isOk() }
                }.andReturn()

        val responseBody = createResponse.response.contentAsString
        val shortKey = JsonPath.read<String>(responseBody, "$.shortKey")

        // when: redirect
        mockMvc
            .get("/$shortKey")
            .andExpect {
                status { isFound() }
            }

        //  then: event async
        verify(
            redirectEventPublisher,
            timeout(1000),
        ).publish(any())
    }
}
