all: clean compile doc unittest test lab

nounittest: clean compile doc test lab

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

lab:
	java -ea Driver ../inputs/lab2-input.txt &> ../output/labOutput.txt

test: compile
	java -ea Driver ../inputs/blank_input.txt &> ../output/blankOutput.txt
	java -ea Driver ../inputs/invalid_input.txt &> ../output/invalidOutput.txt
	java -ea Driver ../inputs/random_input.txt &> ../output/randomOutput.txt

unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
