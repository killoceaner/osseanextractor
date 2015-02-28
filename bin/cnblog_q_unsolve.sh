#!/bin/bash

SITE='cnblog_q_unsolve'

find ./target/classes -name "*.xml"|xargs rm -f


tmp='./bin/resources'
tmp='./target/classes':$tmp
tmp='./target/osseanextractor-0.0.1-jar-with-dependencies-without-resources/*':$tmp

CLASSPATH=$tmp:$CLASSPATH

JAVA_OPTS="-Xms256m -Xmx256m -Xmn128m"  

echo $CLASSPATH

java $JAVA_OPTS -DlogFilePath=${SITE} -classpath $CLASSPATH net.trustie.extractor.CNblogQUnSolve_Extractor >>log/${SITE}.log 2>&1 &