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

        #-- convert
        addi $s1, $zero, 0 #counter
loop:
        lb $s2, 0($s0)  # character

        #-- print character
        li $v0, 11 #print character
        addi $a0, $s2, 0
        syscall
        #--

        #jal arabic
        #li $v0, 1
        #addi $a0, $s0, 0
        #syscall

        #-- check for null and exit
        beq $s2, $zero, exit
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

        la $t0, numerals
        la $t1, values

next:
        lb $t2, 0($t0)  #grab next roman numeral

        #-- print character
        li $v0, 11 #print character
        addi $a0, $t2, 0
        syscall
        #--

        beq $t2, $a0, return
        addi $t0, $t0, 1
        addi $t1, $t1, 1
        j next

return:
        lb $v0, 0($t1)  #grab numeric value
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
