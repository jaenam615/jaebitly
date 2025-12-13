package com.example.jaebitly

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.infrastructure.InMemoryRedirectStatHandler
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class RedirectStatIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var statHandler: InMemoryRedirectStatHandler

    @BeforeEach
    fun setUp() {
        statHandler.clear()
    }

    @Test
    fun `redirect accumulates stats in memory`() {
        // given: short link 생성
        val createResponse =
            mockMvc
                .post("/links") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"originalUrl":"https://example.com"}"""
                }.andReturn()

        val shortKeyValue =
            JsonPath.read<String>(createResponse.response.contentAsString, "$.shortKey")
        val shortKey = ShortKey(shortKeyValue)

        // when: redirect 요청
        mockMvc.get("/$shortKeyValue").andReturn()

        // then: 최대 1초 폴링 후 count 검증
        val deadline = System.currentTimeMillis() + 1000
        var count: Long? = null

        while (System.currentTimeMillis() < deadline) {
            count = statHandler.getStat(shortKey)?.count
            if (count != null) break
            Thread.sleep(20)
        }

        assertEquals(1L, count)
    }
}
