.data
.text
L0: # f
		addiu	$sp,	$sp,	-20
		sw	$ra,	16($sp)
		sw	$fp,	12($sp)
		move	$fp,	$sp
		sw	$a0,	8($sp)
		sw	$a1,	4($sp)
		lw	$ra,	16($sp)
		lw	$fp,	12($sp)
		addiu	$sp,	$sp,	20
		jr	$ra
L1: # f2
		addiu	$sp,	$sp,	-20
		sw	$ra,	16($sp)
		sw	$fp,	12($sp)
		move	$fp,	$sp
		sw	$a0,	8($sp)
		sw	$a1,	4($sp)
		lw	$ra,	16($sp)
		lw	$fp,	12($sp)
		addiu	$sp,	$sp,	20
		jr	$ra
.globl main
main:
		li	$v0,	10
		syscall
