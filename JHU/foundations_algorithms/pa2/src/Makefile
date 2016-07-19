SHELL := /bin/bash

all: clean compile doc test run

notest: clean compile doc run

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac MultiStack.java

test:
	javac *.java
	java -ea TestRunner >& ../output/testing_output.txt

run:
	java Driver >> ../output/problem_output.txt

doc:
	javadoc *.java -d docs
