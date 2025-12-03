#!/usr/bin/env bash
cd "$(dirname "$0")"
echo "Compiling..." \
&& javac -cp ".:../lib/java-cup-11b-runtime.jar" \
         -d "../build/" \
         "../build/Lexer.java" \
         "../build/parser.java" \
         "../build/sym.java" \
         "../src/Main.java" \
         "../src/Tac.java" \
         "../src/Res.java" \
&& echo "Compilation done. Executing..." \
&& java -cp "../build/:../lib/java-cup-11b-runtime.jar" Main "$1" \
&& echo "Execution done."
