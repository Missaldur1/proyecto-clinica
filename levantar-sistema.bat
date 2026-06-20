@echo off
setlocal enabledelayedexpansion
cd /d "%~dp0"
title Sistema Clinico - Puesta en Marcha

echo ==================================================
echo SISTEMA CLINICO - MICRO SERVICIOS
echo Puesta en marcha sin Docker
echo ==================================================
echo.
echo Ruta actual:
cd
echo.

echo Verificando Java...
java -version
if errorlevel 1 (
    echo.
    echo ERROR: Java no esta disponible en el PATH.
    echo Instala Java 21 o revisa la variable PATH.
    pause
    exit /b 1
)

echo.
echo Buscando archivos JAR generados...
echo.

set "ROOT=%cd%\"

call :buscarJar "eureka-server" EUREKA_JAR || goto :error
call :buscarJar "msusuarios" USUARIOS_JAR || goto :error
call :buscarJar "mspacientes" PACIENTES_JAR || goto :error
call :buscarJar "msmedicos" MEDICOS_JAR || goto :error
call :buscarJar "msfarmacia" FARMACIA_JAR || goto :error
call :buscarJar "msreservas" RESERVAS_JAR || goto :error
call :buscarJar "msexamenes" EXAMENES_JAR || goto :error
call :buscarJar "msfichasclinicas" FICHAS_JAR || goto :error
call :buscarJar "ms-recetas" RECETAS_JAR || goto :error
call :buscarJar "ms-notificaciones" NOTIFICACIONES_JAR || goto :error
call :buscarJar "ms-pagos" PAGOS_JAR || goto :error
call :buscarJar "api-gateway" GATEWAY_JAR || goto :error

echo.
echo Todos los archivos JAR fueron encontrados correctamente.
echo.

echo ==================================================
echo 1. Levantando Eureka Server...
echo ==================================================
start "Eureka Server" cmd /k java -jar "!EUREKA_JAR!"

echo Esperando que Eureka inicie...
timeout /t 20 /nobreak

echo.
echo ==================================================
echo 2. Levantando microservicios de negocio...
echo ==================================================

start "MS Usuarios" cmd /k java -jar "!USUARIOS_JAR!"
timeout /t 5 /nobreak

start "MS Pacientes" cmd /k java -jar "!PACIENTES_JAR!"
timeout /t 5 /nobreak

start "MS Medicos" cmd /k java -jar "!MEDICOS_JAR!"
timeout /t 5 /nobreak

start "MS Farmacia" cmd /k java -jar "!FARMACIA_JAR!"
timeout /t 5 /nobreak

start "MS Reservas" cmd /k java -jar "!RESERVAS_JAR!"
timeout /t 5 /nobreak

start "MS Examenes" cmd /k java -jar "!EXAMENES_JAR!"
timeout /t 5 /nobreak

start "MS Fichas Clinicas" cmd /k java -jar "!FICHAS_JAR!"
timeout /t 5 /nobreak

start "MS Recetas" cmd /k java -jar "!RECETAS_JAR!"
timeout /t 5 /nobreak

start "MS Notificaciones" cmd /k java -jar "!NOTIFICACIONES_JAR!"
timeout /t 5 /nobreak

start "MS Pagos" cmd /k java -jar "!PAGOS_JAR!"

echo.
echo Esperando que los microservicios se registren en Eureka...
timeout /t 25 /nobreak

echo.
echo ==================================================
echo 3. Levantando API Gateway...
echo ==================================================
start "API Gateway" cmd /k java -jar "!GATEWAY_JAR!"

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
echo Esperar 1 o 2 minutos antes de probar en Postman.
echo.
pause
exit /b 0

:buscarJar
set "%~2="
for %%F in ("%ROOT%%~1\target\*.jar") do (
    if exist "%%~fF" (
        set "%~2=%%~fF"
        echo %~1: %%~nxF
        exit /b 0
    )
)

echo ERROR: No se encontro archivo .jar en:
echo %ROOT%%~1\target\
echo.
echo Ejecuta primero:
echo mvn clean package
echo.
exit /b 1

:error
echo.
echo No se pudo continuar porque falta uno o mas archivos .jar.
pause
exit /b 1