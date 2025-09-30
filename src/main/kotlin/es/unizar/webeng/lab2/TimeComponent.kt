package es.unizar.webeng.lab2

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// DTO para transferir la hora
data class TimeDTO(val time: LocalDateTime)

// Interfaz para proveedor de tiempo
interface TimeProvider {
    fun now(): LocalDateTime
}

// Servicio que implementa la interfaz de tiempo
@Service
class TimeService : TimeProvider {
    override fun now(): LocalDateTime = LocalDateTime.now()
}

// Función de extensión para convertir LocalDateTime a TimeDTO
fun LocalDateTime.toDTO(): TimeDTO = TimeDTO(time = this)

// Controlador REST para exponer el endpoint /time
@RestController
class TimeController(private val service: TimeProvider) {
    @GetMapping("/time")
    fun time(): TimeDTO = service.now().toDTO()
}

