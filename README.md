![logo_ironhack_blue 7](https://user-images.githubusercontent.com/23629340/40541063-a07a0a8a-601a-11e8-91b5-2f13e4e6b441.png)

# LAB | Fundamentos de SpringBoot

## **Instrucciones**

1. Haz un **fork** de este repositorio.  
2. Clona tu **fork** en tu máquina local.  
3. Resuelve los desafíos.  

## Entregables  

- Una vez completado, agrega tu solución a **git**.  
- Luego, haz **commit** en git y **push** a tu repositorio en GitHub.  
- Crea un **pull request** y pega el enlace del pull request en el campo de entrega en el **Portal del Estudiante**.

## Tareas

1. **Crea una aplicación Spring Boot** utilizando **Spring Initializr** con las siguientes dependencias:
   - **Spring Web**
   - **Spring Boot DevTools**

2. **Crea una clase `GreetingController`** que contenga los siguientes endpoints:
   - **`/hello`** → Retorna `"Hello World!"`
   - **`/hello/{name}`** → Retorna `"Hello {name}!"` donde `{name}` es una variable de ruta.
   - **`/add/{num1}/{num2}`** → Retorna la **suma** de `num1` y `num2`.
   - **`/multiply/{num1}/{num2}`** → Retorna el **producto** de `num1` y `num2`.

3. **Crea una clase `WeatherService`** con los siguientes métodos:
   - **Obtener la temperatura actual** → Simulada con un número aleatorio entre **-10 y 40**.
   - **Obtener la condición climática** → Devuelve aleatoriamente uno de los siguientes valores: `"Sunny"`, `"Rainy"`, `"Cloudy"`, `"Windy"`.
   - **Obtener la velocidad del viento** → Simulada con un número aleatorio entre **0 y 100**.

4. **Crea una clase `WeatherController`** que utilice **inyección de dependencias por constructor** para recibir el `WeatherService` y exponga los siguientes endpoints:
   - **`/weather/temperature`** → Retorna la temperatura actual.
   - **`/weather/condition`** → Retorna la condición climática actual.
   - **`/weather/wind`** → Retorna la velocidad actual del viento.
   - **`/weather/all`** → Retorna **toda la información meteorológica** en una sola respuesta.

5. **Crea una clase `TimeService`** con los siguientes métodos:
   - **Obtener la hora actual**.
   - **Obtener la fecha actual**.
   - **Obtener el día actual de la semana**.

6. **Crea una clase `TimeController`** que utilice **inyección de dependencias por constructor** para recibir el `TimeService` y exponga los siguientes endpoints:
   - **`/time`** → Retorna la **hora actual**.
   - **`/date`** → Retorna la **fecha actual**.
   - **`/day`** → Retorna el **día actual de la semana**.
   - **`/all`** → Retorna **toda la información temporal** en una sola respuesta.

### **Recuerda:**

- Usa una **estructura de paquetes adecuada**.  
- **Usa inyección de dependencias por constructor** en lugar de `@Autowired`.  
- **Prueba todos los endpoints** en tu navegador o en **Postman**.  
- **Incluye manejo de errores adecuado**.  
- Usa **nombres de variables y métodos significativos**.

---

## Solución

Aplicación **Spring Boot 4.0.8** (Java 25) generada con **Spring Initializr** con las
dependencias **Spring Web** y **Spring Boot DevTools**.

### Cómo ejecutarla

```bash
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`.

Para compilar y ejecutar los tests:

```bash
mvn clean package
```

### Endpoints

| Método | Ruta | Respuesta | Ejemplo |
| --- | --- | --- | --- |
| GET | `/hello` | `text/plain` | `Hello World!` |
| GET | `/hello/{name}` | `text/plain` | `/hello/Leonardo` → `Hello Leonardo!` |
| GET | `/add/{num1}/{num2}` | `text/plain` | `/add/7/5` → `12` |
| GET | `/multiply/{num1}/{num2}` | `text/plain` | `/multiply/7/5` → `35` |
| GET | `/weather/temperature` | `text/plain` | `12 C` (aleatoria entre -10 y 40) |
| GET | `/weather/condition` | `text/plain` | `Sunny` \| `Rainy` \| `Cloudy` \| `Windy` |
| GET | `/weather/wind` | `text/plain` | `37 km/h` (aleatoria entre 0 y 100) |
| GET | `/weather/all` | `application/json` | `{"temperatureCelsius":12,"condition":"Sunny","windSpeedKmh":37}` |
| GET | `/time` | `text/plain` | `21:28:51` |
| GET | `/date` | `text/plain` | `2026-10-20` |
| GET | `/day` | `text/plain` | `Tuesday` |
| GET | `/all` | `application/json` | `{"time":"21:28:52","date":"2026-10-20","dayOfWeek":"Tuesday"}` |

### Estructura de paquetes

```
com.ironhack.springbootfundamentals
├── SpringbootFundamentalsApplication
├── controller   → GreetingController, WeatherController, TimeController
├── service      → WeatherService, TimeService
├── dto          → WeatherReport, TimeInformation, ApiError
└── exception    → GlobalExceptionHandler, InvalidRequestException
```

Los controladores reciben sus servicios mediante **inyección por constructor**
(sin `@Autowired` en campos).

### Manejo de errores

`GlobalExceptionHandler` es un `@RestControllerAdvice` que convierte cualquier
error en un JSON con `timestamp`, `status`, `error`, `message` y `path`:

| Caso | Estado | Ejemplo de respuesta |
| --- | --- | --- |
| Operando no numérico (`/add/abc/5`) | `400` | `"'abc' is not a valid value for 'num1': a whole number is required."` |
| Nombre vacío o en blanco (`/hello/%20`) | `400` | `"The name must not be blank."` |
| Desbordamiento numérico | `400` | `"The operands are too large: the result does not fit in a 64-bit integer."` |
| Ruta inexistente (`/nope`) | `404` | `"No endpoint is mapped to '/nope'."` |
| Cualquier otro error | `500` | `"An unexpected error occurred."` |