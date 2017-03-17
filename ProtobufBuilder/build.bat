@echo off

::Э���ļ�·��, ���Ҫ����\������
set SOURCE_FOLDER=.\protos\

::C#������·��
set CS_COMPILER_PATH=.\tools\protoc-3.2.0-win32\bin\protoc.exe
::C#�ļ�����·��, ���Ҫ����\������
set CS_TARGET_PATH=.\net\

::Java������·��
set JAVA_COMPILER_PATH=.\tools\protoc-3.2.0-win32\bin\protoc.exe
::Java�ļ�����·��, ���Ҫ����\������
set JAVA_TARGET_PATH=.\java\
::ɾ��֮ǰ�������ļ�
del %CS_TARGET_PATH%\*.* /f /s /q
del %JAVA_TARGET_PATH%\*.* /f /s /q

::���������ļ�
for /f "delims=" %%i in ('dir /b "%SOURCE_FOLDER%\*.proto"') do (
    
    echo ��ʼ����proto�ļ�...
    ::���� C# ����
    echo %CS_COMPILER_PATH% --csharp_out=%CS_TARGET_PATH% --csharp_opt=file_extension=.cs %SOURCE_FOLDER%\%%i
    %CS_COMPILER_PATH% --csharp_out=%CS_TARGET_PATH% --csharp_opt=file_extension=.cs %SOURCE_FOLDER%\%%i
    
    ::���� Java ����
    echo %CS_COMPILER_PATH% --csharp_out=%CS_TARGET_PATH% --csharp_opt=file_extension=.cs %SOURCE_FOLDER%\%%i
    %JAVA_COMPILER_PATH% --java_out=%JAVA_TARGET_PATH% %SOURCE_FOLDER%\%%i
    
)

echo Э��������ϡ�

pause