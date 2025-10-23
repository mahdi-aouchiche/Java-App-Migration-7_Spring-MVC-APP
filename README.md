# Java App Migration: Part 7 - Spring MVC APP

Author: Mahdi Aouchiche (<https://github.com/mahdi-aouchiche/Java-App-Migration-7_Spring-MVC-APP>)

## Overview

This Java App Migration Part 7: 

Outlines the architectural shift and implementation steps taken to migrate the application from a traditional Java Servlet-based web application to a modern, structured Spring Model-View-Controller (MVC) application.

The Java Web Application is an example to manage employees in a company with different department.

Note: Login info to run the app: username = Admin, password = 123


### Architectural Overview: The Migration from Servlets to MVC

The core of this migration was replacing the tight coupling of Servlets and JSPs with the standardized, decoupled MVC pattern provided by the Spring Framework.

| Feature | Old Architecture (Servlets) | New Architecture (Spring MVC) |
|:---|:---|:---|
| Front Controller | Multiple Servlets (`@WebServlet` on each class) | Single `DispatcherServlet` (Registered via `WebApplicationInitializer.java`) |
| Request Handling | Manual `doGet()` and `doPost()` methods with explicit URL mapping. | `@Controller` classes and `@RequestMapping` annotations (e.g., `@GetMapping`, `@PostMapping)`. |
| Data Transfer | Data stored directly in the `HttpServletRequest` using `request.setAttribute()`. | Data stored in the Spring `Model` object (`model.addAttribute()`).
| View Logic | Heavy use of **Java Scriptlets** (`<% ... %>`) to access request attributes and embed application logic. | Use of **JSTL** and Spring Expression Language (**EL**) (`${...}`) to cleanly render data passed via the `Model`. |
| Configuration | Dependency injection (DI) often manual or through custom setup (e.g., `init` method). | **Spring IoC (Inversion of Control)** container handles all service/DAO dependencies using `@Autowired`. |

### Key Modification Steps

The migration was performed across three main areas: **Configuration**, **Java Controllers**, and **JSP Views**.

#### A. Core Configuration (The Front Controller)

**Dependency Update**: Updated `pom.xml` to include `spring-webmvc` and related dependencies (Spring ORM, Hibernate). We also ensured proper management of JAXB dependencies for compatibility.

**DispatcherServlet Registration**: The old `web.xml` (or `@WebServlet` annotations) was replaced by a Java Configuration class, `AppInitializer.java`, which extends `AbstractAnnotationConfigDispatcherServletInitializer`. This class programmatically registers the `DispatcherServlet` to handle all incoming requests (`/`).

**Spring MVC Configuration**: A new configuration class (`WebMvcConfig.java`) was introduced to configure MVC-specific components, such as the `InternalResourceViewResolver`, which tells Spring where to find the JSP files (e.g., `/WEB-INF/views/`).

#### B. Migration of Servlets to Controllers

Every former `HttpServlet` was refactored into a focused Spring `@Controller`:

**Servlet Class Removal**: The `extends HttpServlet` boilerplate and methods like `init()`, `doGet()`, and `doPost()` were removed.

**Annotation-Driven Mapping**: The class was annotated with `@`Controller`, and methods were mapped to URLs using `@GetMapping` and `@PostMapping`.

**Simplified Request Handling**: Manual calls to `request.getParameter()` were replaced by annotating method parameters with `@RequestParam` or using `@ModelAttribute` for form binding, allowing Spring to handle parameter conversion and validation automatically.

**Model Usage**: The method signature was updated to accept a `Model` object. All data intended for the view (such as `lists` of `employees`, `departments`, or `success/error` messages) was added using `model.addAttribute(key, value)`.

**View Return Value**: Controller methods now return a logical `String` view name (e.g., `"assign-existing-employee-to-department"`), which the `DispatcherServlet` resolves to the final JSP path.

#### C. Migration of JSP Views (Scriptlet Removal)

JSPs were cleaned to separate presentation logic from application logic:

**Scriptlet Removal**: All Java scriptlets (`<% ... %>`) were removed from the JSPs.

**JSTL Implementation**: The JSTL tag library was included (`<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`).

**Expression Language (EL)**: Data retrieval (e.g., accessing the list of employees) now uses EL notation, seamlessly accessing the `Model` attributes (e.g., `${employees}`).

 **Old**: `<% List<Employee> e = (List<Employee>) request.getAttribute("employees"); %>`

 **New**: `<c:forEach var="employee" items="${employees}">`

### Benefits of Spring MVC Architecture

Migrating to Spring MVC provides several significant advantages over the legacy Servlet structure:

#### 1. Separation of Concerns (SoC)

The MVC pattern cleanly separates responsibilities:

**Controller**: Handles requests and coordinates data transfer.

**Model**: Holds the application data.

**View**: Renders the user interface. This makes the application much easier to read, maintain, and debug.
 
![SPRING MVC](/src/main/webapp/images/spring-mvc.jpg)

#### 2. Simplified Unit Testing

Because the Controller methods are just POJOs (Plain Old Java Objects) that accept parameters and return a view name, they can be unit tested easily without needing to mock `HttpServletRequest` and `HttpServletResponse` objects.

#### 3. Reduced Boilerplate Code

Spring handles vast amounts of boilerplate:

**Front Controller**: The single `DispatcherServlet` replaces the need to manually configure and map every single URL endpoint.

**Request/Parameter Handling**: Automatic parameter binding using `@RequestParam` eliminates manual string parsing, type casting, and validation from the Servlet code.

#### 4. Robust Framework Features

The application gains access to all of Spring's powerful features instantly:

**Inversion of Control (IoC)**: Dependencies (like Services and DAOs) are managed by the Spring Container via `@Autowired`, promoting loose coupling.

**Interceptors**: Allows for centralized pre-processing and post-processing of requests (e.g., logging, security checks) without cluttering every controller.

**Validation**: Spring's integration with Hibernate Validator makes form data validation declarative and easy to implement.
