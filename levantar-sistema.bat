@echo off
title Sistema Clinico - Puesta en Marcha

echo ==================================================
echo SISTEMA CLINICO - MICRO SERVICIOS
echo Puesta en marcha sin Docker
echo ==================================================
echo.

echo Verificando archivos .jar...
echo.

if not exist "eureka-server\target\eureka-server-*.jar" (
    echo ERROR: No se encontro el .jar de Eureka Server.
    pause
    exit
)

if not exist "api-gateway\target\api-gateway-*.jar" (
    echo ERROR: No se encontro el .jar de API Gateway.
    pause
    exit
)

echo Archivos .jar encontrados correctamente.
echo.

echo ==================================================
echo 1. Levantando Eureka Server...
echo ==================================================
start "Eureka Server" cmd /k "java -jar eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar"

echo Esperando que Eureka inicie...
timeout /t 20 /nobreak

echo.
echo ==================================================
echo 2. Levantando microservicios de negocio...
echo ==================================================

start "MS Usuarios" cmd /k "java -jar msusuarios\target\msusuarios-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Pacientes" cmd /k "java -jar mspacientes\target\mspacientes-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Medicos" cmd /k "java -jar msmedicos\target\msmedicos-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Farmacia" cmd /k "java -jar msfarmacia\target\msfarmacia-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Reservas" cmd /k "java -jar msreservas\target\msreservas-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Examenes" cmd /k "java -jar msexamenes\target\msexamenes-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Fichas Clinicas" cmd /k "java -jar msfichasclinicas\target\msfichasclinicas-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Recetas" cmd /k "java -jar ms-recetas\target\ms-recetas-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Notificaciones" cmd /k "java -jar ms-notificaciones\target\ms-notificaciones-0.0.1-SNAPSHOT.jar"
timeout /t 5 /nobreak

start "MS Pagos" cmd /k "java -jar ms-pagos\target\ms-pagos-0.0.1-SNAPSHOT.jar"

echo.
echo Esperando que los microservicios se registren en Eureka...
timeout /t 25 /nobreak

echo.
echo ==================================================
echo 3. Levantando API Gateway...
echo ==================================================
start "API Gateway" cmd /k "java -jar api-gateway\target\api-gateway-0.0.1-SNAPSHOT.jar"

echo.
echo ==================================================
echo SISTEMA EN PROCESO DE INICIO
echo ==================================================
echo.
echo Revisar Eureka:
echo http://localhost:8761
echo.
echo API Gateway:
echo http://localhost:8094
echo.
echo Swagger ejemplos:
echo http://localhost:8081/swagger-ui/index.html
echo http://localhost:8082/swagger-ui/index.html
echo http://localhost:8083/swagger-ui/index.html
echo.
echo IMPORTANTE:
echo Esperar 1 o 2 minutos antes de probar en Postman.
echo.
pause