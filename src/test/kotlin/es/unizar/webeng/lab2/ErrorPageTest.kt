package es.unizar.webeng.lab2

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.assertj.core.api.Assertions.assertThat

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErrorPageTest {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `custom error page is shown for not found`() {
        val headers = org.springframework.http.HttpHeaders()
        headers.accept = listOf(org.springframework.http.MediaType.TEXT_HTML)
        val entity = org.springframework.http.HttpEntity<String>(headers)
        val response: ResponseEntity<String> = restTemplate.exchange(
            "/ruta-inexistente",
            org.springframework.http.HttpMethod.GET,
            entity,
            String::class.java
        )
        assertThat(response.statusCode.is4xxClientError).isTrue()
        assertThat(response.headers.contentType?.toString()).contains("text/html")
        assertThat(response.body).contains("¡Ups! Algo salió mal.")
        assertThat(response.body).contains("Código de error")
    }
}
