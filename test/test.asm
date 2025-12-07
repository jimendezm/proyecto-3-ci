.data
.text
L0: # f1
		addiu	$sp,	$sp,	-16
		sw		$ra,	12($sp)
		sw		$fp,	8($sp)
		move	$fp,	$sp
		sw		$a0,	4($sp)
		lw		$ra,	12($fp)
		lw		$fp,	8($fp)
		addiu	$sp,	$sp,	16
		jr		$ra
		nop
L1: # f2
		addiu	$sp,	$sp,	-20
		sw		$ra,	16($sp)
		sw		$fp,	12($sp)
		move	$fp,	$sp
		sw		$a0,	8($sp)
		sw		$a1,	4($sp)
		lw		$ra,	16($fp)
		lw		$fp,	12($fp)
		addiu	$sp,	$sp,	20
		jr		$ra
		nop
.globl main
main:
		addiu	$sp,	$sp,	-0
		jal		L0
		jal		L1
		li		$v0,	10
		syscall
