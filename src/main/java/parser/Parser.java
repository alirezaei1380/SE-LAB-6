package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

import Log.Log;
import codeGenerator.CodeGenerator;
import errorHandler.ErrorHandler;
import parser.actionHandler.*;
import scanner.lexicalAnalyzer;
import scanner.token.Token;

public class Parser {
    private ArrayList<Rule> rules;
    private Stack<Integer> parsStack;
    private ParseTable parseTable;
    private lexicalAnalyzer lexicalAnalyzer;
    private CodeGenerator cg;
    private Boolean finish = false;

    private Token lookAhead;

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public void setLookAhead(Token lookAhead) {
        this.lookAhead = lookAhead;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public Stack<Integer> getParsStack() {
        return parsStack;
    }

    public CodeGenerator getCg() {
        return cg;
    }

    public Token getLookAhead() {
        return lookAhead;
    }

    public scanner.lexicalAnalyzer getLexicalAnalyzer() {
        return lexicalAnalyzer;
    }

    public Parser() {
        parsStack = new Stack<Integer>();
        parsStack.push(0);
        try {
            parseTable = new ParseTable(Files.readAllLines(Paths.get("src/main/resources/parseTable")).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rules = new ArrayList<Rule>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get("src/main/resources/Rules"))) {
                rules.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cg = new CodeGenerator();
    }

    public void startParse(java.util.Scanner sc) {
        lexicalAnalyzer = new lexicalAnalyzer(sc);
        lookAhead = lexicalAnalyzer.getNextToken();
        Action currentAction;
        ActionHandler actHandler;
        while (!finish) {
            try {
                Log.print(/*"lookahead : "+*/ lookAhead.toString() + "\t" + parsStack.peek());
//                Log.print("state : "+ parsStack.peek());
                currentAction = parseTable.getActionTable(parsStack.peek(), lookAhead);
                Log.print(currentAction.toString());
                //Log.print("");
                if(currentAction.action == act.shift)
                    actHandler = new ActionShift(this);
                else if(currentAction.action == act.reduce)
                    actHandler = new ActionReduce(this);
                else
                    actHandler = new ActionAccept(this);

                actHandler.execute(currentAction);
                Log.print("");
            } catch (Exception ignored) {
                ignored.printStackTrace();
//                boolean find = false;
//                for (NonTerminal t : NonTerminal.values()) {
//                    if (parseTable.getGotoTable(parsStack.peek(), t) != -1) {
//                        find = true;
//                        parsStack.push(parseTable.getGotoTable(parsStack.peek(), t));
//                        StringBuilder tokenFollow = new StringBuilder();
//                        tokenFollow.append(String.format("|(?<%s>%s)", t.name(), t.pattern));
//                        Matcher matcher = Pattern.compile(tokenFollow.substring(1)).matcher(lookAhead.toString());
//                        while (!matcher.find()) {
//                            lookAhead = lexicalAnalyzer.getNextToken();
//                        }
//                    }
//                }
//                if (!find)
//                    parsStack.pop();
            }
        }
        if (!ErrorHandler.hasError) cg.printMemory();
    }
}
