public class Main {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable(15);
        Pair<Integer,Integer> p1 = new Pair<>(-1,-1);
        Pair<Integer,Integer> p2 = new Pair<>(-1,-1);
        Pair<Integer,Integer> p3 = new Pair<>(-1,-1);

        try {
            System.out.println("Identifiers: \n");
            p1 = symbolTable.addIdentifier("idk");
            System.out.println("idk -> "+ p1+ "\n");
            System.out.println("yup -> " + symbolTable.addIdentifier("yup")+ "\n");
            System.out.println("hy -> "+ symbolTable.addIdentifier("hy")+"\n");
            System.out.println("Identifier: \n");
            System.out.println("idk ->"+ symbolTable.getIdentifierPos("idk"));
            System.out.println("www ->"+ symbolTable.getIdentifierPos("www"));

            System.out.println("Integer constants: \n");
            p2 = symbolTable.addIntConst(33);
            System.out.println("33 ->" + p2 + "\n");
            System.out.println("66 ->" + symbolTable.addIntConst(66) +"\n");
            System.out.println("99 ->" + symbolTable.addIntConst(99) + "\n");
            System.out.println("Integer constant: \n");
            System.out.println("33 ->" + symbolTable.getIntConstPos(33) +"\n");

            System.out.println("String Constants: \n");
            p3 = symbolTable.addStringConst("adi");
            System.out.println("adi ->"+ p3 +"\n");
            System.out.println("schiop ->" + symbolTable.addStringConst("schiop") + "\n");
            System.out.println("clar ->"+  symbolTable.addStringConst("clar") + "\n");

            System.out.println("String const: \n");
            System.out.println("adi ->" + symbolTable.getStringConstPos("adi"));


            System.out.println("\n");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}