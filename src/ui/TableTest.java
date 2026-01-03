// COMMENTED OUT FOR NOW SINCE THERE WAS A LOT OF ISSUES WITH THE VALUENODE REPRESENTATION

//package ui;
//
//import ast.ValueNode;
//import types.FileScriptType;
//import types.FileScriptTypeContainer;
//import types.Table;
//
////import static interpreter.Dispatch.open;
//
//public class TableTest {
//
//    public static FileScriptTypeContainer makeStudent(String name, int age, boolean isStudent) {
//        Table student = new Table();
//        student.put("name", new ValueNode(FileScriptType.STRING, name));
//        student.put("age", new ValueNode(FileScriptType.INTEGER, age));
//        student.put("is_student", new ValueNode(FileScriptType.BOOLEAN, isStudent));
//
//        FileScriptTypeContainer studentContainer = new FileScriptTypeContainer(FileScriptType.TABLE);
//        studentContainer.setValue(student);
//
//        return studentContainer;
//    }
//
//    public static void main(String[] args) {
//        Table students = new Table();
//        students.push(makeStudent("Alice", 22, true));
//        students.push(makeStudent("Bob", 19, true));
//        students.push(makeStudent("Carl", 45, false));
//
//        while (students.len() > 0) {
//            Table student = (Table) students.pop().getValue();
//            System.out.println(student.get("name").getValue());
//            System.out.println(student.get("age").getValue());
//            System.out.println(student.get("is_student").getValue());
//        }
//
//        System.out.println("Length of students array now: " + students.len());
//
//        // TODO: come back and fix this with the new method dispatch code
////        Table res = open("src/ast");
////        System.out.println(res.get("name").getValue());
////        System.out.println(res.get("size").getValue());
////        System.out.println(res.get("last_modified").getValue());
////        System.out.println(res.get("is_dir").getValue());
////        Table subs = (Table) res.get("subs").getValue();
////        for (int i = 0; i < subs.len(); i++) {
////            System.out.println(subs.get(i).getValue());
////        }
//    }
//}
