#!/bin/bash
# remove build and make new
rm -rf build
mkdir -p build/classes build/docs build/jar

# compiling sources in build/classes
javac -d build/classes src/main/java/ru/nsu/mzaugolnikov/task111/*.java

#docs
javadoc -d docs src/main/java/ru/nsu/mzaugolnikov/task111/*.java

# packing
echo "Main-Class: ru.nsu.mzaugolnikov.task111.task111" > build/manifest.txt
jar cfm build/jar/sort.jar build/manifest.txt -C build/classes .

# run
java -jar build/jar/sort.jar
