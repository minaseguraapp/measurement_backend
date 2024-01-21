@echo off

cd %~dp0
cd ../..

echo Initializing params...

SET STACK_NAME=measurement-stack
SET PARAMS=--resolve-s3 --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
SET TEMPLATE_PATH=deployment\template\template.yaml

echo The stack name to be created/updated is %STACK_NAME%...

echo Creating JAR file to upload...
call mvnw package

IF %ERRORLEVEL% NEQ 0 (
    echo The artifact creation has failed.
    exit /b %ERRORLEVEL%
)
echo JAR File Created...

echo Ruta actual: %CD%

SET FINAL_PARAMS=--template-file %TEMPLATE_PATH% --stack-name %STACK_NAME% %PARAMS%

echo The creation/update of the Stack %STACK_NAME% will start with the next params [%FINAL_PARAMS%]
sam deploy %FINAL_PARAMS%

IF %ERRORLEVEL% NEQ 0 (
    echo The stack deployment has failed.
    exit /b %ERRORLEVEL%
)

echo The creation/update of the Stack %STACK_NAME% has finished successfully
