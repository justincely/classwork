SHELL := /bin/bash

all: clean compile doc unittest lab

nounittest: clean compile_no_junit lab

clean:
	rm -f *.class
	rm -f ../output/*.txt
	rm -f ../analysis/*_timing.txt

compile:
	javac *.java

compile_no_junit:
	javac Driver.java
	javac Heap.java
	javac InsertionSort.java
	javac QuickSort.java
	javac QuickSortMed.java

lab:
	echo "Running all insertion sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile insertion; done; }
	echo "Running all heap sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile heap; done; }
	echo "Running all recursive heap sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile heaprecursive; done; }
	echo "Running all quick sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick; done; }
	echo "Running all quick sorts with median partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quickmed; done; }
	echo "Running all quick sorts with a 50 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick 50; done; }
	echo "Running all quick sorts with a 100 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick 100; done; }

timing:
	echo "Running all insertion sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile insertion >> ../analysis/insertion_timing.txt; done; }
	echo "Running all heap sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile heap >> ../analysis/heap_timing.txt; done; }
	echo "Running all recursive heap sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java Driver $$infile $$outfile heaprecursive >> ../analysis/heaprecursive_timing.txt; done; }
	echo "Running all quick sorts";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick >> ../analysis/quick_timing.txt; done; }
	echo "Running all quick sorts with median partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quickmed >> ../analysis/quickmed_timing.txt; done; }
	echo "Running all quick sorts with a 50 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick 50 >> ../analysis/quick50_timing.txt; done; }
	echo "Running all quick sorts with a 100 size minimum partition";
	{ for infile in ../input/*.txt; do replace=output; outfile=$${infile/input/$$replace}; java -Xss200m Driver $$infile $$outfile quick 100 >> ../analysis/quick100_timing.txt; done; }


unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
