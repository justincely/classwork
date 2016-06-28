SHELL := /bin/bash

all: clean compile doc test run

notest: clean compile doc run

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac AbstractTree.java
	javac BinaryTree.java
	javac Driver.java
	javac Tree.java

test:
	javac *.java
	java -ea TestRunner >& ../output/tree_testing.txt

run:
	java Driver ../input/sample_input.txt
	java Driver ../input/sample_string_input.txt

doc:
	javadoc *.java -d docs
