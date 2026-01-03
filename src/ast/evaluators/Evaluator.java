/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast.evaluators;

import ast.*;
import ast.BooleanLiteral;
import ast.Number;
import exceptions.FileScriptException;
import exceptions.FileScriptRuntimeException;
import interpreter.ProgramState;
import interpreter.TypeChecker;
import types.FileScriptValue;
import types.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Evaluator implements FileScriptVisitor<ProgramState, FileScriptValue<?>> {
    @Override
    public FileScriptValue<?> visit(ProgramState state, Program p) {
        for (StatementNode node : p.getStmts()) {
            node.accept(state, this);
        }
        return null;
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, Assign a) throws FileScriptRuntimeException {
        IdentifierName var = a.getVarname();
        String varName = var.getVal();

        FileScriptValue<?> value = a.getExp().accept(state, this);

        if (!var.isIndexedVariable()) {
            // Set the variable directly (not an array reference)
            state.setVariable(varName, value);
        } else {
            ExpressionNode key = var.getIndex();
            if (key != null) {
                FileScriptValue<?> evaluatedKey = key.accept(state, this);
                if (evaluatedKey != null) {
                    state.setVariableArrayIndex(varName, evaluatedKey, value);
                }
            }
        }

        // Returns null because it is an assignment/statement
        return null;
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, If i) throws FileScriptException {
        FileScriptValue<?> conditionEvaluated = i.getCondition().accept(state, this);
        boolean condition;
        if (TypeChecker.isType(conditionEvaluated, Boolean.class)) {
            condition = TypeChecker.unwrap(conditionEvaluated, Boolean.class);
        } else {
            // Cast to integer and check if it is not equal to 0 regardless
            condition = TypeChecker.unwrapAndTypeCheck(conditionEvaluated, Integer.class) != 0;
        }
        List<StatementNode> branch = condition ? i.getThen() : i.getElse_();

        for (StatementNode stmt : branch) {
            stmt.accept(state, this);
        }
        return null;
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, While w) throws FileScriptException {
        while (true) {
            FileScriptValue<?> conditionEvaluated = w.getCondition().accept(state, this);
            boolean condition;
            if (TypeChecker.isType(conditionEvaluated, Boolean.class)) {
                condition = TypeChecker.unwrap(conditionEvaluated, Boolean.class);
            } else {
                // Cast to integer and check if it is not equal to 0 regardless
                condition = TypeChecker.unwrapAndTypeCheck(conditionEvaluated, Integer.class) != 0;
            }

            if (!condition) {
                break;
            }

            for (StatementNode stmt : w.getDo_()) {
                stmt.accept(state, this);
            }
        }
        return null;
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, FnCall f) throws FileScriptException {
        // TODO: see if there is a way to abstract this code with "visitFnCall" and ensure that
        // the return type is null for "statements" versus "expressions" which may return a value

        // For now, note that any changes made here most likely needs to be made in visitFnCall unless
        // it surrounds logic around return values and statements vs expressions
        List<FileScriptValue<?>> argValues = new ArrayList<>();
        for (ExpressionNode argExpr : f.getArgs()) {
            // Evaluate every arg to a FnCall
            FileScriptValue<?> argValue = argExpr.accept(state, this);
            if (argValue != null && argValue.getValue() != null) {
                argValues.add(argValue);
            } else {
                // If a user tries to pass in an argument as an array index that is out of bounds
                // this is a fail-safe exception to inform that argument is not existent
                throw new FileScriptRuntimeException("Argument to " + f.getFnName().getVal() + " could not be found. Check that the variable (and index) has an assigned value.");
            }
        }
        String fnName = f.getFnName().getVal();
        state.runFunction(fnName, argValues);
        return null;
    }

    private boolean compStrings(String string, String pattern) {
        if (Objects.equals(pattern, "*") || Objects.equals(pattern, "**")) {
            return true;
        } else if (pattern.length() > 1 && pattern.charAt(0) == '*' && pattern.charAt(pattern.length()-1) == '*') {
            return string.contains(pattern.substring(1, pattern.length() - 1));
        } else if (!pattern.isEmpty() && pattern.charAt(0) == '*') {
            return string.endsWith(pattern.substring(1));
        } else if (!pattern.isEmpty() && pattern.charAt(pattern.length()-1) == '*') {
            return string.startsWith(pattern.substring(0, pattern.length() - 1));
        } else {
            return Objects.equals(string, pattern);
        }
    }

    private FileScriptValue<?> handleIntegerOp(int left, int right, String op) throws FileScriptRuntimeException {
        return switch (op) {
            case "+" -> new FileScriptValue<>(left + right);
            case "-" -> new FileScriptValue<>(left - right);
            case "*" -> new FileScriptValue<>(left * right);
            case "/" -> {
                if (right == 0) throw new FileScriptRuntimeException("Division by 0");
                yield new FileScriptValue<>(left / right);
            }
            case ">" -> new FileScriptValue<>(left > right);
            case "<" -> new FileScriptValue<>(left < right);
            case ">=" -> new FileScriptValue<>(left >= right);
            case "<=" -> new FileScriptValue<>(left <= right);
            case "==" -> new FileScriptValue<>(left == right);
            case "!=" -> new FileScriptValue<>(left != right);
            default -> throw new FileScriptRuntimeException("Invalid integer operator");
        };
    }

    private FileScriptValue<?> handleStringOp(String left, String right, String op) throws FileScriptRuntimeException {
        return switch (op) {
            case "==" -> new FileScriptValue<>(compStrings(left, right));
            case "!=" -> new FileScriptValue<>(!compStrings(left, right));
            default -> throw new FileScriptRuntimeException("Invalid string operator: " + op);
        };
    }

    private FileScriptValue<?> handleBooleanOp(Boolean left, Boolean right, String op) throws FileScriptRuntimeException {
        return switch (op) {
            case "==" -> new FileScriptValue<>(left.equals(right));
            case "!=" -> new FileScriptValue<>(!left.equals(right));
            case "&&" -> new FileScriptValue<>(left && right);
            case "||" -> new FileScriptValue<>(left || right);
            default -> throw new FileScriptRuntimeException("Invalid boolean operator: " + op);
        };
    }

    @Override
    public FileScriptValue<?> visitBinExp(ProgramState state, BinExp b) throws FileScriptRuntimeException {
        FileScriptValue<?> leftEvaluated = b.getLeft().accept(state, this);
        FileScriptValue<?> rightEvaluated = b.getRight().accept(state, this);
        String op = b.getOp();

        if (TypeChecker.isType(leftEvaluated, Integer.class) && TypeChecker.isType(rightEvaluated, Integer.class)) {
            int left = TypeChecker.unwrap(leftEvaluated, Integer.class);
            int right = TypeChecker.unwrap(rightEvaluated, Integer.class);
            return handleIntegerOp(left, right, op);
        } else if (TypeChecker.isType(leftEvaluated, String.class) && TypeChecker.isType(rightEvaluated, String.class)) {
            String left = TypeChecker.unwrap(leftEvaluated, String.class);
            String right = TypeChecker.unwrap(rightEvaluated, String.class);
            return handleStringOp(left, right, op);
        } else if (TypeChecker.isType(leftEvaluated, Boolean.class) && TypeChecker.isType(rightEvaluated, Boolean.class)) {
            Boolean left = TypeChecker.unwrap(leftEvaluated, Boolean.class);
            Boolean right = TypeChecker.unwrap(rightEvaluated, Boolean.class);
            return handleBooleanOp(left, right, op);
        } else {
            throw new FileScriptRuntimeException("Mismatched or unsupported types in binary expression");
        }
    }

    @Override
    public FileScriptValue<?> visitIdentifierName(ProgramState state, IdentifierName n) throws FileScriptRuntimeException {
        String varName = n.getVal();
        if (!n.isIndexedVariable()) {
            return state.getVariable(varName);
        } else {
            FileScriptValue<?> index = n.getIndex().accept(state, this);
            return state.getVariableArrayIndex(varName, index);
        }
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, Number n) {
        // Create a new value type as the result of interpreting a number to itself
        return new FileScriptValue<>(n.getVal());
    }

    @Override
    public FileScriptValue<?> visitText(ProgramState state, Text t) {
        return new FileScriptValue<>(t.getVal());
    }

    @Override
    public FileScriptValue<?> visitBooleanLiteral(ProgramState state, BooleanLiteral b) {
        return new FileScriptValue<>(b.getVal());
    }

    @Override
    public FileScriptValue<?> visitFnCall(ProgramState state, FnCallExp f) throws FileScriptException {
        List<FileScriptValue<?>> argValues = new ArrayList<>();
        for (ExpressionNode argExpr : f.getArgs()) {
            // Evaluate every arg to a FnCall
            FileScriptValue<?> argValue = argExpr.accept(state, this);
            if (argValue != null && argValue.getValue() != null) {
                argValues.add(argValue);
            } else {
                // If a user tries to pass in an argument as an array index that is out of bounds
                // this is a fail-safe exception to inform that argument is not existent
                throw new FileScriptRuntimeException("Argument to " + f.getFnName().getVal() + " could not be found. Check that the variable (and index) has an assigned value.");
            }
        }
        String fnName = f.getFnName().getVal();
        return state.runFunction(fnName, argValues);
    }

    @Override
    public FileScriptValue<?> visit(ProgramState state, Index i) {
        return i.getIdx().accept(state, this);
    }

    @Override
    public FileScriptValue<?> visitTables(ProgramState state, Tables table) {
        // Create a new empty table when {} is encountered in the program
        return new FileScriptValue<>(new Table());
    }
}
