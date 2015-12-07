all: clean compile doc unittest lab

nounittest: clean compile_no_junit lab

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

compile_no_junit:
	javac Driver.java
	javac Heap.java
	javac InsertionSort.java
	javac QuickSort.java

lab:
	

unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
