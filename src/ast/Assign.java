/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import helpers.ErrorHandler;

public class Assign extends StatementNode {
    private final IdentifierName varname;
    private final ExpressionNode exp;

    public Assign(IdentifierName varname, ExpressionNode exp) {
        this.varname = varname;
        this.exp = exp;
    }

    public IdentifierName getVarname() {
        return this.varname;
    }

    public ExpressionNode getExp() {
        return this.exp;
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
