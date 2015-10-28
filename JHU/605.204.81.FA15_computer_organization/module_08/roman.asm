.globl main

.text
 main:
        #-----------------------------
        #-- Greeting, instructions
        #-- read input
        #-----------------------------
        li $v0, 4
        la $a0, greeting
        syscall

read:
        li $v0, 4
        la $a0, ask
        syscall

        #-- read Input string
        li $v0, 8
        la $a0, buffer
        li $a1, 20
        move $s0, $a0
        syscall

        #-- print out input as verification
        li $v0, 4
        la $a0, verify
        syscall
        la $a0, buffer
        move $a0, $s0
        li $v0, 4
        syscall


        #--------------------------
        #-- Strip newline character
        #-- from input
        #--------------------------
        addi $t1, $s0, 0            # tmp store location
remove:
        lb $a3, 0($s0)              # Load character at index
        addi $s0, $s0, 1            # Increment index
        bnez $a3, remove            # Loop until the end of string is reached
        beq $a1, $s0, skip          # Do not remove \n when string = maxlength
        addi $s0, $s0, -2           # If above not true, Backtrack index to '\n'
        sb $0, 0($s0)               # Add the terminating character in its place
skip:
        addi $s0, $t1, 0            #restore location


        #--------------------------
        #-- Begin conversion
        #-------------------------
        addi $s3, $zero, 0          # output value init to 0
loop:
        lb $s2, 0($s0)              # character

        beq $s2, $zero, finish      # check for null and exit

        addi $a0, $s2, 0            # load character as argument
        jal arabic                  # calculate first numeric
        move $s4, $v0               # grab returned value of numeral.

        #-- Grab next if possible
        addi $t1, $s0, 1            # offset cursor to next value
        lb $s5, 0($t1)              # load the character
        beq $s5, $zero, justadd     # if next is the end go to the add
        move $a0, $s5               # load character as arguement
        jal arabic                  # comput second numeric
        move $s5, $v0               # grab returned value of numeral

        #---------------------------------
        #-- conditional block
        #-- to deal with subtraction rules
        #----------------------------------

        #-- if value = 1, next = 5
        addi $t7, $zero, 1          # t7 = 1
        seq $t1, $s4, $t7           # if value == 1, t1 = 1

        addi $t7, $zero, 5          # t7 = 5
        seq $t2, $s5, $t7           # if next == 5, t2 = 1

        and $t3, $t1, $t2           # if (value == 1) and (next == 5): t3 = 1
        addi $t4, $zero, 1          # t4 = 1
        beq $t3, $t4, subadd        # if conditional is true, goto subtraction

        #-- if value = 1, next = 10
        addi $t7, $zero, 1          # ditto above, for each set of possible
        seq $t1, $s4, $t7           # triggering values for subtraction

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

        j justadd                   # else, add

justadd:
        #--------------------------
        # Add value to running sum
        #--------------------------
        add $s3, $s3, $s4
        addi $s0, $s0, 1
        j loop
subadd:
        #------------------------------
        # subtract current from next
        # before adding to running sum
        #------------------------------
        sub $t1, $s5, $s4
        add $s3, $s3, $t1
        addi $s0, $s0, 2
        j loop


finish:
        #--------------------------
        #-- Print values and cycle
        #--------------------------
        li $v0, 4                   # print result string
        la $a0, decimal
        syscall

        li $v0, 1                   # print result number
        addi $a0, $s3, 0
        syscall

        li $v0, 4
        la $a0, repeat
        syscall

        #-- read Input string
        li $v0, 8
        la $a0, buffer
        li $a1, 20
        move $s0, $a0
        syscall

        lb $t1, 0($s0)              # read character
        beq $t1, 'y', read          # go back to beginning


exit:
        #------------------------
        #--exit
        #------------------------
        li $v0, 4                   # print final message
        la $a0, final
        syscall

        addi $v0, $zero, 10         # exit
        syscall

#---------------------------
#--- find value of character
#---------------------------
arabic:
        sw $ra, 4($sp)
        sw $a0, 0($sp)
        addi $sp, $sp, -8

        move $s7, $a0               # store argument
        la $t0, numerals            # load character array
        la $t1, values              # load value array

next:
        lb $t2, 0($t0)              # grab next roman numeral
        lw $t3, 0($t1)              # grab numeric value

        beq $t2, $s7, return        # return if character match is found
        addi $t0, $t0, 1            # advance character pointer
        addi $t1, $t1, 4            # advance value pointer
        j next

return:
        move $v0, $t3               # move value to output
        jr $ra                      # return


#--------------#
#  Data below
#--------------#
.data
buffer:
    .space 20

numerals:
    .asciiz "IVXLCDMivxlcdm"

values:
    .word 1, 5, 10, 50, 100, 500, 1000, 1, 5, 10, 50, 100, 500, 1000

greeting:
    .asciiz "#-- Roman to Arabia converter --#\nInput values may be in either upper or lower case \nand should be no more than 20 characters long.\n\n"

ask:
    .asciiz "What would you like to convert?\n"

decimal:
    .asciiz "\nThe decimal value is: "

final:
    .asciiz "\n#-- The converter has terminated --#"

verify:
    .asciiz "\nYou have input:"

repeat:
    .asciiz "\nWould you like to input another value? y/n: "
