all: clean compile doc test

notest: clean compile doc

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

test: compile
	java -ea Driver ../inputs/lab2-input.txt &> ../output/labOutput.txt
	java -ea Driver ../inputs/blank_input.txt &> ../output/blankOutput.txt
	java -ea Driver ../inputs/invalid_input.txt &> ../output/invalidOutput.txt

unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
