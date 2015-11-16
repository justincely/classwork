all: clean compile doc unittest test lab

nounittest: clean compile_no_junit test lab

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

compile_no_junit:
	javac BadMatrix.java
	javac Driver.java
	javac Matrix.java

lab:


test:


unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
