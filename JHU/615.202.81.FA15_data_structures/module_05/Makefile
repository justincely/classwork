all: compile test run doc

compile:
	javac *.java

test:
	java -ea TestStack
	java -ea TestEval

run:
	java PostfixEval PostfixMachineLangInput.txt

doc:
	javadoc *.java -d docs
