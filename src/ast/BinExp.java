/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptRuntimeException;
import helpers.ErrorHandler;

public class BinExp extends ExpressionNode {
    private final String op;
    private final ExpressionNode left;
    private final ExpressionNode right;

    public BinExp(ExpressionNode left, String op, ExpressionNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public String getOp() {
        return this.op;
    }

    public ExpressionNode getLeft() {
        return this.left;
    }

    public ExpressionNode getRight() {
        return this.right;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        try {
            return v.visitBinExp(context, this);
        } catch (FileScriptRuntimeException e) {
            // Display error message to user
            ErrorHandler.handle(e);
        }
        return null;
    }
}