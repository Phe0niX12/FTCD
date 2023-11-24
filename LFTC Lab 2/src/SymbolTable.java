public class SymbolTable {
    private final HashTable<String> identifiersHashTable;
    private final HashTable<Integer> intConstHashTable;
    private final HashTable<String> stringConstHashTable;


    public SymbolTable(int size) {
        this.identifiersHashTable = new HashTable<>(size);
        this.intConstHashTable = new HashTable<>(size);
        this.stringConstHashTable = new HashTable<>(size);
    }

    public Pair<Integer, Integer> addIdentifier(String name) throws Exception {
        return identifiersHashTable.add(name);
    }

    public Pair<Integer,Integer> addIntConst(int constant) throws Exception{
        return intConstHashTable.add(constant);
    }

    public Pair<Integer,Integer> addStringConst(String constant) throws Exception{
        return stringConstHashTable.add(constant);
    }

    public Pair<Integer,Integer> getIntConstPos(int constant){
        return intConstHashTable.getPosition(constant);
    }

    public Pair<Integer,Integer> getStringConstPos(String constant){
        return stringConstHashTable.getPosition(constant);
    }

    public Pair<Integer,Integer> getIdentifierPos(String name){
        return identifiersHashTable.getPosition(name);
    }

    public  boolean hasIdentifier(String name){
        return identifiersHashTable.contains(name);
    }
    public boolean hasConstantInt(int constant){
        return intConstHashTable.contains(constant);
    }
    public boolean hasConstantString(String constant){
        return stringConstHashTable.contains(constant);
    }

    @Override
    public String toString() {
        return "SymbolTable_I{ " +
                "\nidentifiers HashTable=" + identifiersHashTable +
                "\n int Const HashTable=" + intConstHashTable +
                "\n string Const HashTable=" + stringConstHashTable +
                "\n}";
    }
}
