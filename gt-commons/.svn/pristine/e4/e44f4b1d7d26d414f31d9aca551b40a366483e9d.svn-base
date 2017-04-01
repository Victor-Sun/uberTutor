@echo off

echo ---------------SET ENV---------------
cd /d Z:\Bin
call env.bat

echo ---------------package gt-commons--------------- 
cd /d W:\Gnomon\gt-commons
call mvn clean package -U -Dmaven.test.skip=true
call mvn install:install-file -DgroupId=com.gnomon -DartifactId=gt-commons -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar -Dfile=W:\Gnomon\gt-commons\target\gt-commons-1.0.0-SNAPSHOT.jar

PAUSE

