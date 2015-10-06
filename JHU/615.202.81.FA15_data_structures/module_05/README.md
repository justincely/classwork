Usage
-----
Postfix expressions to be evaluated may be given directly as arguements, as lines in a file, or any combination of the two.
```java
$ java PostfixEval 'ABC+$CBA-+*'

$ java PostfixEval File1.txt

$ java PostfixEval 'ABC+$CBA-+*' File1.txt
```

Output
------
As per the program requirements: the program prints the instructions to
standard output.  Writing to a file can be done using redirection:
```java
java PostfixEval "AB+" >> simple_out.txt
```

Limitations
-----------

1.  The stack is limited to Strings  
2.  Expressions can be only 50 characters long before overflow  
3.  The evaluator doesn't handle parenthesis


Usage Note
----------

Postfix arguments passed straight to the evaluator must be enclosed in single
quotes to prevent the shell from expanding wildcards.

Notice in the following example that the input expression is modified by the
shell when supplied without quotes.

```java
$ java PostfixEval ABC+$CBA-+*
----------------
Given expression: ABC+-+*
----------------
--Translating: ABC+-+* into machine code.
LD B
AD C
ST TEMP1
LD A
SB TEMP1
ST TEMP2
Exception in thread "main" java.lang.UnsupportedOperationException: Empty stack on second arg
	at PostfixEval.translate(PostfixEval.java:141)
	at PostfixEval.main(PostfixEval.java:28)
```

```java
$ java PostfixEval 'ABC+$CBA-+*'
----------------
Given expression: ABC+$CBA-+*
----------------
--Translating: ABC+$CBA-+* into machine code.
LD B
AD C
ST TEMP1
LD A
PW TEMP1
ST TEMP2
LD B
SB A
ST TEMP3
LD C
AD TEMP3
ST TEMP4
LD TEMP2
ML TEMP4
ST TEMP5
```
