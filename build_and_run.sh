#!/bin/bash
sbt assembly &&
java -jar -XX:ErrorFile=jvm.error.log "${BASH_SOURCE%/*}/assembly/print_table.jar" "$@"
