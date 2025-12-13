package com.example.jaebitly

import com.example.jaebitly.application.RedirectStatStore
import com.example.jaebitly.domain.ShortKey
import com.jayway.jsonpath.JsonPath
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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
class RedirectStatAccumulationIntegrationTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var statStore: RedirectStatStore

    @Test
    fun `redirect accumulates stat in store`() {
        // given: short link 생성
        val createResponse =
            mockMvc
                .post("/links") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"originalUrl":"https://example.com"}"""
                }.andExpect {
                    status { isOk() }
                }.andReturn()

        val shortKeyValue =
            JsonPath.read<String>(createResponse.response.contentAsString, "$.shortKey")
        val key = ShortKey(shortKeyValue)

        // when: redirect 호출
        mockMvc
            .get("/$shortKeyValue")
            .andExpect { status { isFound() } }

        // then: store에 통계 누적 확인
        val stat = statStore.findByShortKey(key)
        assertNotNull(stat)
        assertEquals(1L, stat!!.count)
    }
}
