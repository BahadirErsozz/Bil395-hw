import java.io.FileWriter;

public class hw2_Bahadir_Ersoz {
    // storing the output
    static String toWriteOnFile = "";
    // storing the stack
    static String stack = "";
    // output file name
    static String outFileName = "";
    // storing input
    static String input = "";
    // lr Parsing table
    static String[][] lrTable = {{"S5",null,null,"S4",null,null,"1","2","3"},
                                 {null,"S6",null,null,null,"accept",null,null,null},
                                 {null,"R2","S7",null,"R2","R2",null,null,null},
                                 {null,"R4","R4",null,"R4","R4",null,null,null},
                                 {"S5",null,null,"S4",null,null,"8","2","3"},
                                 {null,"R6","R6",null,"R6","R6",null,null,null},
                                 {"S5",null,null,"S4",null,null,null,"9","3"},
                                 {"S5",null,null,"S4",null,null,null,null,"10"},
                                 {null,"S6",null,null,"S11",null,null,null,null},
                                 {null,"R1","S7",null,"R1","R1",null,null,null},
                                 {null,"R3","R3",null,"R3","R3",null,null,null},
                                 {null,"R5","R5",null,"R5","R5",null,null,null}};

    public static void main(String[] args) {
        // getting the args
        input = args[0];
        outFileName = args[1];
        // the stack starts with 0
        stack = "0";
        // adjusting the output
        toWriteOnFile = toWriteOnFile.concat("Stack\t\t\t\t\t\t  " + "Input\t\t\t\t\t\t\t" + "Action\n");
        // until the program ends with either an error or accept
        while (true)
            // the main method that computes
            readCurrent();
    }
    // gives you the current element on input
    public static String getCurrent(){
        if(input.indexOf("id") == 0)
            return "id";
        return "" + input.charAt(0);
    }
    // gives you the current element on input and removes it
    public static String removeCurrent(){
        if(input.indexOf("id") == 0){
            input =input.substring(2);
            return "id";
        }
        char first = input.charAt(0);
        input = input.substring(1);
        return "" + first;
    }
    // adjusts the printing format
    public static void printCurrent(){
        toWriteOnFile = toWriteOnFile.concat(stack);
        for(int i = 0; i < 30 - stack.length(); i++)
            toWriteOnFile = toWriteOnFile.concat(" ");
        toWriteOnFile = toWriteOnFile.concat(input);
        for(int i = 0; i < 30 - input.length(); i++)
            toWriteOnFile = toWriteOnFile.concat(" ");
    }
    // reads the current instruction and calls the necessarry functions
    public static void readCurrent(){
        printCurrent();
        // getting the row index of the LR Parsing Table
        int row;
        if(stack.length() > 2 && stack.charAt(stack.length()-2) == '1')
            row = Integer.parseInt(stack.substring(stack.length() - 2));
        else
            row = Integer.parseInt(stack.charAt(stack.length() - 1) + "");

        // getting the column. GOTO's are taken care of in the reduce function
        int col = switch (getCurrent()) {
            case "id" -> 0;
            case "+" -> 1;
            case "*" -> 2;
            case "(" -> 3;
            case ")" -> 4;
            case "$" -> 5;
            default -> 0;
        };
        // getting the element from the table
        String current = lrTable[row][col];

        // if its an empty element
        if(current == null){
            System.out.print("Error occurred.");
            toWriteOnFile = toWriteOnFile.concat("ERROR");
            try{
                FileWriter fw = new FileWriter(outFileName);
                fw.write(toWriteOnFile);
                fw.close();
            }catch(Exception e){System.out.println(e);}
            System.exit(0);
        }
        // if the action is Shift
        if(current.contains("S")) {
            shift(Integer.parseInt(current.substring(1)));
            toWriteOnFile = toWriteOnFile.concat("Shift " + current.substring(1) + "\n");
        }
        // if the action is Reduce
        if(current.contains("R")) {
            reduce(Integer.parseInt(current.substring(1)));
            toWriteOnFile = toWriteOnFile.concat("Reduce " + current.substring(1) + "\n");
        }
        // if the action is accept
        if(current.equals("accept")){
            System.out.print("The input has been parsed successfully.");
            toWriteOnFile = toWriteOnFile.concat("Accept");
            try{
                FileWriter fw = new FileWriter(outFileName);
                fw.write(toWriteOnFile);
                fw.close();
            }catch(Exception e){System.out.println(e);}
            System.exit(0);
            System.exit(0);

        }

    }// does shifting and calls removeCurrent to update input
    public static void shift(int i){
        stack = stack.concat(removeCurrent() + i);
    }
    // does reducing
    public static void reduce(int i){
        // all the rules
        if(i == 1){
            stack = stack.substring(0,stack.lastIndexOf("E"));
            stack = stack.concat("E" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][6]);
        }
        if(i == 2){
            stack = stack.substring(0,stack.lastIndexOf("T"));
            stack = stack.concat("E" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][6]);
        }
        if(i == 3){
            stack = stack.substring(0,stack.lastIndexOf("T"));
            stack = stack.concat("T" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][7]);
        }
        if(i == 4){
            stack = stack.substring(0,stack.lastIndexOf("F"));
            stack = stack.concat("T" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][7]);
        }
        if(i == 5){
            stack = stack.substring(0,stack.lastIndexOf("("));
            stack = stack.concat("F" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][8]);
        }
        if(i == 6){
            stack = stack.substring(0,stack.lastIndexOf("id"));
            stack = stack.concat("F" + lrTable[Integer.parseInt(stack.charAt(stack.length() - 1) + "")][8]);
        }

    }
}
