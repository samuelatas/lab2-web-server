# Lab 2 Web Server -- Project Report

## Description of Changes
1. Se ha creado una página de error personalizada (`error.html`) que se muestra cuando ocurre un error en la aplicación. Esta plantilla muestra el código HTTP del error utilizando una variable de plantilla (`status`).

2. También se ha implementado un test de integración que valida el uso de la página de error personalizada. Este test levanta el servidor completo y realiza una petición HTTP real a una ruta inexistente, comprobando que la respuesta contiene el contenido esperado de la plantilla de error (`¡Ups! Algo salió mal.` y `Código de error`).

3. Se ha creado un nuevo endpoint REST (`/time`) que devuelve la hora actual del servidor en formato estructurado JSON. Para ello, se ha implementado un componente de tiempo (`TimeProvider` y `TimeService`), un DTO (`TimeDTO`) y un controlador REST (`TimeController`).

4. Además, se ha añadido un test que comprueba el correcto funcionamiento del endpoint `/time`. El test utiliza MockMvc para realizar una petición GET al endpoint, comprobando que la respuesta tiene código 200 OK, tipo `application/json` y contiene el campo `time` con la hora actual. Este test asegura que el endpoint responde correctamente.

5. Se ha habilitado soporte para HTTP/2 y SSL en la aplicación. Para ello, se ha generado un certificado autofirmado (localhost.crt y localhost.key) usando OpenSSL, y se ha creado un keystore PKCS12 (localhost.p12) que se ha movido a `src/main/resources`. La configuración de Spring Boot se ha actualizado en `application.yml` para activar SSL y HTTP/2 en el puerto 8443, utilizando el keystore y la contraseña definida.

## Technical Decisions

1. Para los tests, se ha empleado `TestRestTemplate` y la anotación `@SpringBootTest(webEnvironment = RANDOM_PORT)` para asegurar que se levanta el servidor completo y se valida la respuesta HTML real en el caso de la página de error.

2. Para el endpoint `/time`, se ha utilizado MockMvc para comprobar la respuesta JSON y la correcta integración del controlador y el servicio de tiempo.

## Learning Outcomes
1. Aprendí a crear páginas de error personalizadas en aplicaciones web y a utilizar variables de plantilla para mostrar información dinámica (como el código de error HTTP).

2. Comprendí la diferencia entre tests con MockMvc y tests de integración completos, y cómo asegurar que la validación de la página de error y del endpoint `/time` se realiza en condiciones reales de ejecución y con verificación de la estructura de la respuesta.

3. También aprendí a generar certificados autofirmados y a crear keystores PKCS12 con OpenSSL, así como a configurar Spring Boot para habilitar SSL y HTTP/2.

## AI Disclosure
### AI Tools Used
- GitHub Copilot

### AI-Assisted Work
- La plantilla `error.html` fue generada con asistencia de IA, incluyendo el diseño visual y la integración de la variable para el código de error.

- Los tests de integración y MockMvc fueron sugeridos y estructurados con ayuda de IA, asegurando que se valida correctamente la respuesta HTML y JSON.

- El 90% del trabajo de la plantilla fue asistido por IA, mientras que la integración y adaptación final fue realizada manualmente.

- El 60% del trabajo de la creación y diseño inicial de los test fue asistido por IA, sin embargo se tuvieron que adpatra manualmente tanto para crear el test de integración simulado la ctivación complea de la app, como para mockear correctamente el ENDPOINT `/time` y simular su comportamiento real.

### Original Work
- La integración de la plantilla en el proyecto y la adaptación y ejecución de los tests se realizó manualmente.

- La parte de configuración de HTTP/2 y SSL, así como la generación y gestión de certificados, se ha realizado manualmente siguiendo los pasos indicados en la práctica.
