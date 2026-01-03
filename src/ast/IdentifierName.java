/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import helpers.ErrorHandler;

public class IdentifierName extends ExpressionNode {
    private final String val;
    private final boolean indexedVariable;
    private final ExpressionNode index;

    public IdentifierName(String val) {
        this.val = val;
        index = null;
        indexedVariable = false;
    }

    public <T> IdentifierName(String val, ExpressionNode index) {
        this.val = val;
        this.index = index;
        indexedVariable = true;
    }

    public String getVal() {
        return this.val;
    }

    public ExpressionNode getIndex() {
        return this.index;
    }

    public boolean isIndexedVariable() {
        return this.indexedVariable;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        try {
            return v.visitIdentifierName(context, this);
        } catch (FileScriptException e) {
            ErrorHandler.handle(e);
        }
        return null;
    }
}
