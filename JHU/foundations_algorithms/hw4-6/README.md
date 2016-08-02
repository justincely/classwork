# Summary
This package contains the solution to HW4 and HW5; compression and cryptography track.  
A Command Line Interface (CLI) has been chosen for the UI.  Specific usage options
and examples are given in the Usage section below.

Note that initial work on reading/writing as binary has been implemented
implemented, so sample input/output has also been provided to test this
functionality.  Input/ouput is supplied, and
the output can be re-generated using the Makefile.


### Directory Contents  
------------------
*  ./input/: sample input text files to be evaluated
*  ./output/: sample output text files from runs against the included input
*  ./src/: raw and compiled source java code, long with Makefile for testing, compilation, documentation
*  ./src/docs/: java autodoc output

### Compilation/Installation
------------------------
The src directory contains a Makefile to clean the directory, run the compile the code, run the tests, produce documentation, and finally pass the available input through the evaluator.

Note that the JUnit library is required to run the unittests.  If JUnit is not installed, "make nounittest" should be run to avoid that section of the code.

Makefile usage is outlined below.
```bash
make             # run the entire suite
make nounittest  # run the entire suite without the JUnit unittest framework
make compile     # compile the source
make unittest    # compile and tun the unit tests with the JUnit framework
make doc         # generate documentation
make lab         # run the code against the input files
```

### Usage
---------

All optional arguments are outlined below.  Note that some argument pairs are
mutually exclusive, such as --compress and --extract.  If any mutually exclusive
pair is issued, the program will end.  

```bash
$ java Driver -h
Usage:
--help, -h: Print this helpfile and exit.
--input, -i: Filename to read input text from.
--readbytes, -rb: Read input as bytes.
--output, -o: Filename to send output to.  If omitted, STDOUT will be used.
--writebytes, -wb: Write output as bytes.
--compress, -c: Flag to run compression algorithm.
--huffmanfiles: Files from which to build huffman tree.
--extract, -x: Flag to decompress the text.
--encrypt, -e: Flag to encrypt the text.
--decrypt, -d: Flag to decrypt the text.
```

An example of a valid command sequence is given below.  This would read from
the given file, compress the text via Huffman Encoding, and then write to the
specified output file.
```bash
java Driver --input sample_input.txt --output sample_output.txt --compress
java Driver --i sample_input.txt -o sample_output.txt -c
```

The encryption and decryption functions are implemented as a simple shift cypher.
To supply this argument with a shift value, simply supply it as part of the
flag.  e.g.
```bash
java Driver -i ../input/rawinput.txt -o ../output/ceaserout.txt -e3
java Driver -i ../input/rawinput.txt -o ../output/ceaserout.txt --decrypt3
java Driver -i ../input/rawinput.txt -o ../output/ceaserout.txt -e23
java Driver -i ../input/rawinput.txt -o ../output/ceaserout.txt --decrypt23
```

### Compression
---------------
Compression can be accomplished through Huffman encoding.  This encoding requires
a frequency table that must be created for each compression or extraction.  This
can be read from text files suppied through the --huffmanfiles argument.  

```bash
java Driver -i ../input/plaintextinput.txt --huffmanfiles ../input/huffman*.txt --compress -o ../output/plaintext_huffman_compressed.txt
java Driver -i ../output/plaintext_huffman_compressed.txt --huffmanfiles ../input/huffman*.txt --extract -o ../output/plaintext_huffman_extracted_shift3.txt
```
