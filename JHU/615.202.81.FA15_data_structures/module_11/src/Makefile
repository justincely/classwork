all: clean compile doc unittest lab

nounittest: clean compile_no_junit lab

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

compile_no_junit:
	javac BadEncoding.java
	javac EncodingError.java
	javac Tree.java
	javac MinQueue.java
	javac QueueNode.java
	javac Driver.java
	javac HuffmanTranslator.java

lab:
	java Driver ../input/LabInput.txt > ../output/LabOutput.txt
	java Driver ../input/AdditionalInput.txt > ../output/AdditionalOutput.txt

unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
