.data
.text
L0: # f1
		addiu	$sp,	$sp,	-12
		sw		$ra,	8($sp)
		sw		$fp,	4($sp)
		move	$fp,	$sp
		sw		$a0,	0($sp)
		lw		$ra,	8($fp)
		lw		$fp,	4($fp)
		addiu	$sp,	$sp,	12
		jr		$ra
		nop
L1: # f2
		addiu	$sp,	$sp,	-16
		sw		$ra,	12($sp)
		sw		$fp,	8($sp)
		move	$fp,	$sp
		sw		$a0,	4($sp)
		sw		$a1,	0($sp)
		lw		$ra,	12($fp)
		lw		$fp,	8($fp)
		addiu	$sp,	$sp,	16
		jr		$ra
		nop
.globl main
main:
		addiu	$sp,	$sp,	-16
		jal		L0
		jal		L1
		li		$v0,	10
		syscall
