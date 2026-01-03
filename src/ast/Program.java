/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import java.util.List;

public class Program extends Node {
    private final List<StatementNode> stmts;

    public Program(List<StatementNode> stmts) {
        this.stmts = stmts;
    }

    public List<StatementNode> getStmts() {
        return this.stmts;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        return v.visit(context, this);
    }
}
