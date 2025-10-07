package es.unizar.webeng.lab2

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.http.MediaType
import org.assertj.core.api.Assertions.assertThat
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@SpringBootTest
@AutoConfigureMockMvc
class TimeEndpointTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    data class TimeDTO(val time: String)

    @Test
    fun `time endpoint returns server time in structured format using mockmvc`() {
        val result = mockMvc.get("/time") {
            accept = MediaType.APPLICATION_JSON
        }
            .andExpect { status { isOk() } }
            .andExpect { content { contentTypeCompatibleWith(MediaType.APPLICATION_JSON) } }
            .andReturn()

        val body = result.response.contentAsString
        val mapper = jacksonObjectMapper()
        val timeDto: TimeDTO = mapper.readValue(body)
        assertThat(timeDto.time).isNotBlank()
    }
}

