import java_cup.runtime.*;
import java.io.InputStream;
import java.io.InputStreamReader;

%%

%class Lexer
%unicode
%cup
%line
%column
%public
%state STRING

%{
  StringBuffer string_buffer = new StringBuffer();
  private Symbol symbol(int type) {
      return new Symbol(type, yyline, yycolumn, yytext());
  }
  private Symbol symbol(int type, Object value) {
      //System.out.println(value);
      return new Symbol(type, yyline, yycolumn, value);
  }
%}

// Ignored things.
eol = \r|\n|\r\n
whitespace =  {eol} | [ \t\f]
oneline_comment = "|" [^\r\n]* {eol}?
multiline_comment = "¡"([^!])*"!"

// Identifier and literals.
id        = [a-zA-Z_][a-zA-Z0-9_]*
int_lit   = (-?[1-9][0-9]*)|0
float_lit = -?([1-9][0-9]*|0)\.([0-9]*[1-9]|0)
char_lit  = \'([^'\\]|\\[nrt'\\])\'

// Keywords.
let       = "let"
int       = "int"
float     = "float"
bool      = "bool"
char      = "char"
string    = "string"
for       = "for"
return    = "return"
input     = "input"
output    = "output"
principal = "principal"
loop      = "loop"
decide    = "decide"
else      = "else"
true      = "true"
false     = "false"
void      = "void"
of        = "of"
end       = "end"
exit      = "exit"
when      = "when"
step      = "step"
to        = "to"
downto    = "downto"
do        = "do"
break     = "break"

// Symbols.
sigma       = "Σ"
dollar      = "$"
lparen      = \u0454 // є
rparen      = \u044d // э
lblock      = "¿"
rblock      = "?"
assign      = "="
plus        = "+"
minus       = "-"
mult        = "*"
div         = "/"
mod         = "%"
power       = "^"
and         = "@"
or          = "~"
eq          = "=="
neq         = "!="
gt          = ">"
lt          = "<"
ge          = ">="
le          = "<="
comma       = ","
arrow       = "->"
lbracket    = "["
rbracket    = "]"
divint      = "//"
inc         = "++"
dec         = "--"

%%

<YYINITIAL> {
  {whitespace}        {/* Ignored */}
  {oneline_comment}   {/* Ignored */}
  {multiline_comment} {/* Ignored */}

  // Keuwords.
  {let}       { return symbol(sym.LET); }
  {float}     { return symbol(sym.FLOAT); }
  {int}       { return symbol(sym.INT); }
  {bool}      { return symbol(sym.BOOL); }
  {char}      { return symbol(sym.CHAR); }
  {string}    { return symbol(sym.STRING); }
  {for}       { return symbol(sym.FOR); }
  {return}    { return symbol(sym.RETURN); }
  {input}     { return symbol(sym.INPUT); }
  {output}    { return symbol(sym.OUTPUT); }
  {principal} { return symbol(sym.PRINCIPAL); }
  {loop}      { return symbol(sym.LOOP); }
  {decide}    { return symbol(sym.DECIDE); }
  {else}      { return symbol(sym.ELSE); }
  {true}      { return symbol(sym.TRUE); }
  {false}     { return symbol(sym.FALSE); }
  {void}      { return symbol(sym.VOID); }
  {of}        { return symbol(sym.OF); }
  {end}       { return symbol(sym.END); }
  {exit}      { return symbol(sym.EXIT); }
  {when}      { return symbol(sym.WHEN); }
  {step}      { return symbol(sym.STEP); }
  {to}        { return symbol(sym.TO); }
  {downto}    { return symbol(sym.DOWNTO); }
  {do}        { return symbol(sym.DO); }
  {break}     { return symbol(sym.BREAK); }


  // Symbols.
  {inc}      { return symbol(sym.INC); }
  {dec}      { return symbol(sym.DEC); }
  {sigma}    { return symbol(sym.SIGMA); }
  {dollar}   { return symbol(sym.DOLLAR); }
  {lparen}   { return symbol(sym.LPAREN); }
  {rparen}   { return symbol(sym.RPAREN); }
  {lblock}   { return symbol(sym.LBLOCK); }
  {rblock}   { return symbol(sym.RBLOCK); }
  {assign}   { return symbol(sym.ASSIGN); }
  {plus}     { return symbol(sym.PLUS); }
  {minus}    { return symbol(sym.MINUS); }
  {mult}     { return symbol(sym.MULT); }
  {div}      { return symbol(sym.DIV); }
  {mod}      { return symbol(sym.MOD); }
  {power}    { return symbol(sym.POW); }
  {and}      { return symbol(sym.AND); }
  {or}       { return symbol(sym.OR); }
  {eq}       { return symbol(sym.EQ); }
  {neq}      { return symbol(sym.NEQ); }
  {gt}       { return symbol(sym.GT); }
  {lt}       { return symbol(sym.LT); }
  {ge}       { return symbol(sym.GE); }
  {le}       { return symbol(sym.LE); }
  {comma}    { return symbol(sym.COMMA); }
  {arrow}    { return symbol(sym.ARROW); }
  {lbracket} { return symbol(sym.LBRACKET); }
  {rbracket} { return symbol(sym.RBRACKET); }
  {divint}   { return symbol(sym.DIVINT); }

  // Identifier and literals.
  {id}         { return symbol(sym.ID, yytext()); }
  {float_lit}  { return symbol(sym.FLOAT_LIT, yytext()); }
  {int_lit}    { return symbol(sym.INT_LIT, yytext()); }
  {char_lit}   { return symbol(sym.CHAR_LIT, yytext().replaceAll("'", "")); }

  // String handler.
  \" { string_buffer.setLength(0); yybegin(STRING); }
}

<STRING> {
  \"          {
                yybegin(YYINITIAL);
                return symbol(sym.STRING_LIT, string_buffer.toString());
              }
  \\n         { string_buffer.append('\n'); }
  \\t         { string_buffer.append('\t'); }
  \\\\        { string_buffer.append('\\'); }
  \\\"        { string_buffer.append('\"'); }
  [^\\\"\n]+  { string_buffer.append(yytext()); }
  \n          {
                throw new RuntimeException("Lexical error: Unterminated string at line " + (yyline + 1) + ". Aborting.");
              }
}

. {
  return symbol(
    sym.LEXICAL_ERROR,
    "Unrecognized character <<" + yytext() + ">>");
}
