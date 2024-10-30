# Event Management Challenge

## Descripción

Este proyecto es un microservicio que maneja información relacionada con eventos y publica evento para las acciones realizadas en base de datos. Utiliza **Spring Boot** para el backend y una base de datos **PostgreSQL** para almacenar la información.

## Prerrequisitos

- **Java 17** o superior.
- **Gradle**
- **Docker** y **Docker Compose** para ejecutar la base de datos en contenedores.
- **AWS** configurado con un usuario que tenga permisos para acceder al recurso de Secret Manager
- **IntelliJ IDEA** (u otro IDE compatible con proyectos de Java).

## Configuración del Proyecto

### 1. Clonar el repositorio

Clona el repositorio desde GitHub y accede al directorio del proyecto:

```bash
git clone https://github.com/BACKEND-JJOS/event-management-challenge.git
cd event-management-challenge
git checkout develop
git pull origin develop
```

### 2. Configuración de AWS Secrets Manager

#### Requisitos Previos

Asegúrate de tener lo siguiente:

1. **Cuenta de AWS**: Debes tener una cuenta de AWS activa.
2. **AWS CLI**: Instala y configura la AWS Command Line Interface (CLI) y configura con un Usuario que tenga permisos para Secret Manager.

#### Nota: Recuerda Configurar AWS Secrets Manager

#### Nota: Recuerda Crear un Secreto en AWS Secrets Manager

1. Inicia sesión en la [Consola de AWS](https://aws.amazon.com/).
2. Navega a **Secrets Manager**.
3. Haz clic en **Store a new secret**.
4. Selecciona **Other type of secret**.
5. Introduce tus credenciales de RabbitMQ en formato JSON, como el siguiente:
      

    secret-name
      rabbitmq-secret-name
    data
         {
            "host": "localhost",
            "port": "5672",
            "username": "guest",
            "password": "guest",
            "virtual_host": "/"
         }

### 3. Configuración de la base de datos y broker de mensajaría con Docker Compose

Este proyecto utiliza una base de datos PostgreSQL y RabbitMq que puedes configurar rápidamente utilizando Docker. Para levantar la base de datos, simplemente ejecuta el siguiente comando:

#### Nota: Recuerde tener actualizado docker

```bash
docker-compose up -d
```

Esto levantará un contenedor de 

PostgreSQL
- Usuario: postgres
- Contraseña: postgres
- Puerto local: 5432

RabbitMq(Los secrets manger  deben estar configurado previamente)
- host: localhost
- port: 5672
- username: guest
- password: guest
- virtual-host: /


El contenedor almacenará los datos de forma persistente en un volumen de Docker llamado postgres_data.

### 4. Ejecutar el proyecto en IntelliJ

1. Abre IntelliJ IDEA y selecciona File > Open para cargar el proyecto.
2. Asegúrate de que todas las dependencias estén correctamente instaladas .
3. Ejecuta la clase principal del proyecto MainApplication.

Una vez ejecute el proyecto puede hacer uso de los endpoint, recuerde que si desea ver la documentación swagger puede entra a

    http://localhost:8081/iasapi/doc/swagger-ui/webjars/swagger-ui/index.html#/

Usuario de prueba para generar el token

    curl - POST 'http://localhost:8081/iasapi/auth/login'

    body 
    {
        "userName":"assistant",
        "password":"assistant"
    }