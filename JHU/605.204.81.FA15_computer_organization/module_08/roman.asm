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

        ####
        # as of here - only $s0 should be saved
        ####

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

        ####
        # as of here - only $s0 should be saved
        ####



        ####
        # $s0, $s2, $s3, $s4, $s5

        # s4 - value
        # s5 - next value
        ####
        #-- convert
        addi $s3, $zero, 0 #output value init to 0
loop:
        lb $s2, 0($s0)  # character

        #-- check for null and exit
        beq $s2, $zero, exit

        #-- calculate first numeric
        addi $a0, $s2, 0
        jal arabic
        move $s4, $v0  #grab value of numeral.

        #-- print character
        #li $v0, 11
        #addi $a0, $s2, 0
        #syscall

        #li $v0, 4
        #la $a0, sep
        #syscall

        #li $v0, 1
        #addi $a0, $s4, 0
        #syscall
        #---


        #-- Grab next if possible
        addi $t1, $s0, 1
        lb $s5, 0($t1)
        beq $s5, $zero, justadd  #if next is the end go to the add
        move $a0, $s5
        jal arabic
        move $s5, $v0 #grab returned value

        #-- print character
        #li $v0, 11
        #addi $a0, $s2, 0
        #syscall

        #li $v0, 4
        #la $a0, sep
        #syscall

        #li $v0, 1
        #addi $a0, $s5, 0
        #syscall
        #---

        #-- conditionals
        #-- if value = 1, next = 5
        addi $t7, $zero, 1
        seq $t1, $s4, $t7

        addi $t7, $zero, 5
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #-- if value = 1, next = 10
        addi $t7, $zero, 1
        seq $t1, $s4, $t7

        addi $t7, $zero, 10
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #-- if value = 10, next = 50
        addi $t7, $zero, 10
        seq $t1, $s4, $t7

        addi $t7, $zero, 50
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #-- if value = 10, next = 100
        addi $t7, $zero, 10
        seq $t1, $s4, $t7

        addi $t7, $zero, 100
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #-- if value = 100, next = 500
        addi $t7, $zero, 100
        seq $t1, $s4, $t7

        addi $t7, $zero, 500
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #-- if value = 100, next = 1000
        addi $t7, $zero, 100
        seq $t1, $s4, $t7

        addi $t7, $zero, 1000
        seq $t2, $s5, $t7

        and $t3, $t1, $t2
        addi $t4, $zero, 1
        beq $t3, $t4, subadd

        #else
        j justadd

justadd:
        add $s3, $s3, $s4
        addi $s0, $s0, 1
        j loop
subadd:
        sub $t1, $s5, $s4
        add $s3, $s3, $t1
        addi $s0, $s0, 2
        j loop



exit:
        #print results
        li $v0, 4
        la $a0, decimal
        syscall

        li $v0, 1
        addi $a0, $s3, 0
        syscall

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
        lw $t3, 0($t1)  #grab numeric value

        beq $t2, $s7, return
        addi $t0, $t0, 1
        addi $t1, $t1, 4
        j next

return:
        #-- print character
        #li $v0, 11
        #addi $a0, $s7, 0
        #syscall

        #li $v0, 4
        #la $a0, sep
        #syscall

        #li $v0, 1
        #addi $a0, $t3, 0
        #syscall
        #---
        move $v0, $t3
        jr $ra   # return





.data
buffer:
    .space 20

numerals: .asciiz "IVXLCDMivxlcdm"
values: .word 1, 5, 10, 50, 100, 500, 1000, 1, 5, 10, 50, 100, 500, 1000

greeting:
    .asciiz "Roman to Arabia converter:\nInput values should be in UPPER case and no more than 20 characters:\n"

ask:
    .asciiz "String to convert?\n"

decimal:
    .asciiz "\nThe decimal value is :"

final:
    .asciiz "\n--The converter has terminated--"

verify:
    .asciiz "\nYou have input:"

sep:
    .asciiz " "

newline:
    .asciiz "\n"
