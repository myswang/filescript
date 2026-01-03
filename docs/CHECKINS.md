# Group 4 Check-Ins

## Table of contents
- [Check-In 1 Report](#check-in-1-report)
- [Check-In 2 Report](#check-in-2-report)
- [Check-In 3 Report](#check-in-3-report)
- [Check-In 4 Report](#check-in-4-report)
- [Check-In 5 Report](#check-in-5-report)

## Check-In 1 Report

### Planned DSL Description

The primary purpose of our Domain-Specific Language (DSL) is to assist those with an interest in databases and programming with file searching tasks. It is designed to enable granular and conditional searches through file metadata and return aggregated results.

Part of the inspiration for the syntax comes from SQL with a combination of scripting languages like Bash and Lua.

---

### Rich Features

#### 1. Mutable Variables
- **Description**: Allows storing and updating values (like counts, sums) during runtime.
- **Example**:
    ```
    // SIZE(file) returns the file size
    count = 0
    sum_size = 0
    count = count + 1
    sum_size = sum_size + SIZE(file)
    ```

#### 2. Conditionals
- **Description**: Allows filtering based on specific criteria, such as file size, name, or extension.
- **Example**:
    ```
    // NAME(file) returns the filename
    IF SIZE(file) > 1000000 THEN
        PRINT("Large file detected: ", NAME(file))
    END
    ```

#### 3. Loops
- **Description**: Allows iterating through files and applying operations like counting or aggregation.
- **Example**:
    ```
    // DIR("/home/foo") returns a list of files in the directory
    FOR file IN DIR("/home/foo") DO
        PRINT("File Name: ", NAME(file))
    END
    ```

---

### Feature Interactions

By combining mutable variables, conditionals, and loops, users can perform complex file operations, such as counting files meeting specific criteria or finding the largest file in a directory.

#### Example 1: Counting Large Files

```
count = 0
FOR file IN DIR("/home/foo") DO
	IF SIZE(file) > 1000000 THEN
	    count = count + 1
    END
END
PRINT("Number of large files:", count)
```

#### Example 2: Finding the Largest File
```
largest_name = ""
largest_size = 0
FOR file IN DIR("/home/foo") DO
	IF SIZE(file) > largest_size THEN
		largest_name = NAME(file)
		largest_size = SIZE(file)
	END
END
PRINT("Largest file:", largest_name, "with size", largest_size)
```

---

### TA Feedback Integration
1. We have been informed that our proposed DSL idea should be able to meet the project requirements.
2. While our SQL-like syntax was also promising, we ultimately chose a more imperative style for its simplicity.
3. We are working on making loops more general for broader use cases.
4. We have made the syntax simpler. 
5. We plan to decide whether optional features like user-defined functions should be added.

---

### Next Steps
1. Finalize the syntax. 
2. Create more example snippets to illustrate features of the DSL. 
3. Determine the necessity for optional features such as functions.
4. Get started on the project 1 proposal.

## Check-In 2 Report

* Planned division of main responsibilities between team members, considering how to enable working in parallel as much as possible. Consider the following points:  
  * *Modular design for the software system: what is the input, output of each component? Who is responsible for each component? Do you want to be jointly responsible for some components?*

| Module | Input | Output | Responsible |
| :---- | :---- | :---- | :---- |
| Lexer | DSL code | Parse Tree | Yingkai & Sunny |
| Parser | Parse Tree | AST | Yingkai & Alan |
| File System Models | Custom File System Object | Custom Metadata Object (mapping to output of interpreter) | Ryan & Mike & Sunny |
| Interpreter | AST | Evaluation Result | Ryan & Mike & Alan |

* *What is the data at each interface point? Are there invariants over the data other than the class structure?*  
  * For now, we cannot think of any invariants that we may incur.   
* *How will you be able to build component X independently? Can you write tests for component X independently of its dependent components?*  
  * By creating separate “transfer objects” we plan to allow each module to take in an independent data type, test it separately with that data type, and then connect the two using a common interface later on when they are ready to be joined together. Therefore, we should be able to build the components independently. Additionally, we can use stub objects while implementing for points of connection.    
* *Who will be responsible for writing which tests, and when (will the same people write the tests as the code)?*  
  * Each team will be responsible for writing their own tests for their respective module, in addition to the implementations.  
  * We will each implement our own sanity tests to verify that our modules are functional, in addition to some E2E/integration tests consisting of sample programs written using our DSL.

Other tasks (subject to change):

| Module | Responsible |
| :---- | :---- |
| User Study 1 | Sunny, Alan, Yingkai |
| User Study 2 | Mike, Ryan |
| Video | All of us |

* Roadmap/timeline(s) for what should be done when, and how you will synchronise/check-in with each other to make sure progress is on-track. Talk clearly with your team members about your expectations for communication and progress, and what you will do as a team if someone falls behind.  
  * We split up the roadmap in one week increments until the project deadline (with checkpoints on every Friday)  
  * January 31: Lexer \+ Parser completed   
  * — user study \#1 –  
  * February 7: Basic features \+ data structures  
  * February 14: Dynamic type checking \+ advanced features (loops, conditionals, etc). Ideally we would be able to perform a DFS on some subfolders by this week (in line with the code example)  
  * — user study \#2 —   
  * — video completed before next Friday deadline —   
  * February 21: Ironing out any details, bugs and adding any unit tests.   
* Summary of progress so far.  
  * So far we have created a proposal for the features that we are hoping to support in our project. We are starting to plan the more technical details around who will complete which parts and what languages we plan to use. We have ironed out all the details with our TA and have a plan going forward.

**Feedback from TA:**

Need simpler examples  
Init list with literal syntax, like list \= \[1,2,3\] is difficult in antler  
Proposal:

1. Static check (may be too advanced)  
2. Dynamic type checking, is it an error  
3. Accept it and implicitly do a conversion

Complex data structures: “We will check bad if operation done to wrong data structure, raise appropriate error”

Division of responsibilities: Antlr grammar, loops, how to split.

Rough timeline for when things will be done. 

- Having it ‘done’ for first user study  
- Some time to say where implementation should be essentially done (for 2nd user study)

Next week: think about language syntax

Don’t have to write tests, but will check error handling. Ensure doesn’t crash in weird way for syntax error. Nothing in rubric in testing, can submit with no tests

## Check-In 3 Report
### Explain a mockup of your concrete language design
#### Stack
```
stack = {}
```
- A stack is initalized with `{}`. In Filescript, a stack is treated as an iterable, allowing loops to iterate over each file in the stack. A stack can only hold files and cannot hold any other type of element. 
```
stack = open("/file/path/")
```
- The open method returns a stack of files. This either overwrites or initializes the variable `stack`
```
push(stack, file)
file = pop(stack)
```
- Push a file onto the (end) of the stack. 
- Pop a file (from the end) of the stack. 
```
push(stack, open("/file/path/"))
```
- Can also push a stack onto the end of another stack. 
#### Quick digression into open
```
open("/foo/main.py")    # returns the file at /foo/main.py
open("/foo/bar")        # returns the file /foo/bar
open("/foo/bar/")       # returns a stack of files in /foo/bar/
```
- In other words, if ending of a path has /, `open` will return the stack of files within that file. Otherwise, `open` will return the file (or directory) at that path (NOT a length 1 stack). 

#### Back to stack
```
length = len(stack)
```
- Returns the length of the stack as an integer
```
file = stack[3]
```
- A stack can also be used as a list/array. This returns the file at index 3 of stack. Indexing starts from 0, obviously. Stacks do not support negative indexes, has to be 0 or positive, and within length of stack. 
#### Other file types
```
an_integer = 1
a_string = "asdf"
nothing = nil
boolean = true
```
- Note the following are not supported: floats/doubles (1.2)
#### Conditionals
```
while <condition> do
   <code>
end 
```
- A while loop is what it is. It checks if <condition> is true. If it is, then \<code> will run. Then it will check again. If <condition> is false, then it will stop. 
```
if <condition> then
  <code>
end
```
- If <condition> is true then \<code> will run. 
- There are no `else` nor `else if` statements. 
#### Comments
```
// for in-line comments
```
- There are no block comments. 

#### Mutable variables
- All variables are mutable. All variables have infinite scope. The following is valid:
```
if 0 == 0 then
  crazy = open("/home/")  // crazy is initalized as a stack of files
  crazy = crazy == nil    // crazy is a boolean (false)
  crazy = crazy == 0      // false, false != 0
  crazy = 0              // crazy is 0
end
crazy = 1               // crazy is 1
```
#### Files
- Okay left you hanging enough. Here are the awesomeness of the file abstraction
```
name_of_file = name(file)             // "hello.py"
relative_path_of_file = path(file)    // "./node_modules/node.js"
absolute_path_of_file = a_path(file)  // "/home/..."
extension = type(file)                // "txt", "pdf", etc
int_size = size(file)                 // in bytes
int_size = size_kb(file)              // in kilobytes
int_size = size_mb(file)              // in megabytes
int_size = size_gb(file)              // in gigabytes 
sub_files = files(file)               // See notes below
```
- A file can be either a file or a directory. The method `files` gets the subfiles (and sub directories) as a `stack`. If running `files` on a file that is not a directory (ie an actual file), it will return `{}`, an empty stack. 
- The `files` method is NOT recursive. Will only get the files (includes both actual files and directories) that are child of the selected directory. 

#### Math
- Filescript has a bunch of super handy math
```
1 < 2 // true
1 > 2 // false
1 == 2 // false
1 != 2 // true
1 + 2 // 3
1 - 2 // -1
```
- The following do exist: `+`, `-`. Only addition and subtraction are supported. 
- The following are *not* supported: `/`, `%`, `*`
- The following do not exist: `<=`, `>=`, `+=`, `-=`

#### The sole reason for Filescript
- Filescript has only one functionality. `print`
```
print("Hello world!")     // Hello world!
print("1 + 2 = ", 1 + 2)   // 1 + 2 = 3
print("found file: ", name(file), " with size: ", size(file), " bytes") 
// found file: 1.txt with size: 3 bytes
```
- The print statement is very powerful. It can print any type of (supported) datatype out onto the console. 

#### The next big reason: wildcard
- Filescript supports a very simplified wildcard operator for checking "like" or "contains" or "in" cases. 
```
"foo.txt" == "*.txt"   // true
"foo.txt" == "foo.*"   // true
"foo.txt" == "*oo.t*"  // true
```
- The wildcard operator `*` can only be at the beginning, end, or both ends of the string. If at the beginning as in `*.txt`, it will match any string that ends with `.txt`. If at the end as in `foo.*`, it will match any string that starts with `foo.`. If both, then it can be considered as a "contains" operation or an "in" operation. 

### Example 
Task: Count the number of .txt files in the specified directory, and all subdirectories
```
stack = {} 
push(stack, open("/home/foo/")) // this is long way, but permitted
num_files = 0
while len(stack) > 0 do
    cur = pop(stack) 
    if type(file) == "txt" then 
        num_files = num_files + 1 
    end

    push(stack, files(cur))    // push all children of cur onto stack
end

print("Number of .txt files found:", num_files) 
```

## User Study
2 tasks to be asked early next week. 
- **Task 1**: Print the name and size (in KB) for all .py files found in the current directory and all subdirectories
- **Task 2**: Print all the files where whose relative path name contains the folder "foo", and whose grandparent folder has only their parent folder, and their parent folder only has them. ie, `/home/foo/bar/file.py` assuming `bar` only contains `file.py` and `foo` only contains `bar`. 

#### Sample solution to task 2 (not shown to user tester)
```
only_child = {}
only_grandchild = {}
solution = {}
has_siblings = open("/home/")
while len(has_siblings) > 0 do
    curr = pop(has_siblings)
    sub_files = files(curr)
    if len(sub_files) == 1 then
        push(only_child, sub_files)
    end
    if len(sub_files) != 1 then
        push(has_siblings, sub_files)
    end

    while len(only_child) > 0 do
        curr = pop(only_child)
        sub_files = files(curr)
        if len(sub_files) == 1 then
            push(only_grandchild, sub_files)
        end
        if len(sub_files) != 1 then
            push(has_siblings, sub_files)
        end
    end

    while len(only_grandchild) > 0 do
        curr = pop(only_grandchild)
        sub_files = files(curr)
        if len(sub_files) == 0 then
            push(solution, sub_files)
        end
        if len(sub_files) == 1 then
            push(only_grandchild, sub_files)
        end
        if len(sub_files) > 1 then
            push(has_siblings, sub_files)
        end
    end 
end
while len(solution) > 0 do
    curr = pop(solution)
    if path(curr) == "*/foo/*" then
        print("file: ", name(file))
    end
end
```

### User tester 1 outputs
#### Task 1
```
stack = {}
push(stack, open("/home/foo/"))
while len(stack) > 0 do
    cur = pop(stack)
    if type(file) == "py" then
        print(name(file), " with size: ", size(file) / 1000, " KB")
    end

    push(stack, files(cur))
end
```

#### Task 2
```
cur_stack = {}
// continuosly open directories and check if there is one file/directory
// keep track of how many directories we have iterated through (target is 3)
// if the directory has more than or less than 1, stop with this dir
// also add a check in our cur_stack for foo when checking for success condition

push(stack, open("/home/"))
empty_dirs_count = 0
foo_check = False
seen_stack = {}
while len(cur_stack) > 0 do

end
```

### What did they find easy
- Expect them to find easy the first task as it is very similar to the given example. 

#### User tester 1
- while loop syntax and if statement syntax was easy to grasp, so was stack syntax. Using `>`, `=`, `==`, etc was very natural. 
- Purpose of the language was easy to grasp, could easily see how such a language could _in theory_ be used to solve the problem easily. 

#### User tester 2
- The use-case of the language and the similarity of the first task to provided examples.
- The basic iteration and stack usage.

### What did they find difficult
- Expect the second task to be a lot harder, as they have to come up with a novel way of satisfying the constraints. The most obvious solution is maintaining different stacks for each constraint, but they may have completely different ideas. 
- Expect the biggest difficulty to be user forgetting a functionality exists, such as wildcards.

#### User tester 1
- Some difficulties remembering exact syntax and permitted operations. Usage of division `/`, boolean `False` instead of `false. Had to be reminded that can create more than one stack and can create more than one while loop. 
- Second problem was difficult. I wasn't able to understand user's plan of attack, not sure if that was due to a difficult problem or because the language is confusing. 

#### User tester 2
- Remembering or discovering that you don't need to check whether a file is a folder in this case.
- Keeping track of upper-level directory information (parent and grandparent) without a dedicated method.

### What did you learn from your users
- Expect to learn if there are new features we should add into the language to streamline the common tasks. 
- Expect to learn if a feature is commonly used incorrectly (maybe path is usually assumed to be absolute path, etc). 

#### User tester 1
- User (not sure if intentional) very cleverly initialized a stack with `push(stack, open("/home/"))`, which should technically work. Not sure if this should be 'patched'. 
- May want to add in division `/` and multiplication `*`. User instinctively used `/` even after learning that those do not exist in the langauge

#### User tester 2
- The language might benefit from having more built-in utilities for common file system operations (such as explicitly checking for directories and retrieving parent directory information).

### What would you have done differently
- Not possible to answer without having done a user study. 

#### User tester 1
- A better written 'documentation' would have resulted in more knowledge of how the language works which would have made the tasks easier. 
- The example code was used heavily as inspiration for the first task, perhaps it can be useful to not include any starter example. 
- Perhaps given a plan of attack for the second task would better test language features rather than their leetcode abilities. 

#### User tester 2
- While Task 1 works without an explicit folder check (since ‘files(file)’ returns empty for files), and Task 2 can be handled using multiple stacks, our study shows that some users wanted more abstraction. In future iterations, we might add helper functions like ‘isFolder’ or ‘parentInfo’ to simplify these tasks.

## Changes to language
### Changes made so far
- stack, push/pop adds to end, CANNOT hold ints, etc and only can hold 'files'
- open("/file/path") I assume returns a stack of files. So you can simply do `stack = open("/file/path")`. Also can do `push(stack, open(...))` obviously.
- Assume `stack = open("/file/path")` initalizes a new stack. ie, don't have to first call `stack = {}`
- Assume we do NOT have `else` and `else if` conditionals for our `if` statements
- Assume `0 != false`, etc, no automatic type casting
- Assume variables have infinite scope? I'm assuming this is simplest to code, and is what the best programming language (Python) does. Meaning, in a if-statement, I can declare var "asdf", and outside the if, I can access asdf.
- Assume stacks do not support negative index (ie `stack[-1]` to get last file is NOT supported)
- Make `size(file)` in bytes. Add `size_kb`, `size_gb`, etc.
- Language DOES NOT support floats, only int. If you need to check 2.3 GB, check 2300 MB
- default `path(file)` returns RELATIVE path, use `a_path(file)` for absolute
- to get files of a file (a file can be a directory), do `files(file)` and NOT `file["files"]`
- running `files(file)` on a non-directory file WILL NOT return `nil`, BUT RETURN `{}`
- ONLY addition and subtraction supported. NO `*`, `/`, `%`, NO `+=`
- calling `open("/foo/bar")` returns only the file `bar`. Calling `open("/foo/bar/")` returns the contents within `bar`

### Changes considering
- Nothing yet, may appear when implementing

## Changes to project timeline
- No changes to project timeline yet
## Unit tests
Assuming the following sample directory graph:
```
foo
├── foo1
│   ├── bar1
│   │   └── a
│   │       └── long
│   │           └── file
│   │               └── path
│   │                   └── to
│   │                       └── file.txt
│   ├── bar2
│   │   └── rpc
│   │       ├── clientStub
│   │       │   └── clientStub.go
│   │       └── serverStub
│   │           └── serverStub.go
│   └── bar3
├── foo2
├── foo3
├── .gitignore
├── main.py
└── hello.py
```
### Test for opening directory and printing files
```
stack = open("/foo/")
print("stack length after opening foo:", len(stack))

while len(stack) > 0 do
    file = pop(stack)
    print("File name: ", name(file), " File type: ", type(file))
end
```
Should result in
```
stack length after opening foo: 6
File name: foo1 File type: 
File name: foo2 File type: 
File name: foo3 File type: 
File name: .gitignore File type: gitignore
File name: main.py File type: py
File name: hello.py File type: py
```

### Test printing relative path:
```
stack = open("/foo/foo1/bar2/rpc/")

while len(stack) > 0 do
    file = pop(stack)
    print("File path: ", path(file))
end
```
Should result in
```
File path: ./clientStub/clientStub.go
File path: ./serverStub/serverStub.go
```

### Test Push and popping from stack
```
stack = {}
file = open("/foo/main.py")
push(stack, file)
print("Stack length after push: ", len(stack))
file_popped = pop(stack)
print("File popped: ", name(file_popped))
print("Stack length after pop: ", len(stack))
```
Should result in:
```
Stack length after push: 1
File popped: main.py
Stack length after pop: 0
```

### Test counting all the .py files in root
```
stack = open("/foo/")
num_py_files = 0

while len(stack) > 0 do
    file = pop(stack)
    if type(file) == "py" then
        num_py_files = num_py_files + 1
    end
end

print("Number of .py files found: ", num_py_files)
```
Should result in:
```
Number of .py files found: 2
```

### Test counting all the .go files in all paths
```
stack = open("/foo/")
num_go_files = 0

while len(stack) > 0 do
    file = pop(stack)
    if type(file) == "py" then
        num_go_files = num_go_files + 1
    end

    push(stack, files(file))
end

print("Number of .go files found: ", num_go_files)
```
Should result in:
```
Number of .go files found: 2
```
### Check metadata on file
```
file = open("/foo/foo1/bar1/a/long/file/path/to/file.txt")
print("File name: ", name(file))
print("File extension: ", type(file))
print("File size in bytes: ", size(file))
print("File size in KB: ", size_kb(file))
print("File size in GB: ", size_gb(file))
```
Should result in:
```
File name: file.txt
File extension: txt
File size in bytes: 1234
File size in KB: 1
File size in GB: 0
```

### Check wildcard
```
print("Test: ", "foo/bar/xyz.txt" == "*/foo/*")
print("Test: ", "foo/bar/xyz.txt" == "*/none/*")
print("Test: ", "foo/bar/xyz.txt" == "*/foo/*/xyz.txt")
```
Should result in:
```
Test: true
Test: false
Error: Wildcards can only be placed at ends
```
### Error case: Testing opening non-existant file/directory
```
stack = open("/non_existent_directory/")
print("Stack length: ", len(stack))
```
Should result in:
```
Error: Could not find directory /non_existent_directory/
```
Alternatively (we have not decided yet)
```
Stack length: 0
```

### Error case: popping from empty stack
```
stack = {}
file = pop(stack)
print("asdf File popped: ", file == nil)
```
Should result in:
```
Error: Stack is empty
```
Alternatively (we have not decided yet)
```
asdf File popped: true
```

### Error case: accessing from out-of-bounds/negative idx
```
stack = open("/foo/")
file = stack[42069] 
print("asdf file: ", file == nil)
```
Should result in
```
Error: Index 42069 is out of bounds
```
Alternatively (we have not decided yet)
```
asdf file: true
```

### Error case: push non-element file onto stack
```
stack = {}
push(stack, 42)
print("asdf stack length: ", len(stack))
```
Should result in
```
Error: Pushed value is not a file
```
Alternatively (we have not decided yet)
```
asdf stack length: 0
```

### Error case: division by 0 (division not supported)
```
num = 42 / 0
```
Should result in
```
Error: unsupported operation
```

## Check-In 4 Report
### Status of the implementation
- Component-wise progress
- Which tests are passing, and which not?
- Which extra tests still need to be written/made?

Overall we are relatively behind in our development processes, with some more up to speed than others. We have not written many tests and so ideally we would work to create a more comprehensive test suite in the coming weeks that includes an end-to-end test with the examples folder we created of potential operations and results that FileScript can use. 

In general, we have been more focused on creating a design for the language. This includes internal representations of errors, the AST, etc. we can use in the interpreter along with the development of an interface for error messages that will be communicated with the user, reflecting that there is an issue with their program and not our interpretter. 

Breaking things down into components the design of error handling and parsing/lexing is complete. We are currently designing the visitor pattern and distinguishing between statement/expressions and how we will accumulate the results of the visitor. Additionally, we are working on designing an intermediate representation between a value and an expression in our language to make things like argument passing simpler. We are also working on a method dispatch mechanism. 

### Plans for final user study
- Are there any major differences from the previous one? If so, what are the reasons?
- Make sure to find suitable users (different from the first study!)

There likely won't be any major differences from the previous one, but there will be some changes. 
What we will likely keep 

- Have pre-written problems ranging in difficulty for the user tester to solve. 
- Have a 'documentation' and example(s) for the user to reference to solve the problem. 
- Encourage the user tester to walk through their thoughts and for us to provide minimal guidance and not fix any mistakes they make

What will likely change
- Ensure the harder problems are testing language features rather than user's programming capabilities. I think this means for harder problem(s), if the user has no idea how to approch solving we should provide a strategy/plan on how to approach the problem by providing them with the theoretical solution, so the user will still end up having to implement the solution using the langauge features. 
- It may be cool to see if the user can come up with tasks they believe the language is particularly good at solving. It is obvious to us that the language can help with finding files and aggregating information about files, but maybe that is not obvious to a new user. 
### Planned timeline of remaining days
- Plans for integration/end-to-end testing
- Be sure to test for smooth error-handling (as well as expected results working)
- Make sure to schedule some time for bug-fixing!

Ideally around the half-way point of reading break we would have most implementation and testing completed to leave ourselves room for bug fixing. A focus on the design is needed over the next week to break tasks up amongst each group member.

By Friday, 14, 2025: 
- Method dispatch functional
- Basic file metadata is accessible after using `open` in FileScript
- Visitor pattern for evaluation is working. If a user inputs something like `open("test" + "file")` the program successfully evaluates the argument expression to "testfile" and then dispatches `open` with the argument as a value (not an expression)

By Friday, 21, 2025:
- Loops, conditionals, and all rich features working 
- Integeration tests completed and passing

By Tuesday, 25, 2025: 
- Ideally everything will be passing and completed by Friday, 21, 2025. But, in the event of unexpected bugs, they should be fixed by this due date.
- Record and put together demonstration video

## Check-In 5 Report
Status of user study (should be completed this week at the latest)
- User study not done for this week.
- Expected findings: Simple problem(s) should be trivial for the user if there is good documentation with examples that user-tester could reference. 
- Expect difficult problems, or problems not provided a similar 'solved' example to be more difficult as it will require the user tester to internalize the language, rather than copy-paste and change a few keywords. 
- Expect key feedback to come from user making understandable mistakes with the language, such as assuming a feature exists that is not (yet) supported. 
- We will likely use the same 2 questions as used in the previous user studies, but with updated syntax to match our implementation, as well as expected results from our demo directory for the user to use. The more difficult question will still be optional and see to what extent the user can internalize our language in such a brief amount of time.

Are there any last changes to your design, implementation or tests?
- We have made the following changes, amongst others:
    - Changed how subfiles and subdirectories are represented in a file metadata object returned by `open()`
    - Add internal function dispatch capabilities: simplifies calling built-in methods like 'print' and 'open' by being more generalized and support any function
    - Separate values/expressions representation in the AST Java classes in our language design to perform eager evaluation
    - Added `else` case to `if` statements: enables using else instead of writing another if statement for the opposite case. 
    - Support division and multiplication, as well as `>=`, `<=`: enables another way to check greater than or less than. 

Plans for final video (possible draft version). 
- Plan to create a simple slide deck that will be used in the video. Also have a demo of the language with a demo directory to showcase the language in action. 
- No set roles for the video as of now, dynamic assigning of tasks for the video as time comes closer to implementing the video. 

Planned timeline for the remaining days.
- Week 8
    - Finish implementation up to extent to support user studies with the basic task
    - Do user studies
    - Start working on the video and finish rest of the implementation
- Week 9
    - Finish video
    - Last minute testing/debugging/final touches + tests
    - Submit

### User Studies
#### User study 1
**Task 1**: Print the name and size (in KB) for all .py files found in the current directory and all subdirectories
```
root = open('examples/sample_dir')

dirs = {}
push(dirs,root)

while len(dirs) > 0 do
  dir = pop(dirs)
  sub_files = dir['subs']
  while len(sub_files) > 0 do
    sub = open(pop(sub_files))
    if sub['is_dir'] then
      push(dirs,sub)
    else
      if sub['name'] == '*.py' then
        print(sub['name'],sub['size'])
      end
    end
  end
end
```
**Findings**
- User found it very simple given the sample program, essentially only changed a few lines
- Was very helpful in understanding the structure of the language and how to use it, and played a great role in their understanding of the second problem
- Expected them to find the `open(pop(sub_files))` to be obscure and complicated, but they seemed to easily understand that concept and how to use it.
- No difficulties with the language or syntax given mostly it was based off of the example.

**Task 2**: Print all the files where whose relative path name contains the folder "foo", and whose grandparent folder has only their parent folder, and their parent folder only has them. ie, `/home/foo/bar/file.py` assuming `bar` only contains `file.py` and `foo` only contains `bar`. 

```
root = open('examples/sample_dir')

solo_children = {}
solo_grandchildren = {}
dirs = {}
push(dirs,root)

while len(dirs) > 0 do
  dir = pop(dirs)
  sub_files = dirs['subs']
  if len(sub_files) == 1 do
    sub = open(pop(sub_files))
    push(solo_children,sub)
  else
    while len(sub_files) > 0 do
      sub = open(pop(sub_files))
      if sub['is_dir'] then
        push(dirs,sub)
      end
    end
  end
  while len(solo_children) > 0 do
    solo_child = pop(solo_children)
    sub_files = solo_child['subs']
    if len(sub_files) == 1 do
      sub = open(pop(sub_files))
      push(solo_grandchildren,sub)
    else
      while len(sub_files) > 0 do
        sub = open(pop(sub_files))
        if sub['is_dir'] then
          push(dirs,sub)
        end
      end
    end
  end
  while len(solo_grandchildren) > 0 do
    solo_grandchild = pop(solo_grandchildren)
    if !solo_grandchild['is_dir'] then
      if solo_grandchild['name'] == */foo/* then
        print(solo_grandchild['name'])
      end
    end
    sub_files = solo_grandchild['subs']
    if len(sub_files) == 1 do
      sub = open(pop(sub_files))
      push(solo_grandchildren,sub)
    else
      while len(sub_files) > 0 do
        sub = open(pop(sub_files))
        if sub['is_dir'] then
          push(dirs,sub)
        end
      end
    end
  end
end

```
**Findings**
- In this problem, the user got stuck - as expected - and had to be guided through it by a series of hints. The hints were all logical in nature, such as the idea to have multiple stacks, to do a while check on `solo_children` outside the if statement, etc. The user had to apply these hints using Filescript, which I gave no hints besides correcting obvious mistakes such as forgetting to rename variables. The user took 45 minutes to solve this problem. 
- In the problem, the user kept wanting to do string manipulation to check whether parent had a sole child, but had to be reminded that Filescript does not have that capabilities and they have to manually traverse the directories. 
- The user instinctively used the not operator `!` in their code, which is understandable but not supported by Filescript. 
- The user had a surprisingly difficult time understanding how to use the wildcard operator `*`. They thought a lot about if there was a way to split the string into an array of strings to find whether it contains 'foo', but that was completely off track. Even their final solution after being briefed on wildcards again is incorrect because they forgot to enclose it in strings. 
- The use of `len` and `push` and `pop` was very natural, there was no confusion. Occasionally the user forget how to check if a file is a file or a directory and had to be reminded to use `is_dir` or check len of `subs` is 0.

#### User Study 2
**Task 1**: Print the name and size (in MB) for all .py files found in the current directory and all subdirectories

```
// Print the name and size (in MB) for all .py files found in the current directory and all subdirectories
root = open('examples/sample_dir')

stack = {}

push(stack, root)

while len(stack) > 0 do
    dir = pop(stack)
    sub_files = dir['subs']
    while len(sub_files) > 0 do
        sub = open(pop(sub_files))
        if sub['is_dir'] then
            push(stack, sub)
        else
            if sub['name'] == '*.py' then
                print("", sub['names'])
                sub_mb = sub['size'] / 1000
                print("", sub_mb)
            end
        end
    end
end
```
**Findings**
- User confused on what `dir['subs']` is, afraid that there may be a file name in `'subs'`, confused on notation. Had to confirm with the documentation again to understand those are properties of the `file` and have no impact on the subfiles the direction contains. 
- Slightly confused on the print syntax, believed that they had to start off with string: `print("", file['names'])` rather than just `print(file['names'])`.
- Otherwise, a very simple problem. The documentation was slightly outdated so they used double quotes when our language only supports single quotes. The user had no hardships solving this problem, similar with previous user, mostly because the example provided in the documentation is very similar to this task. 

**Task 2**: Print all the files where whose relative path name contains the folder "foo", and whose grandparent folder has only their parent folder, and their parent folder only has them. ie, `examples/sample_dir/home/foo/bar/file.py` assuming `bar` only contains `file.py` and `foo` only contains `bar`.

```
root = open('examples/sample_dir')

stack1 = {}
stack2 = {}

push(stack1, root)

while len(stack1) > 0 do
    dir = pop(stack1)

    sub_files_dirs = dir['subs']

    if len(sub_files_dirs) == 1 then
        sub_dir = open(pop(sub_files_dirs))
        print('only child ', sub_dir['name'])
        sub_files_dirs_sub = sub_dir['subs']
        if len(sub_files_dirs_sub) == 1 then
            idk = open(pop(sub_files_dirs_sub))
            final_sub = idk['subs']
            print('idk ', idk['name'])
            while len(final_sub) > 0 do
                file = open(pop(final_sub))
                if file['is_dir'] == false then
                    push(stack2, file)
                end
            end
        end
    end

    sub_files_dirs = dir['subs']

    while len(sub_files_dirs) > 0 do
        sub = open(pop(sub_files_dirs))
        if sub['is_dir'] then
            push(stack1, sub)
        end
    end
end

while len(stack2) > 0 do
    file = open(pop(stack2))

    if file['name'] == '*/foo/*' then
        print(file)
    end
end
```
- User did not want to be guided to the solution I came up with, and wanted to try to solve it themselves according to their own volition. The above solution is the result of them solving it by themselves, and then us pair-programming fixing the syntax errors to debug their program. It does not work, but it is very close as printing `print('idk ', idk['name'])` prints the correct files in a very simple test case. 

**Findings**
- User had a hard time understanding wildcards, similar to previous user tester. This is surprising as I would have thought this to be pretty obvious. They also talked about how simple this would be in Python, giving the example of `"foo" in "asdf/foo/adsf"` being simple in Python. And at the same time they had a hard time seeing how to do that with wildcards. They do eventually figure it out to use `*/foo/*`, but had to be told to check documentation.
- During programming, user wanted to use `&&` at the 🔴
```
root = open('examples/sample_dir')

stack1 = {}

push(stack1, root)

while len(stack1 > 0) do
    dir = pop(stack1)


    sub_files_dirs = dir['subs']

    if len(sub_files_dirs) == 1 && 🔴 do
        
    end
    
    while len(sub_files_dirs) > 0 do
        sub = open(pop(sub_files_dirs))
        if sub['is_dir'] then
            push(stack1, sub)
        end
    end
end
```
- The user, unlike the previous user tester, forgot to `open` many times and had to be continously reminded to use it, as well as to read up on the documentation again to realize that `dir['subs']` doesn't return stack of `file`s. See 🔴 for popping but not wrapping in open before calling the subs property on a file. 
```
    sub_files_dirs = dir['subs']

    if len(sub_files_dirs) == 1 do
        sub_dir = pop(sub_files_dirs)🔴
        sub_files_dirs_sub = sub_dir['subs']
        if len(sub_files_dirs_sub) == 1 do
            
        end
    end
```
- Like previous user tester, user assumes 'not' exists. Neither user testers thought of `== false` for such a condition. But this user tester, upon asking whether booleans existed in the language (I said yes) found out they could do `== false` on their own volition. See use of not at 🔴
```
            final_sub = idk['subs']
            while len(final_sub) > 0 do
                file = open(pop(final_sub))
                if not 🔴 file['is_dir'] then
                    push()
            end
```
- Snapshot iteration nearing the end of their implementation of their program. Many times the user forgot to close off their `while` and `if` with an `end`, see 🟢 for such cases. User also mixed up `if ... then` with `if ... do`, see 🟠 for such cases. User uses double quotes throughout, which is not supported. 
```
root = open('examples/sample_dir')

stack1 = {}
stack2 = {}

push(stack1, root)

while len(stack1 > 0) do
    dir = pop(stack1)
    
    sub_files_dirs = dir['subs']

    if len(sub_files_dirs) == 1 do 🟠
        sub_dir = open(pop(sub_files_dirs))
        sub_files_dirs_sub = sub_dir['subs']
        if len(sub_files_dirs_sub) == 1 do 🟠
            idk = open(pop(sub_files_dirs_sub))
            final_sub = idk['subs']
            while len(final_sub) > 0 do
                file = open(pop(final_sub))
                if not file['is_dir'] then
                    push(stack2, file)
                🟢
            end
        end
    end

    sub_files_dirs = dir['subs']
    
    while len(sub_files_dirs) > 0 do
        sub = open(pop(sub_files_dirs))
        if sub['is_dir'] then
            push(stack1, sub)
        end
    end
end
```
- User again forgot to use `open`, and forgot how to check for the file name (supposed to use `file['name']`).
```
while len(stack2) > 0 do
    file = pop(stack2)

    if file == "*/foo/*"
        print("", file)
    end
end
```
- While programming without my guidance, user made significant strides in their implementation. Their command of the language was much more frail, either because I was uncertain what they were doing so I did not stop them when they were doing something I was not expecting, unlike the previous user study where as soon as the user strayed off path I gave hints on how to approach instead. The user also took about 45 minutes to solve this problem. 
- The user had a much more difficult time understanding what `dir['subs']` returns and forgets the need for `open`. Additionally, they had a bit of difficulty understanding wildcards. 

### User study reflection
- General observations
  - Task 1: Both users found task 1 pretty simple due to the provided example in the documentation. Both were able to adapt the example with minimal modifications. 
  - Task 2: Much more challenging task, as expected, and both took 45 minutes, which was way longer than I thought. Wild-cards was an unexpected problem for both users as both wanted better ways to do string manipulation. 
- Specific findings
  - Usage of boolean operators such as `!`, `not`, and `&&` were natural to both users but not supported. 
  - Forgetting to open files: the documentation may need to be more clear on the distinction between 'unopened' files and opened files: opened files allow you to access metadata such as `is_dir`, `subs`, etc. 
  - Wildcards were complex
- Reflections
  - Perhaps have better updated documentation. If specific findings aren't going to be supported, at least provide users with alternatives such as `== false`, or give examples of what happens if they don't open files. 
  - Maybe can simplify the need to `open` files each time. 
  - Overall, the language provides a very useful abstraction to iterate through directories and files, at no time was there confusion on what the purpose of the language nor why it may be useful. Users very clearly saw how the language can be used to solve a problem, although the language is certainly opinionated. Despite support for list-like attributes, such as accessing a specifc index in a stack, that capability doesn't do much and is not recommended as users cannot pop from there nor set them as something else (ie `stack[3] = file`). They are 'forced' to use the `stack` as a stack, to iterate through the entire stack every time and have while loops to check if `len(...) > 0`. If the user does not naturally think through problems with that perspective, they will have difficulties and try to find solutions that don't exist (such as string manipulation). 