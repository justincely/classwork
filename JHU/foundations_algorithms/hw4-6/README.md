# Summary
This package contains the solution to HW4, HW5, and HW6; compression and cryptography track.  
A Command Line Interface (CLI) has been chosen for the UI.  Specific usage options
and examples are given in the Usage section below.

Input/ouput is supplied, and
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
--readbytes, -rb: Read input as binary.
--output, -o: Filename to send output to.  If omitted, STDOUT will be used.
--writebytes, -wb: Write output as binary.
--compress, -c: Flag to run compression.
  -c huffman: to run huffman compression
  -c lzw: to run LZW compression.
--huffmanfiles: Files from which to build huffman tree.
  only used if huffman compression is run.
--extract, -x: Flag to decompress the text.
  -x huffman: decompress with Huffman
  -x lzw: decompress with LZW.
--encrypt, -e: Flag to encrypt the text.
  -e INT: encrypt with simple shift cypher of INT.
  -e RSA: run RSA encryption
  -e AES: run AES encruption
--decrypt, -d: Flag to decrypt the text.
  -d INT: decrypt with simple shift cypher of INT.
  -d RSA: decrypt with RSA.
  -d AES: decrypt with AES.
```

An example of a valid command sequence is given below.  This would read from
the given file, compress the text the LZW method, and then write to the
specified output file as binary.
```bash
java Driver --input sample_input.txt --output sample_output.txt --compress lzw -wb
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

#### Compression
---------------
Compression can be accomplished through Huffman encoding or LZW compression.
Note that the same algorithm must be used for both compression and extraction
to prevent garbage output, or even code failures.

##### LZW compression/extraction
```bash
java Driver -i ../input/plaintextinput.txt --compress lzw -o ../output/plaintext_huffman_compressed.txt
java Driver -i ../output/plaintext_huffman_compressed.txt --extract lzw -o ../output/plaintext_huffman_extracted_shift3.txt
```

##### Huffman compression/extraction
While LZW compression/decompression can be done with no additional input, Huffman encoding requires
a frequency table that must be created for each compression or extraction.  This
can be read from text files suppied through the --huffmanfiles argument.  

```bash
java Driver -i ../input/plaintextinput.txt --huffmanfiles ../input/huffman*.txt --compress huffman -o ../output/plaintext_huffman_compressed.txt
java Driver -i ../output/plaintext_huffman_compressed.txt --huffmanfiles ../input/huffman*.txt --extract huffman -o ../output/plaintext_huffman_extracted_shift3.txt
```

#### Encryption
----------
Encryption and Decryption can be done by a simple Shift cypher, AES, or RSA
encryption.  Note that the same algorithm must be used for both encryption
and decryption to prevent losing the message.

##### Shift Cypher
A shift cypher can be encryped or decrypted by specifying an integer value with the
encryption or decryption flag.  Note that decryption should not supply the negative
of the incryption value, the reversal will happen in the code.

```bash
java Driver -i ../input/plaintextinput.txt --encrypt 6 -o ../output/shifted_out.txt
java Driver -i ../output/shifted_out.txt --decrypt 6
```

##### AES
AES encryption will auto-generate a private key, and save it to the filename: 'AESKey'
in the local directory.  This key can then be supplied to the decryption algorithm to
decrypt the message through the -k command-line argument.

```bash
java Driver -i ../input/plaintextinput.txt --encrypt AES -o ../output/AESout.txt
java Driver -i ../output/AESout.txt --decrypt AES -k AESKey
```

##### RSA
RSA encryption will auto-generate a public and a private key, and save them
to the filenames 'RSAprivateKey' and 'RSApublicKey', respectively,
in the local directory.  These keys can then be supplied to the decryption algorithm to
decrypt the message through the -k command-line argument.

NOTE the decryption side of this algorithm is incomplete, and will fail.  Though
encryption and decryption, as implemented, will work - writing to a file and
subsequently reading back in cause some unknown difference in the text to cause
an encoding error.  Tests confirmed the algorithms correct behavior when run without
translated to/from text.

```bash
java Driver -i ../input/plaintextinput.txt --encrypt RSA -o ../output/RSAout.txt
java Driver -i ../output/RSAout.txt --decrypt RSA -k RSAprivateKey
```
