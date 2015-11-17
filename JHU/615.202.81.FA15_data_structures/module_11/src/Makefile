all: clean compile doc unittest test lab

nounittest: clean compile_no_junit test lab

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

compile_no_junit:
	javac Tree.java
	javac MinQueue.java
	javac QueueNode.java
	javac Driver.java
	javac HuffmanTranslator.java

lab:
	java Driver ../input/LabInput.txt > ../output/LabOutput.txt

test:


unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
