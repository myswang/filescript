/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import helpers.ErrorHandler;

import java.util.List;

public class While extends StatementNode {
    private final ExpressionNode condition;
    private final List<StatementNode> do_;

    public While(ExpressionNode condition, List<StatementNode> do_) {
        this.condition = condition;
        this.do_ = do_;
    }

    public ExpressionNode getCondition() {
        return this.condition;
    }

    public List<StatementNode> getDo_() {
        return this.do_;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        try {
            return v.visit(context, this);
        } catch (FileScriptException e) {
            ErrorHandler.handle(e);
            return null;
        }
    }
}
