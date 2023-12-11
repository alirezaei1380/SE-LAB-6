package parser.actionHandler;

import parser.Action;
import parser.Parser;

public class ActionShift implements ActionHandler{

    Parser parser;
    public ActionShift(Parser parser){
        this.parser = parser;
    }

    @Override
    public void execute(Action action) {
        parser.getParsStack().push(action.number);
        parser.setLookAhead(parser.getLexicalAnalyzer().getNextToken());
    }
}
