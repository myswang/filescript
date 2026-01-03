/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ast;

import exceptions.FileScriptException;
import exceptions.FileScriptRuntimeException;

public interface FileScriptVisitor<C, T> {
    T visit(C context, Program p);

    T visit(C context, Assign a) throws FileScriptRuntimeException;

    T visit(C context, If i) throws FileScriptException;

    T visit(C context, While w) throws FileScriptException;

    T visit(C context, FnCall f) throws FileScriptException;

    T visitBinExp(C context, BinExp b) throws FileScriptRuntimeException;

    T visitIdentifierName(C context, IdentifierName n) throws FileScriptRuntimeException;

    T visit(C context, Number n);

    T visitText(C context, Text t);

    T visitFnCall(C context, FnCallExp f) throws FileScriptException;

    T visit(C context, Index i);

    T visitTables(C context, Tables table);

    T visitBooleanLiteral(C context, BooleanLiteral b);
}
