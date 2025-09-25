---
title: "Web Engineering 2025-2026"
subtitle: "Lab 2: Web Server"
format:
  html:
    toc: true
    toc-depth: 3
    number-sections: true
    code-fold: true
    code-tools: true
    code-overflow: wrap
    theme: cosmo
    css: |
      body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
      .quarto-title-block { border-bottom: 2px solid #2c3e50; padding-bottom: 1rem; }
      h1, h2, h3 { color: #2c3e50; }
      pre { background-color: #f8f9fa; border-left: 4px solid #007acc; }
      .callout { border-left: 4px solid #28a745; }
      /* Wrap long lines in code blocks */
      pre, code { white-space: pre-wrap; word-break: break-word; overflow-wrap: anywhere; }
  pdf:
    documentclass: article
    classoption: [11pt, a4paper]
    toc: true
    toc-depth: 3
    number-sections: true
    geometry: [margin=2.5cm, headheight=15pt]
    fontsize: 11pt
    linestretch: 1.15
    colorlinks: true
    breakurl: true
    urlcolor: blue
    linkcolor: blue
    citecolor: blue
    hyperrefoptions:
      - linktoc=all
      - bookmarksnumbered=true
      - bookmarksopen=true
    header-includes:
      - |
        \usepackage{helvet}
        \renewcommand{\familydefault}{\sfdefault}
        \usepackage{hyperref}
        \usepackage{fancyhdr}
        \pagestyle{fancy}
        \fancyhf{}
        \fancyhead[L]{Web Engineering 2025-2026}
        \fancyhead[R]{Lab 2: Web Server}
        \fancyfoot[C]{\thepage}
        \renewcommand{\headrulewidth}{0.4pt}
        \usepackage{microtype}
        \usepackage{booktabs}
        \usepackage{array}
        \usepackage{longtable}
        \usepackage{xcolor}
        \definecolor{sectioncolor}{RGB}{44,62,80}
        \usepackage{sectsty}
        \allsectionsfont{\color{sectioncolor}}
        % Wrap long lines in code blocks for PDF output
        \usepackage{fvextra}
        \fvset{breaklines=true, breakanywhere=true}
        \DefineVerbatimEnvironment{Highlighting}{Verbatim}{breaklines,breakanywhere,commandchars=\\\{\}}
lang: en
---

Welcome to the second lab assignment of the 2025--2026 course! This guide will help you complete the assignment efficiently. Although this guide is command-line oriented, you are welcome to use IDEs like **VS Code**, **IntelliJ IDEA**, or **Eclipse**—all of which fully support the tools we'll be using. Ensure you have at least **Java 21** installed on your system before getting started.

**Estimated time**: 2 hours.

## System requirements

For this assignment, we'll be using the following technologies:


1. **Java Version**: The project uses **Java 21 LTS** for optimal performance and modern language features.
2. **Programming Language**: We're using [Kotlin 2.2.10](https://kotlinlang.org/), a versatile, open-source language that's popular for Android development and server-side applications.
3. **Framework**: The application is based on [Spring Boot 3.5.3](https://docs.spring.io/spring-boot/). It requires Java 17 and is compatible with Java up to version 21.
4. **Build System**: [Gradle 9.0.0](https://gradle.org/), which automates building, testing, and packaging.
5. **Template Engine**: **Thymeleaf 3** (via `spring-boot-starter-thymeleaf`) to render the custom error page.
6. **Web Stack**: **Spring Web MVC** (via `spring-boot-starter-web`) with the embedded **Tomcat** server.
7. **JSON**: **Jackson** with **jackson-module-kotlin** to serialize Kotlin data classes in the `/time` DTO response.
8. **TLS + HTTP/2**: **Java 21 TLS stack** with Spring Boot’s HTTP/2 support on Tomcat; uses a **PKCS#12** keystore.
9. **CLI Tools**: **OpenSSL** (generate the self-signed certificate) and **curl** (manual verification commands in this guide).
10. **Code Quality**: **Ktlint** Gradle plugin for formatting and checks.


## Getting started
### Clone the repository

```bash
git clone https://github.com/UNIZAR-30246-WebEngineering/lab-2-web-server.git
cd lab-2-web-server-<your-github-user>
```

### Run locally
```bash
./gradlew bootRun
# Before SSL: http://localhost:8080
# After SSL task: https://127.0.0.1:8443
```

 

## Primary Tasks

Your assignment comprises the following tasks:

1. **Customize the Whitelabel Error Page**
2. **Add a New Endpoint**
3. **Enable HTTP/2 and SSL Support**

### 1. Customize the Whitelabel Error Page

When your Spring Boot application encounters an error, it displays a default "Whitelabel Error Page." Your task is to customize this page to enhance user experience.

**Steps:**

1. **Create a Custom Error Page**

   Design an `error.html` page with custom content. This page will be displayed whenever an error occurs.

2. **Save the Error Page**

   Place the `error.html` file in the `src/main/resources/templates` directory. Spring Boot will automatically use this template for error handling.

3. **Add a test that validates the use of the custom error page**

### 2. Add a New Endpoint

You'll create a new REST endpoint that returns the current server time in a structured format.

**Steps:**

1. **Create `TimeComponent.kt`**

   In your project's main package (e.g., `es.unizar.webeng.lab2`), create a new Kotlin file named `TimeComponent.kt`.

2. **Define a Data Transfer Object (DTO)**

   ```kotlin
   import java.time.LocalDateTime

   data class TimeDTO(val time: LocalDateTime)
   ```

   A DTO is used to transfer data between software application subsystems.

3. **Create a Time Provider Interface**

   ```kotlin
   interface TimeProvider {
       fun now(): LocalDateTime
   }
   ```

   This interface allows for different implementations of time retrieval, making your code more flexible and testable.

4. **Implement the Time Provider Service**

   ```kotlin
   import org.springframework.stereotype.Service

   @Service
   class TimeService : TimeProvider {
       override fun now(): LocalDateTime = LocalDateTime.now()
   }
   ```

   The `@Service` annotation indicates that this class is a service component in Spring's component scanning.

5. **Create an Extension Function**

   ```kotlin
   fun LocalDateTime.toDTO(): TimeDTO = TimeDTO(time = this)
   ```

   Extension functions in Kotlin allow you to extend a class with new functionality without inheriting from it.

6. **Create a REST Controller**

   ```kotlin
   import org.springframework.web.bind.annotation.GetMapping
   import org.springframework.web.bind.annotation.RestController

   @RestController
   class TimeController(private val service: TimeProvider) {
       @GetMapping("/time")
       fun time(): TimeDTO = service.now().toDTO()
   }
   ```

   Dependency injection is used here to inject the `TimeProvider` service into the controller.

 7. **Add a test that validates the use of the time endpoint**

Note: Jackson is auto-configured by `spring-boot-starter-web`; with `jackson-module-kotlin` added, Kotlin data classes like `TimeDTO` are serialized/deserialized correctly (nullability, default params, data classes).

### 3. Enable HTTP/2 and SSL Support

You'll enable HTTP/2 and configure SSL using a self-signed certificate.

**Steps:**

1. **Generate a Self-Signed Certificate**

   Run the following command to generate a self-signed certificate and private key:

   ```bash
   openssl req -x509 -out localhost.crt -keyout localhost.key \
   -newkey rsa:2048 -nodes -sha256 \
   -subj '/CN=localhost' -extensions EXT -config <( \
   printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n" \
   printf "[EXT]\nsubjectAltName=DNS:localhost\n" \
   printf "keyUsage=digitalSignature\nextendedKeyUsage=serverAuth")
   ```

   This command generates two files: `localhost.crt` (the public certificate) and `localhost.key` (the private key).

   *Windows Users:* If you're not using WSL, you can install [OpenSSL for Windows](https://wiki.openssl.org/index.php/Binaries).

2. **Create a PKCS12 Keystore**

   Use OpenSSL to generate a PKCS12 keystore containing both the certificate and private key. Remember the export password you set (e.g., `secret`):

   ```bash
   openssl pkcs12 -export -in localhost.crt -inkey localhost.key -name localhost -out localhost.p12
   ```

   Move the `localhost.p12` file to `src/main/resources`.

   A PKCS12 keystore is a binary format for storing cryptographic keys and certificates.

3. **Configure Spring Boot for SSL and HTTP/2**

   Create an `application.yml` file in `src/main/resources` with the following content:

   ```yaml
   server:
     port: 8443
     ssl:
       enabled: true
       key-store: classpath:localhost.p12
       key-store-password: "secret"  # Use the same export password you set when creating the PKCS#12 file
       key-store-type: PKCS12
     http2:
       enabled: true
   ```

   This configuration enables SSL using your keystore and activates HTTP/2 support on port 8443.

## Manual Verification

Note: If `curl` does not negotiate HTTP/2, ensure it was built with HTTP/2 support (nghttp2). Check with `curl -V` (look for `nghttp2`). You can also force HTTP/2 with `curl --http2 -i -k https://127.0.0.1:8443`.

1. **Start the Application**

   Run your application using:

   ```bash
   ./gradlew bootRun
   ```

2. **Test the Custom Error Page**

   Execute the following command:

   ```bash
   curl -k -H "Accept: text/html,*/*;q=0.9" -i https://127.0.0.1:8443/
   ```

    - `-k`: Ignores SSL certificate validation.
    - `-H "Accept: text/html,*/*;q=0.9"`: Sets the `Accept` header to request HTML content.
    - `-i`: Includes response headers in the output.

   *Expected Outcome:*
 
    - The response should indicate `HTTP/2` protocol in the status line.
    - You should receive a `404` response with your custom error page content.

3. **Test the time endpoint**

   Run:

   ```bash
   curl -k -i https://127.0.0.1:8443/time
   ```

   *Expected Outcome:*

    - The response should indicate `HTTP/2` protocol in the status line.
    - You should receive a `200` status with a JSON body:

      ```json
      {
        "time": "2024-10-01T16:33:58.91803"
      }
      ```

## Code Quality Tools: Ktlint

The [Ktlint](https://ktlint.github.io/) plugin is enabled.
It automatically formats your code according to the [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html#source-code-organization). 
If Ktlint modifies your source code during formatting, the build will fail.

## Insights

### Understanding SSL and HTTP/2

- **SSL (Secure Sockets Layer):** Encrypts data transmitted between the client and server, ensuring privacy and data integrity.
- **HTTP/2:** Enhances web performance through multiplexing, header compression, and server push capabilities.

*Tip:* Enabling HTTP/2 over SSL can significantly improve your application's performance and security.

### Why Use Interfaces and Dependency Injection

- **Interfaces:** Promote loose coupling by allowing different implementations to be substituted without changing consuming code.
- **Dependency Injection:** Enhances testability and maintainability by managing dependencies externally.

*Example:* Injecting `TimeProvider` allows you to mock time in tests, making your unit tests more reliable.

### Extension Functions in Kotlin

- **Extension Functions:** Add new functions to existing classes without inheritance, improving code readability and maintainability.

*Usage:* The `toDTO()` function extends `LocalDateTime`, allowing for a clean transformation to `TimeDTO`.

### Testing Tips

- **Use Tools Like Postman or HTTPie:** For more interactive testing of your endpoints, especially when dealing with headers and SSL.
- **Check Logs:** If something doesn't work as expected, server logs can provide valuable insights.

### Code Quality Tools

- **Ktlint:** Before building, run `./gradlew ktlintFormat` to format your code and prevent build failures.

## Submission and Evaluation

### Submission Deadline

The deadline for this assignment is **October 17th**. If you fail to submit by then, you will incur a 20% penalty on your individual project score for the _URLShortener_ project. This penalty only applies if you have not attempted to submit anything of value.

### Submission Requirements

You must submit your work in **two ways**:

1. **Moodle Submission**: Upload a zip file of your complete project to Moodle
2. **GitHub Repository**: Push your changes to your GitHub repository for a potential evaluation by the teacher during the Lab 3 session

#### Moodle Zip File Submission

Create a zip file containing your complete project and upload it to Moodle. The zip file should include:

- All source code files
- Documentation files (README.md, REPORT.md)
- Test files
- Configuration files
- Any additional files you've created

#### REPORT.md File Requirements

You must create a `REPORT.md` file in your project root that includes:

1. **Description of Changes**: Detailed explanation of all modifications and enhancements you made
2. **AI Disclosure**: Complete disclosure of any AI tools or assistance used during development, including:
    - Specific AI tools used (ChatGPT, GitHub Copilot, etc.)
    - What code or documentation was generated with AI assistance
    - How much of your work was AI-assisted vs. original
    - Any AI-generated code that was modified or adapted
3. **Learning Outcomes**: What you learned from completing this assignment
4. **Technical Decisions**: Explanation of technical choices and their rationale

**Sample REPORT.md Structure**:
```markdown
# Lab 1 Git Race -- Project Report

## Description of Changes
[Detailed description of all changes made]

## Technical Decisions
[Explanation of technical choices made]

## Learning Outcomes
[What you learned from this assignment]

## AI Disclosure
### AI Tools Used
- [List specific AI tools used]

### AI-Assisted Work
- [Describe what was generated with AI assistance]
- [Percentage of AI-assisted vs. original work]
- [Any modifications made to AI-generated code]

### Original Work

- [Describe work done without AI assistance]
- [Your understanding and learning process]
```

Good luck with your assignment!