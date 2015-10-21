.globl main

.text
 main:
        #-- Load arguements
        li $v0, 4  # print string
        la $a0, in_n
        syscall

        li $v0, 5  #read integer
        syscall
        addi $s0, $v0, 0

        li $v0, 4  # print string
        la $a0, in_k
        syscall

        li $v0, 5 #read integer
        syscall
        addi $s1, $v0, 0
        #--

        sub $s2, $s0, $s1  # calculate n-k

        #confirmation output for testing
        li $v0, 4
        la $a0, confirm
        syscall

        li $v0, 1
        addi $a0, $s0, 0
        syscall

        li $v0, 4  # separator
        la $a0, sep
        syscall

        li $v0, 1
        addi $a0, $s1, 0
        syscall

        li $v0, 4  # separator
        la $a0, sep
        syscall

        li $v0, 1
        addi $a0, $s2, 0
        syscall
        #----

        #-- use factorial algedbra to
        #-- calculate n! / (n-k)!
        addi $t1, $s2, 0
        addi $t2, $zero, 1
loop:
        slt $t3, $t1, $s0
        beq $t3, $zero, exit # exit loop
        mul $t2, $t2, $s0 # mult
        addi $s0, $s0, -1
        j loop
exit:
        add $a0, $zero, $s1  #set input argument to second input
        jal pfctrl
        addi $s4, $v0, 0 # k!

        div $s6, $t2, $s4  # n!/(n-k) / k!

        #-- Probability from input
        li $v0, 4  # print string
        la $a0, out_str
        syscall

        li $v0, 1  #print int
        addi $a0, $s6, 0  #move result into sys argument
        syscall
        #--

        #-- with the powerball
        li $v0, 4  # print string
        la $a0, powerball
        syscall
        la $a0, final
        syscall

        addi $t1, $zero, 35
        mul $s6, $s6, $t1

        li $v0, 1  #print int
        addi $a0, $s6, 0  #move result into sys argument
        syscall

        addi $v0, $zero, 10  #finished, exit
        syscall

######### Factorial Subroutine Fall 2014
#
# Given n, in register $a0;
# calculates n! and stores the result in register $v0

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
# P Snyder 22 April 2015
######### End of the subroutine


.data
out_str:
    .asciiz ", the chance of winning is 1 in "

in_n:
    .asciiz " Input the possible numbers: "

in_k:
    .asciiz " Input the number of items chosen: "

confirm:
    .asciiz " With N, K, (N-k): "

sep:
    .asciiz ", "

powerball:
    .asciiz "\n\n If this is the Powerball Lottery, there is an additional 1/35 chance of picking the powerball correctly."

final:
    .asciiz "\n This means the total probability is 1 in "
