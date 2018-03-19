#!/usr/bin/env bash

mvn -Dmaven.test.skip=true clean package
mv bootstrap/target/tracking-bootstrap-*.jar .
mv profiler/target/tracking-profiler-*.jar ./libs
mv bootstrap-core/target/tracking-bootstrap-core-*.jar ./libs

cd profiler
mvn dependency:copy-dependencies -DoutputDirectory=./target/libs
rm -rf ./target/libs/tracking*.jar
cd ..
mv ./profiler/target/libs/*.jar ./libs