
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     I = 258,
     S = 259,
     C = 260,
     PI = 261,
     JST = 262,
     LAT = 263,
     PO = 264,
     ALA = 265,
     PLUS = 266,
     MINUS = 267,
     TIMES = 268,
     DIV = 269,
     LESS = 270,
     LESSEQ = 271,
     EQ = 272,
     NEQ = 273,
     BIGGEREQ = 274,
     EQQ = 275,
     BIGGER = 276,
     SQRT = 277,
     MODULO = 278,
     ASSIGN = 279,
     SQBRACKETOPEN = 280,
     SQBRACKETCLOSE = 281,
     SEMICOLON = 282,
     OPEN = 283,
     CLOSE = 284,
     BRACKETOPEN = 285,
     BRACKETCLOSE = 286,
     COMMA = 287,
     IDENTIFIER = 288,
     INTCONSTANT = 289,
     STRINGCONSTANT = 290
   };
#endif
/* Tokens.  */
#define I 258
#define S 259
#define C 260
#define PI 261
#define JST 262
#define LAT 263
#define PO 264
#define ALA 265
#define PLUS 266
#define MINUS 267
#define TIMES 268
#define DIV 269
#define LESS 270
#define LESSEQ 271
#define EQ 272
#define NEQ 273
#define BIGGEREQ 274
#define EQQ 275
#define BIGGER 276
#define SQRT 277
#define MODULO 278
#define ASSIGN 279
#define SQBRACKETOPEN 280
#define SQBRACKETCLOSE 281
#define SEMICOLON 282
#define OPEN 283
#define CLOSE 284
#define BRACKETOPEN 285
#define BRACKETCLOSE 286
#define COMMA 287
#define IDENTIFIER 288
#define INTCONSTANT 289
#define STRINGCONSTANT 290




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


