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
L2: # f3
		addiu	$sp,	$sp,	-24
		sw		$ra,	20($sp)
		sw		$fp,	16($sp)
		move	$fp,	$sp
		sw		$a0,	12($sp)
		sw		$a1,	8($sp)
		sw		$a2,	4($sp)
		lw		$ra,	20($fp)
		lw		$fp,	16($fp)
		addiu	$sp,	$sp,	24
		jr		$ra
		nop
L3: # f4
		addiu	$sp,	$sp,	-28
		sw		$ra,	24($sp)
		sw		$fp,	20($sp)
		move	$fp,	$sp
		sw		$a0,	16($sp)
		sw		$a1,	12($sp)
		sw		$a2,	8($sp)
		sw		$a3,	4($sp)
		lw		$ra,	24($fp)
		lw		$fp,	20($fp)
		addiu	$sp,	$sp,	28
		jr		$ra
		nop
L4: # f5
		addiu	$sp,	$sp,	-28
		sw		$ra,	24($sp)
		sw		$fp,	20($sp)
		move	$fp,	$sp
		sw		$a0,	16($sp)
		sw		$a1,	12($sp)
		sw		$a2,	8($sp)
		sw		$a3,	4($sp)
		lw		$ra,	24($fp)
		lw		$fp,	20($fp)
		addiu	$sp,	$sp,	28
		jr		$ra
		nop
.globl main
main:
		li		$v0,	10
		syscall
