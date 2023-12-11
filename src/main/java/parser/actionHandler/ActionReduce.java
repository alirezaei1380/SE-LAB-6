package parser.actionHandler;

import Log.Log;
import parser.Action;
import parser.Parser;
import parser.Rule;

public class ActionReduce implements ActionHandler{
    Parser parser;
    public ActionReduce(Parser parser){
        this.parser = parser;
    }
    @Override
    public void execute(Action action) {
        Rule rule = parser.getRules().get(action.number);
        for (int i = 0; i < rule.RHS.size(); i++) {
            parser.getParsStack().pop();
        }

        Log.print(/*"state : " +*/ parser.getParsStack().peek() + "\t" + rule.LHS);
//                        Log.print("LHS : "+rule.LHS);
        parser.getParsStack().push(parser.getParseTable().getGotoTable(parser.getParsStack().peek(), rule.LHS));
        Log.print(/*"new State : " + */parser.getParsStack().peek() + "");
//                        Log.print("");
        try {
            parser.getCg().semanticFunction(rule.semanticAction, parser.getLookAhead());
        } catch (Exception e) {
            Log.print("Code Genetator Error");
        }
    }
}
