.globl main

.text
 main:
        #-- Greeting and instructions
        li $v0, 4  # print string
        la $a0, greeting
        syscall

        la $a0, ask
        syscall

        #-- read Input
        li $v0, 8 # read string
        la $a0, buffer
        li $a1, 20
        move $s0, $a0
        syscall

        #-- verify input
        li $v0, 4  # print string
        la $a0, verify
        syscall
        la $a0, buffer
        move $a0, $s0
        li $v0, 4
        syscall

        addi $t1, $s0, 0 #tmp store location
remove:
        lb $a3, 0($s0)    # Load character at index
        addi $s0, $s0, 1      # Increment index
        bnez $a3, remove     # Loop until the end of string is reached
        beq $a1, $s0, skip    # Do not remove \n when string = maxlength
        addi $s0, $s0, -2     # If above not true, Backtrack index to '\n'
        sb $0, 0($s0)    # Add the terminating character in its place
skip:

        addi $s0, $t1, 0 #restore location

        #-- convert
        addi $s1, $zero, 0 #counter
loop:
        lb $s2, 0($s0)  # character

        #-- print character
        #li $v0, 11 #print character
        #addi $a0, $s2, 0
        #syscall

        #li $v0, 4  # print string
        #la $a0, sep
        #syscall
        #--

        addi $a0, $s2, 0
        #li $v0, 1
        #addi $a0, $s0, 0
        #syscall

        #-- check for null and exit
        beq $s2, $zero, exit
        jal arabic
        addi $s1, $s1, 1 # Count = Count + 1
        addi $s0, $s0, 1
        j loop


exit:
        #-- Print final and exit
        li $v0, 4  # print string
        la $a0, final
        syscall

        addi $v0, $zero, 10  #finished, exit
        syscall


#--- find value of character
arabic:
        sw $ra, 4($sp)
        sw $a0, 0($sp)
        addi $sp, $sp, -8


        move $s7, $a0  #store argument
        la $t0, numerals
        la $t1, values

next:
        lb $t2, 0($t0)  #grab next roman numeral
        lb $t3, 0($t1)  #grab numeric value

        #-- print character
        #li $v0, 11
        #addi $a0, $s7, 0
        #syscall

        #li $v0, 4
        #la $a0, sep
        #syscall

        #li $v0, 11
        #addi $a0, $t2, 0
        #syscall
        #--

        #-- print int
        #li $v0, 1
        #addi $a0, $t1, 0
        #syscall
        #--

        beq $t2, $s7, return
        addi $t0, $t0, 1
        addi $t1, $t1, 1
        j next

return:

        #-- print character
        li $v0, 11
        addi $a0, $s7, 0
        syscall

        li $v0, 4
        la $a0, sep
        syscall

        li $v0, 1
        addi $a0, $t3, 0
        syscall
        #---

        jr $ra   # return


.data
buffer:
    .space 20

numerals: .asciiz "IVXLCDMivxlcdm"
values: .byte 1, 5, 10, 50, 100, 500, 1000, 1, 5, 10, 50, 100, 500, 1000

greeting:
    .asciiz "Roman to Arabia converter:\nInput values should be in UPPER case and no more than 20 characters:\n"

ask:
    .asciiz "String to convert?\n"

final:
    .asciiz "\n--The converter has terminated--"

verify:
    .asciiz "\nYou have input:"

sep:
    .asciiz " "

newline:
    .asciiz "\n"
