package ast;

import types.Table;

public class Tables extends ExpressionNode {
    private final Table val;

    public Tables(Table val) {
        this.val = val;
    }

    public Table getVal() {
        return this.val;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        return v.visitTables(context, this);
    }
}
