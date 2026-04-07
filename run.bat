@echo off
echo Compiling Java files...
javac *.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Compilation successful. Running application...
java Main
pause
