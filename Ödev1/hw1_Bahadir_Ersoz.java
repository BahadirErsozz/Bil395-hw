import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class hw1_Bahadir_Ersoz {
    static Map<String, String> lexemeToToken = new HashMap<String, String>();
    static FileWriter writer;
    static String outFile;
    public static void main(String[] args) {
        intializeMap();
        String inpFile = args[0];
        outFile = args[1];

        try{
            writer = new FileWriter(outFile);
            Scanner reader = new Scanner(new File(inpFile));
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                for(String str : line.split(" "))
                    properReader(str);
            }
            writer.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Input file does not exist");
            System.exit(1);
        }
        catch (IOException e){
            System.out.println("an error ocured");
            System.exit(1);
        }

    }
    public static void intializeMap(){
        lexemeToToken.put("for", "FOR_STATEMENT");
        lexemeToToken.put("(", "LPARANT");
        lexemeToToken.put(")", "RPARANT");
        lexemeToToken.put("int", "INT_TYPE");
        lexemeToToken.put("char", "CHAR_TYPE");
        lexemeToToken.put("=", "ASSIGNM");
        lexemeToToken.put(";", "SEMICOLON");
        lexemeToToken.put(">", "GREATER");
        lexemeToToken.put("<", "LESS");
        lexemeToToken.put(">=", "GRE_EQ");
        lexemeToToken.put("<=", "LESS_EQ");
        lexemeToToken.put("{", "LCURLYB");
        lexemeToToken.put("}", "RCURLYB");
        lexemeToToken.put("return", "RETURN_STMT");
        lexemeToToken.put("-", "SUBT");
        lexemeToToken.put("/", "DIV");
        lexemeToToken.put("*", "MULT");
        lexemeToToken.put("+", "ADD");
    }
    public static void properReader(String line){
        String readingCurrent = "";

        if(line.length() == 0)
            return;
        while(line.length() > 0 && line.charAt(0) != '(' &&
                line.charAt(0) != ')' && line.charAt(0) != '{'
                && line.charAt(0) != '}' && line.charAt(0) != ';'
                && line.charAt(0) != '=' && line.charAt(0) != '+'
                && line.charAt(0) != '-' && line.charAt(0) != '/'
                && line.charAt(0) != '*'){
            readingCurrent = readingCurrent.concat(line.charAt(0) + "");
            line = line.substring(1);

        }
        checkForLexeme(readingCurrent);
        if (line.length() > 0) {
            checkForLexeme(line.charAt(0) + "");
            line = line.substring(1);
        }
        properReader(line);

    }
    public static void checkForLexeme(String str){
        String token = lexemeToToken.get(str);
        String identifier = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        try {



            if (token != null) {
                //      if((str.contains("(") || str.contains(")") || str.contains("{") || str.contains("}")) && str.length() > 1)

                writer.write("Next token is " + token);

                for (int i = 0; i < 20 - token.length(); i++)
                    writer.write(" ");
                writer.write("Next lexeme is " + str + "\n");
                return;
            } else if (str.length() > 0) {
                if (str.length() == 1) {
                    if (identifier.contains(str))
                        writer.write("Next token is identifier          Next lexeme is " + str + "\n");
                    else if (!isInt(str)) {
                        writer.write("Unknown operator exception: " + str + " is unknown");
                        writer.close();
                        System.exit(1);
                    }
                    return;
                }
                if (isInt(str))
                    writer.write("Next token is INT_LIT             Next lexeme is " + str + "\n");
                else {
                    writer.write("Unknown Identifier exception: " + str + " is unknown");
                    writer.close();
                    System.exit(1);
                }
            }

        }

        catch (IOException e){
            System.out.println("an error occured");
            System.exit(1);
        }


    }
    public static boolean isInt(String str){
            try {
                int a = Integer.parseInt(str);
            }
            catch (Exception e){
                return false;
            }
        return true;
    }
}
