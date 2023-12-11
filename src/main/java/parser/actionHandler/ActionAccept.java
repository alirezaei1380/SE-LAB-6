package parser.actionHandler;

import parser.Action;
import parser.Parser;

public class ActionAccept implements ActionHandler{
    Parser parser;

    public ActionAccept(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void execute(Action action) {
        this.parser.setFinish(true);
    }
}
