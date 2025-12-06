.data
.text
L0: # f
		addiu	$sp,	$sp,	-20
		sw	$ra,	16($sp)
		sw	$fp,	12($sp)
		move	$fp,	$sp
		sw	$a0,	8($sp)
		sw	$a1,	4($sp)
		lw	$ra,	16($fp)
		lw	$fp,	12($fp)
		addiu	$sp,	$sp,	20
		jr	$ra
		nop
L1: # f2
		addiu	$sp,	$sp,	-20
		sw	$ra,	16($sp)
		sw	$fp,	12($sp)
		move	$fp,	$sp
		sw	$a0,	8($sp)
		sw	$a1,	4($sp)
		lw	$ra,	16($fp)
		lw	$fp,	12($fp)
		addiu	$sp,	$sp,	20
		jr	$ra
		nop
.globl main
main:
		li	$v0,	10
		syscall
