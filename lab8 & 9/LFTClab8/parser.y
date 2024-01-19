%{
#include <stdio.h>
#include <stdlib.h>

int yyerror(char *s);

#define YYDEBUG 1
%}


%token I;
%token S;
%token C;
%token PI;
%token JST;
%token LAT;
%token PO;
%token ALA;


%token PLUS;
%token MINUS;
%token TIMES;
%token DIV;
%token LESS;
%token LESSEQ;
%token EQ;
%token NEQ;
%token BIGGEREQ;
%token EQQ;
%token BIGGER;
%token SQRT;
%token MODULO;
%token ASSIGN;

%token SQBRACKETOPEN;
%token SQBRACKETCLOSE;
%token SEMICOLON;
%token OPEN;
%token CLOSE;
%token BRACKETOPEN;
%token BRACKETCLOSE;
%token COMMA;

%token IDENTIFIER;
%token INTCONSTANT;
%token STRINGCONSTANT;

%start CompoundStatement

%%
CompoundStatement : Statement CompoundStatement     { printf("CompoundStatement -> Statement ; CompoundStatement\n"); }
                  | Statement                       { printf("CompoundStatement -> Statement ;\n"); }
                  ;
Statement : DeclarationStatement     { printf("Statement -> DeclarationStatement\n"); }
          | AssignmentStatement     { printf("Statement -> AssignmentStatement\n"); }
          | IfStatement     { printf("Statement -> IfStatement\n"); }
          | WhileStatement     { printf("Statement -> WhileStatement\n"); }
          | PrintStatement     { printf("Statement -> PrintStatement\n"); }
          | ReadStatement     { printf("Statement -> ReadStatement\n"); }
          ;
DeclarationStatement : Type ASSIGN IDENTIFIER    { printf("DeclarationStatement -> Type ASSIGN IDENTIFIER \n"); }
                     ;
Type : I     { printf("Type -> int\n"); }
     | S     { printf("Type -> str\n"); }
     | C     { printf("Type -> char\n"); }
     ;
AssignmentStatement : IDENTIFIER EQ Expression     { printf("AssignmentStatement -> IDENTIFIER = Expression\n"); }
                    ;
Expression : Expression PLUS Term     { printf("Expression -> Expression + Term\n"); }
           | Expression MINUS Term     { printf("Expression -> Expression - Term\n"); }
           | Term     { printf("Expression -> Term\n"); }
           ;
Term : Term TIMES Factor     { printf("Term -> Term * Factor\n"); }
     | Term DIV Factor     { printf("Term -> Term / Factor\n"); }
     | Term MODULO Factor    { printf("Term -> Term # Factor\n"); }
     | Factor     { printf("Term -> Factor\n"); }
     ;
Factor : OPEN Expression CLOSE     { printf("Factor -> ( Expression )\n"); }
       | IDENTIFIER     { printf("Factor -> IDENTIFIER\n"); }
       | INTCONSTANT    { printf("Factor -> INTCONSTANT\n"); }
       ;


IfStatement : JST OPEN Condition CLOSE BRACKETOPEN CompoundStatement BRACKETCLOSE  { printf("IfStatement -> JST ( Expression ) { CompoundStatement }\n"); }
            | JST OPEN Condition CLOSE BRACKETOPEN CompoundStatement BRACKETCLOSE LAT BRACKETOPEN CompoundStatement BRACKETCLOSE  { printf("IfStatement -> JST Expression { CompoundStatement } LAT { CompoundStatement }\n"); }
            ;
WhileStatement : ALA OPEN Condition CLOSE BRACKETOPEN CompoundStatement BRACKETCLOSE  { printf("WhileStatement -> ALA ( Expression ) { CompoundStatement }\n"); }
               ;
PrintStatement : PO OPEN Expression CLOSE     { printf("PrintStatement -> print ( Expression )\n"); }
               | PO OPEN STRINGCONSTANT CLOSE     { printf("PrintStatement -> print ( STRINGCONSTANT )\n"); }
               ;
ReadStatement : PI OPEN IDENTIFIER CLOSE     { printf("ReadStatement -> read ( IDENTIFIER )\n"); }
              ;
Condition : Expression Relation Expression     { printf("Condition -> Expression Relation Expression\n"); }
          ;
Relation : LESS     { printf("Relation -> <\n"); }
         | LESSEQ     { printf("Relation -> <=\n"); }
         | EQQ     { printf("Relation -> ==\n"); }
         | NEQ     { printf("Relation -> !=\n"); }
         | BIGGEREQ     { printf("Relation -> >=\n"); }
         | BIGGER     { printf("Relation -> >\n"); }
         ;
%%
int yyerror(char *s) {
    printf("Error: %s", s);
}

extern FILE *yyin;

int main(int argc, char** argv) {
    if (argc > 1)
        yyin = fopen(argv[1], "r");
    if (!yyparse())
        fprintf(stderr, "\tOK\n");
}