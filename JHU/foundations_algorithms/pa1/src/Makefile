SHELL := /bin/bash

all: clean compile doc test run

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

test: compile
	java -ea TestRunner >& ../output/tree_testing.txt

run:

doc:
	javadoc *.java -d docs
