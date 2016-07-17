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
	java -ea TestRunner >& ../output/tree_testing.txt

run:
	java Driver ../input/sample_input.txt >> ../output/sample_output.txt
	java Driver ../input/sample_string_input.txt >> ../output/sample_string_output.txt

doc:
	javadoc *.java -d docs
