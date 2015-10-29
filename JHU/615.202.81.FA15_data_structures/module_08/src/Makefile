all: clean compile doc test run

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

test: compile
	java -ea TestRunner &> ../output/TestOut.txt

run:
	java Driver ../inputs/lab2-input.txt

doc:
	javadoc *.java -d docs
