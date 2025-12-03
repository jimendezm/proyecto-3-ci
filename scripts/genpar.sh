#!/usr/bin/env bash
cd "$(dirname "$0")"
java -jar "../lib/java-cup-11b.jar" \
     -parser parser \
     -symbols sym \
     -destdir "../build/" \
     -nosummary \
     "../src/Parser.cup"
