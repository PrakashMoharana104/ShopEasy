@echo off
REM ShopEasy Java Development Environment Setup Script for Windows
REM This script compiles and runs the ShopEasy backend

echo ========================================
echo ShopEasy E-Commerce Backend
echo Compilation and Execution Script
echo ========================================
echo.

REM Set directories
set JAVA_SRC_DIR=src
set JAVA_BIN_DIR=bin
set JAVA_MAIN_CLASS=com.shopeasy.ShopEasyServer

REM Check if bin directory exists, if not create it
if not exist "%JAVA_BIN_DIR%" (
    mkdir "%JAVA_BIN_DIR%"
    echo Created bin directory
)

REM Compile all Java files
echo.
echo Compiling Java files...
for /r "%JAVA_SRC_DIR%" %%f in (*.java) do (
    javac -d "%JAVA_BIN_DIR%" -sourcepath "%JAVA_SRC_DIR%" "%%f"
    if errorlevel 1 goto compile_error
)

REM Check if compilation was successful
if errorlevel 1 (
    :compile_error
    echo.
    echo ERROR: Compilation failed!
    echo Make sure you have Java JDK installed and JAVA_HOME is set correctly
    echo.
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Ask user if they want to run
set /p RUN_APP="Do you want to run the API server? (y/n): "
if /i "%RUN_APP%"=="y" (
    echo.
    echo ========================================
    echo Running ShopEasy REST API Server...
    echo ========================================
    echo.
    java -cp "%JAVA_BIN_DIR%" "%JAVA_MAIN_CLASS%"
) else (
    echo To run the server later, use:
    echo java -cp bin com.shopeasy.ShopEasyServer
)

echo.
pause
