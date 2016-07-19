# Summary
This package contains the solution to HW4; compression and cryptography track.  
A Command Line Interface (CLI) has been chosen for the UI.  Specific usage options
and examples are given in the Usage section below.

Note that initial work on Huffman encoding and decoding has already been
implemented, so sample input/output has also been provided to test the compression
and extraction using a default frequency table.  Input/ouput is supplied, and
the ouput can be re-generated using the Makefile.


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
--output, -o: Filename to send output to.  If omitted, STDOUT will be used.
--compress, -c: Flag to run compression algorithm.
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
