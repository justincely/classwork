######### Factorial Subroutine Fall 2014
#
# Given n, in register $a0;
# calculates n! and stores the result in register $v0

.globl main

.text
 main:
        li $v0, 4  # print string
        la $a0, in_n
        syscall

        


        li $v0, 4  # print string
        la $a0, in_k
        syscall


        addi $a0, $zero, 5   # set input argument to 5
        jal pfctrl

	addi $s0, $v0, 0 #store output in $s0.

        li $v0, 4  # print string
        la $a0, out_str
        syscall

        li $v0, 1  #print int
        addi $a0, $s0, 0  #move result into sys argument
        syscall

        addi $v0, $zero, 10  #finished, exit
        syscall


pfctrl: sw $ra, 4($sp) # save the return address
        sw $a0, 0($sp) # save the current value of n
        addi $sp, $sp, -8 # move stack pointer
        slti $t0, $a0, 2 # save 1 iteration, n=0 or n=1; n!=1
        beq $t0, $zero, L1 # not, calculate n(n-1)!
        addi $v0, $zero, 1 # n=1; n!=1
        jr $ra # now multiply

L1:     addi $a0, $a0, -1 # n := n-1

        jal pfctrl # now (n-1)!

        addi $sp, $sp, 8 # reset the stack pointer
        lw $a0, 0($sp) # fetch saved (n-1)
        lw $ra, 4($sp) # fetch return address
        mul $v0, $a0, $v0 # multiply (n)*(n-1)
        jr $ra # return value n!


.data
out_str:
    .asciiz " The chance of winning is 1 in "

in_n:
    .asciiz " Input the possible numbers: "

in_k:
    .asciiz " Input the number of items chosen: "

# P Snyder 22 April 2015
######### End of the subroutine
