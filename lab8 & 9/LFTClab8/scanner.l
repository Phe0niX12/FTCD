

%option noyywrap
%option caseless

DIGIT [0-9]
NON_ZERO_DIGIT [1-9]
INT_CONSTANT [+-]?{NON_ZERO_DIGIT}{DIGIT}*|0
LETTER [a-zA-Z_]
SPECIAL_CHAR [ ?:*\^+=.!]
STRING_CONSTANT (\"({LETTER}|{DIGIT}|{SPECIAL_CHAR})*\")
IDENTIFIER {LETTER}({LETTER}|{DIGIT})*
BAD_IDENTIFIER ({DIGIT})+({LETTER})+({LETTER}|{DIGIT})*

%%


"s" { printf("%s - reserved word\n", yytext); return I; }
"i" { printf("%s - reserved word\n", yytext); return S; }
"c" { printf("%s - reserved word\n", yytext); return C; }
"PI" { printf("%s - reserved word\n", yytext); return PI; }
"JST" { printf("%s - reserved word\n", yytext); return JST; }
"LAT" { printf("%s - reserved word\n", yytext); return LAT; }
"PO" { printf("%s - reserved word\n", yytext); return PO; }
"ALA" { printf("%s - reserved word\n", yytext); return ALA; }


"+" { printf("%s - operator\n", yytext); return PLUS; }
"-" { printf("%s - operator\n", yytext); return MINUS; }
"*" { printf("%s - operator\n", yytext); return TIMES; }
"/" { printf("%s - operator\n", yytext); return DIV; }
"<" { printf("%s - operator\n", yytext); return LESS; }
"<=" { printf("%s - operator\n", yytext); return LESSEQ; }
"=" { printf("%s - operator\n", yytext); return EQ; }
">=" { printf("%s - operator\n", yytext); return BIGGEREQ; }
"==" { printf("%s - operator\n", yytext); return EQQ; }
"!=" { printf("%s - operator\n", yytext); return NEQ; }
">" { printf("%s - operator\n", yytext); return BIGGER; }
"sqrt" { printf("%s - operator\n", yytext); return SQRT; }
"#" { printf("%s - operator\n", yytext); return MODULO; }
"::" { printf("%s - operator\n", yytext); return ASSIGN; }


";" { printf("%s - separator\n", yytext); return SEMICOLON; }
"(" { printf("%s - separator\n", yytext); return OPEN; }
")" { printf("%s - separator\n", yytext); return CLOSE; }
"{" { printf("%s - separator\n", yytext); return BRACKETOPEN; }
"}" { printf("%s - separator\n", yytext); return BRACKETCLOSE; }
"," { printf("%s - separator\n", yytext); return COMMA; }

{IDENTIFIER} { printf("%s - identifier\n", yytext); return IDENTIFIER; }

{BAD_IDENTIFIER} { printf("Error at token %s at line %d\n", yytext, lines); return -1; }

{INT_CONSTANT} { printf("%s - integer constant\n", yytext); return INTCONSTANT; }

{STRING_CONSTANT} { printf("%s - string constant\n", yytext); return STRINGCONSTANT; }

[ \t]+ {}

[\n]+ {++lines;}

. {printf("Error at token %s at line %d\n", yytext, lines); exit(1);}

%%