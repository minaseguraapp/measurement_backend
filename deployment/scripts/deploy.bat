@echo off

echo Initializing params...

SET STACK_NAME=measurement-stack
SET PARAMS=--resolve-s3 --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND

echo The stack name to be created/updated is %STACK_NAME%...

cd %~dp0

cd ../..

echo Creating JAR file to upload...
call mvnw package

IF %ERRORLEVEL% NEQ 0 (
    echo Fallo en la construcción del proyecto Maven. Abortando despliegue.
    exit /b %ERRORLEVEL%
)
echo JAR File Created...

cd deployment/scripts

SET TEMPLATE_PATH=../template/template.yaml

echo The creation/update of the Stack %STACK_NAME% will start with the next params [%PARAMS%]
