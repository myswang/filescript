/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import helpers.ErrorHandler;

import java.util.List;

public class If extends StatementNode {
    private final ExpressionNode condition;
    private final List<StatementNode> then;
    private final List<StatementNode> else_;

    public If(ExpressionNode condition, List<StatementNode> then, List<StatementNode> else_) {
        this.condition = condition;
        this.then = then;
        this.else_ = else_;
    }

    public ExpressionNode getCondition() {
        return this.condition;
    }

    public List<StatementNode> getThen() {
        return this.then;
    }

    public List<StatementNode> getElse_() {
        return this.else_;
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
