SHELL := /bin/bash

all: clean compile doc unittest lab

nounittest: clean compile_no_junit lab

clean:
	rm -f *.class
	rm -f ../output/*.txt
	rm -f ../analysis/*_times.txt

compile:
	javac *.java

compile_no_junit:
	javac Driver.java
	javac Heap.java
	javac InsertionSort.java
	javac QuickSort.java

lab:
	echo "Running all insertion sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile insertion >> ../analysis/insertion_times.txt; done; }
	echo "Running all heap sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile heap >> ../analysis/heap_times.txt; done; }
	echo "Running all quick sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss90m Driver $$infile $$outfile quick; done; }
	echo "Running all quick sorts with a 50 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss90m Driver $$infile $$outfile quick 50; done; }
	echo "Running all quick sorts with a 100 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss90m Driver $$infile $$outfile quick 100; done; }


unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
