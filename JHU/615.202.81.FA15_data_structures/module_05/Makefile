all: compile doc test run

compile:
	javac *.java

test:
	java -ea TestStack
	java -ea TestEval

run:
	java PostfixEval PostfixMachineLangInput.txt

doc:
	javadoc *.java -d docs
