SHELL := /bin/bash

all: clean compile doc test run

notest: clean compile doc run

clean:
	rm -f *.class

compile:
	javac AbstractTree.java
	javac BinaryTree.java
	javac Driver.java
	javac Tree.java

test:
	javac *.java
	java -ea TestRunner >& ../output/tree_testing.txt

run:
	java Driver ../input/sample_input.txt >> ../output/sample_output.txt
	java Driver ../input/sample_string_input.txt >> ../output/sample_string_output.txt

doc:
	javadoc *.java -d docs
