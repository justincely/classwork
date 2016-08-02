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
	java Driver -i ../input/plaintextinput.txt -o ../output/plaintext_compressed_ouput.txt -c --huffmanfiles ../input/qwerty.txt
	java Driver -i ../output/plaintext_compressed_ouput.txt -o ../output/plaintext_extracted_ouput.txt -x --huffmanfiles ../input/qwerty.txt
	java Driver -i ../input/plaintextinput.txt --huffmanfiles ../input/huffman*.txt --compress -e3 -o ../output/plaintext_huffman_compressed_shift3.txt
	java Driver -i ../output/plaintext_huffman_compressed_shift3.txt --huffmanfiles ../input/huffman*.txt --extract -d3 -o ../output/plaintext_huffman_extracted_shift3.txt

unittest: compile
	java -ea TestRunner &> ../output/unitTestOut.txt

doc:
	javadoc *.java -d docs
