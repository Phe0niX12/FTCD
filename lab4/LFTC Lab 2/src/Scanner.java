import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class Scanner {

    private String program;
    private final List<String> tokens;
    private final List<String> reservedWords;
    private SymbolTable symbolTable;
    private List<Pair<String, Pair<Integer, Integer>>> PIF;
    private int index = 0;
    private int currentLine=1;


    public Scanner() {
        this.symbolTable = new SymbolTable(47);
        this.PIF = new ArrayList<>();
        this.reservedWords = new ArrayList<>();
        this.tokens = new ArrayList<>();
        try {
            readTokens();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void readTokens() throws IOException{
        File file = new File("src/Resources/token.in");
        BufferedReader br = Files.newBufferedReader(file.toPath());
        String line;
        while ((line = br.readLine()) != null){
            String[] parts = line.split(" ");
            for(String part :parts) {
                switch (part) {
                    case "AND", "OR", "AF", "JST", "LAT", "PO", "PI", "ALA", "i::", "||", "&&", "s::" -> this.reservedWords.add(part);
                    default -> tokens.add(part);
                }
            }
        }
    }
    private void setProgram(String program){
        this.program = program;
    }


    private void skipSpaces() {
        while (index < program.length() && Character.isWhitespace(program.charAt(index))){
            if(program.charAt(index) == '\n'){
                currentLine++;
            }
            index++;
        }
    }

    private boolean treatStringConstant() {
        var regexForStringConstant = Pattern.compile("^\"[a-zA-z0-9_ ?:*^+=.!]*\"");
        var matcher = regexForStringConstant.matcher(program.substring(index));
        if (!matcher.find()) {
            if (Pattern.compile("^\"[^\"]\"").matcher(program.substring(index)).find()) {
                throw new RuntimeException("Invalid string constant at line " + currentLine);
            }
            if (Pattern.compile("^\"[^\"]").matcher(program.substring(index)).find()) {
                throw new RuntimeException("Missing \" at line " + currentLine);
            }
            return false;
        }
        var stringConstant = matcher.group(0);
        index += stringConstant.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addStringConst(stringConstant);
        } catch (Exception e) {
            position = symbolTable.getStringConstPos(stringConstant);
        }
        PIF.add(new Pair<>("str const", position));
        return true;
    }

    private boolean caseIntConstant(){
//        var regexForIntConstant = Pattern.compile("^([+-]?[1-9][0-9]*|0)");
//        var matcher = regexForIntConstant.matcher(program.substring(index));
//        if (!matcher.find()) {
//            return false;
//        }
//        if (Pattern.compile("^([+-]?[1-9][0-9]*|0)[a-zA-z_]").matcher(program.substring(index)).find()) {
//            return false;
//        }

        var fa = new FA("./src/Resources/intConstant.in");
        var intConstant = fa.getNextAccepted(program.substring(index));
        Pair<Integer, Integer> position;
        if (Objects.equals(intConstant, null)) {
            return false;
        }
        if ((intConstant.charAt(0) == '+' || intConstant.charAt(0) == '-')
                && PIF.size() > 0
                && ( PIF.get(PIF.size() - 1).getFirst().equals("const") || PIF.get(PIF.size() - 1).getFirst().equals("identifier"))) {
            return false;
        }
        index += intConstant.length();
        try {
            position = symbolTable.addIntConst(Integer.parseInt(intConstant));
        } catch (Exception e) {
            position = symbolTable.getIntConstPos(Integer.parseInt(intConstant));
        }
        PIF.add(new Pair<>("int const", position));
        return true;

    }

    private boolean checkIfValid(String possibleIdentifier, String programSubstring) {
        if (reservedWords.contains(possibleIdentifier)) {
            return false;
        }
        if(PIF.size() != 0){
            if (PIF.get(PIF.size()-1).getFirst().equals("i::") || PIF.get(PIF.size()-1).getFirst().equals("s::")) {
                return true;
            }
        }else return false;
        return symbolTable.hasIdentifier(possibleIdentifier);
    }

    private boolean caseIdentifier() {
//        var regexForIdentifier = Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)");
//        var matcher = regexForIdentifier.matcher(program.substring(index));
//        if (!matcher.find()) {
//            return false;
//        }
        var fa = new FA("./src/Resources/identifier.in");
        var identifier = fa.getNextAccepted(program.substring(index));
        if(identifier == null){
            return false;
        }
        if (!checkIfValid(identifier, program.substring(index))) {
            return false;
        }
        index += identifier.length();
        Pair<Integer, Integer> position;
        try {
            position = symbolTable.addIdentifier(identifier);
        } catch (Exception e) {
            position = symbolTable.getIdentifierPos(identifier);
        }
        PIF.add(new Pair<>("identifier", position));
        return true;
    }

    private boolean caseFromTokenList() {
        String possibleToken = program.substring(index).split(" ")[0];
        for (var reservedToken: reservedWords) {
            if (possibleToken.startsWith(reservedToken)) {
                var regex = "^" + "[a-zA-Z0-9_]*" + reservedToken + "[a-zA-Z0-9_]+";
                if (Pattern.compile(regex).matcher(possibleToken).find()) {
                    return false;
                }
                index += reservedToken.length();
                PIF.add(new Pair<>(reservedToken, new Pair<>(-1, -1)));
                return true;
            }
        }
        for (var token : tokens) {
            if (Objects.equals(token, possibleToken)) {
                index += token.length();
                PIF.add(new Pair<>(token, new Pair<>(-1, -1)));

                return true;
            }
            else if (possibleToken.startsWith(token)) {
                index += token.length();
                PIF.add(new Pair<>(token, new Pair<>(-1, -1)));

                return true;
            }
        }
        return false;
    }

    private void nextToken() {
        skipSpaces();
        if (index == program.length()) {
            return;
        }
        if (caseIdentifier()) {
            return;
        }
        if (treatStringConstant()) {
            return;
        }
        if (caseIntConstant()) {
            return;
        }
        if (caseFromTokenList()) {
            return;
        }
        throw new RuntimeException("Lexical error: invalid token at line " + currentLine + ", index " + index);
    }

    public void scan(String program){
        try {
            Path file = Path.of("src/Resources/"+program);
            setProgram(Files.readString(file));
            symbolTable = new SymbolTable(47);
            currentLine = 1;
            while (index < this.program.length()) {
                nextToken();
            }
            FileWriter fileWriter = new FileWriter("PIF" + program.replace(".txt", ".out"));
            for (var pair : PIF) {
                fileWriter.write(pair.getFirst() + " -> (" + pair.getSecond().getFirst() + ", " + pair.getSecond().getSecond() + ")\n\n");
            }
            fileWriter.close();
            fileWriter = new FileWriter("ST" + program.replace(".txt", ".out"));
            fileWriter.write(symbolTable.toString());
            fileWriter.close();
            System.out.println("Lexically correct");
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}

    


