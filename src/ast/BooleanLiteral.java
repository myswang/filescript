package ast;

public class BooleanLiteral extends ExpressionNode {
    private final boolean val;

    public BooleanLiteral(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return this.val;
    }

    @Override
    public <C, T> T accept(C context, FileScriptVisitor<C, T> v) {
        return v.visitBooleanLiteral(context, this);
    }
}
