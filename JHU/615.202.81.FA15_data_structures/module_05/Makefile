all: clean compile doc test run

clean:
	rm -f *.class

compile:
	javac *.java

test: compile
	java -ea TestStack
	java -ea TestEval

run:
	java PostfixEval PostfixMachineLangInput.txt

doc:
	javadoc *.java -d docs
