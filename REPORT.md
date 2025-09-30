# Lab 2 Web Server -- Project Report

## Description of Changes
Se ha creado una página de error personalizada (`error.html`) que se muestra cuando ocurre un error en la aplicación. Esta plantilla incluye un diseño visual atractivo y muestra dinámicamente el código HTTP del error utilizando una variable de plantilla (`status`).

También se ha implementado un test de integración que valida el uso de la página de error personalizada. Este test levanta el servidor completo y realiza una petición HTTP real a una ruta inexistente, comprobando que la respuesta contiene el contenido esperado de la plantilla de error (`¡Ups! Algo salió mal.` y `Código de error`).

## Technical Decisions
Se ha utilizado una plantilla HTML con soporte para variables (por ejemplo, Thymeleaf) para mostrar el código de error HTTP. El diseño utiliza estilos CSS para mejorar la experiencia del usuario y mantener la coherencia visual con el resto de la aplicación.

Para el test, se ha empleado `TestRestTemplate` y la anotación `@SpringBootTest(webEnvironment = RANDOM_PORT)` para asegurar que se levanta el servidor completo y se valida la respuesta HTML real.

## Learning Outcomes
Aprendí a crear páginas de error personalizadas en aplicaciones web, a utilizar variables de plantilla para mostrar información dinámica (como el código de error HTTP) y a mejorar la experiencia del usuario en situaciones de error.

Además, comprendí la diferencia entre tests con MockMvc y tests de integración completos, y cómo asegurar que la validación de la página de error se realiza en condiciones reales de ejecución.

## AI Disclosure
### AI Tools Used
- GitHub Copilot

### AI-Assisted Work
- La plantilla `error.html` fue generada con asistencia de IA, incluyendo el diseño visual y la integración de la variable para el código de error.
- El test de integración fue sugerido y estructurado con ayuda de IA, asegurando que se valida correctamente la respuesta HTML.
- El 90% del trabajo de la plantilla y el test fue asistido por IA, mientras que la integración y adaptación final fue realizada manualmente.
- Se revisó y adaptó el código generado para asegurar compatibilidad con el motor de plantillas y el entorno de test.

### Original Work
- La integración de la plantilla en el proyecto, la adaptación a las necesidades específicas y la ejecución de los tests se realizó manualmente.
- El proceso me permitió comprender mejor el manejo de errores, la personalización de vistas y la validación mediante tests de integración en aplicaciones web.
