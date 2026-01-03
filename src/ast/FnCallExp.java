/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import helpers.ErrorHandler;

import java.util.List;

public class FnCallExp extends ExpressionNode {
    private final IdentifierName fnName;
    private final List<ExpressionNode> args;

    public FnCallExp(IdentifierName fnName, List<ExpressionNode> args) {
        this.fnName = fnName;
        this.args = args;
    }

    public IdentifierName getFnName() {
        return this.fnName;
    }

    public List<ExpressionNode> getArgs() {
        return this.args;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        try {
            return v.visitFnCall(context, this);
        } catch (FileScriptException e) {
            ErrorHandler.handle(e);
        }
        return null;
    }
}
