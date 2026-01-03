/*
 * This file was adapted from the tinyVars repository provided in class.
 * Original source: https://github.students.cs.ubc.ca/CPSC410-2024W-T2/tinyVars
 * Modifications have been made to implement the FileScript DSL.
 */

package ui;

import ast.*;
import ast.evaluators.Evaluator;
import interpreter.ProgramState;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import parser.FileScriptLexer;
import parser.FileScriptParser;
import parser.ParseTreeToASTVisitor;
import parser.ThrowingErrorListener;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("[Error] No input FileScript file provided");
            System.err.println("[Usage] java ui.Main <file>");
            System.exit(1);
        }

        FileScriptLexer lexer = null;
        try {
            lexer = new FileScriptLexer(CharStreams.fromFileName(args[0]));
        } catch (NoSuchFileException e) {
            System.err.println("[Error] Could not find provided FileScript file");
            System.exit(1);
        }

        lexer.reset();
        TokenStream tokens = new CommonTokenStream(lexer);

        FileScriptParser parser = new FileScriptParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        FileScriptParser.ProgramContext ctx = null;
        try {
            ctx = parser.program();
        } catch (ParseCancellationException e) {
            System.err.println("[Parse Error] " + e.getMessage());
            System.exit(1);
        }

        ParseTreeToASTVisitor visitor = new ParseTreeToASTVisitor();
        Program parsedProgram = visitor.visitProgram(ctx);

        Evaluator eval = new Evaluator();
        ProgramState context = new ProgramState();
        parsedProgram.accept(context, eval);
    }
}
