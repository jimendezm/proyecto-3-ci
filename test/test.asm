.data
.text
L0: # f
		jr	$ra
L1: # f2
		jr	$ra
.globl main
main:
		li	$v0,	10
		syscall
