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
        move $t0, $a0
        syscall

        # $a0 = buffer, $a1 = length

        #-- verify input
        li $v0, 4  # print string
        la $a0, verify
        syscall
        la $a0, buffer
        move $a0, $t0
        li $v0, 4
        syscall


        #-- Print final and exit
        li $v0, 4  # print string
        la $a0, final
        syscall

        addi $v0, $zero, 10  #finished, exit
        syscall


.data
buffer:
    .space 20

greeting:
    .asciiz "Roman to Arabia converter:\nInput values should be in UPPER case and no more than 20 characters:\n"

ask:
    .asciiz "String to convert?\n"

final:
    .asciiz "\n--The converter has terminated--"

verify:
    .asciiz "\nYou have input:"
