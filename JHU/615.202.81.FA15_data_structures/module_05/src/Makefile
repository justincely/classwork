all: clean compile doc test run

clean:
	rm -f *.class
	rm -f ../output/*.txt

compile:
	javac *.java

test: compile
	java -ea TestStack >& ../output/stack_testing.txt
	java -ea TestEval >& ../output/evaluation_testing.txt

run:
	java PostfixEval ../input/PostfixMachineLangInput.txt >& ../output/PostfixMachineLangOutput.txt
	java PostfixEval ../input/AdditionalValidInput.txt >& ../output/AdditionalValidOutput.txt

doc:
	javadoc *.java -d docs
