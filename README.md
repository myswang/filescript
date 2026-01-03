# FileScript

A rich DSL for scripting file system operations, inspired by Bash and Lua

### Setup Instructions

1. Install IntelliJ IDEA and the corresponding ANTLRv4 plugin.
2. Open this project in IntelliJ. 
3. Under `src/parser` there are two ANTLR grammar files, `FileScriptLexer.g4` and `FileScriptParser.g4`. Right-click on both of these files and select *Generate ANTLR Recognizer*. If this option doesn't exist, make sure that the ANTLRv4 plugin is installed.
4. This will create a `gen` directory in the project root. Right-click it and select *Mark Directory As -> Generated Sources Root*.

#### Running a single example

*NOTE: Some test scripts depend on a `Team14` directory - you can get it [here](https://drive.google.com/file/d/1auG3pufsLEwm2l261Ot4uzK--PqlO4GL/view?usp=sharing). Unzip the file and place it at the project root.*

1. Open the `Main.java` file, which can be found at `src/ui/Main.java`.
2. In the main toolbar, go to *Run -> Edit Configurations...*
3. Add a new *Application* configuration. For the main class name, put `ui.Main`.
4. Add a CLI argument: `examples/TEST_NAME/input.fs`. See the `examples` directory for various FileScript programs and their corresponding outputs. For example, a "Hello World" program can be found at `examples/hello_world/input.fs`.
5. Click *Run* to execute the program. You can find the expected output at `examples/TEST_NAME/output.txt`.

#### Running the test suite

To run all tests automatically and see a diff-output for failing tests, you can run the following automation script:
```bash
python3 examples/test.py
```

### FileScript Documentation
#### Stack
```
stack = {}
```
- A stack is initialized with `{}`. In FileScript, a stack is treated as an iterable, allowing loops to iterate over each file in the stack. A stack can hold files as well as other types.
```
push(stack, file)
file = pop(stack)
```
- Push a file onto the (end) of the stack.
- Pop a file (from the end) of the stack.
```
push(stack, open("/file/path/"))
```
- Can push file to end of another stack

#### `open` Method
```
open("/foo/main.py")    // returns the file at /foo/main.py
open("/foo/bar")        // returns the file /foo/bar
open("/foo/bar/")       // returns the file /foo/bar
```

#### Stack Operations
```
length = len(stack)
```
- Returns the length of the stack as an integer.
```
file = stack[3]
```
- A stack can also be used as a list/array. This returns the file at index 3 of the stack. Indexing starts from 0.
- Stacks do not support negative indexes.

#### Other Data Types
```
an_integer = 1
a_string = "asdf"
boolean = true
```
- The following are **not** supported: floats/doubles (e.g., `1.2`).

#### Conditionals
```
while <condition> do
   <code>
end
```
- A while loop executes `<code>` as long as `<condition>` is true.
```
if <condition> then
  <code>
else
  <code>
end
```
- If `<condition>` is true, it executes the first block. Otherwise, it executes the `else` block.

#### Comments
```
// for in-line comments
```
- There are no block comments.

#### Mutable Variables
- All variables are mutable and have infinite scope.
```
if true then
  x = open("/home/")    // x is initialized as a stack of files
  x = false             // x is a boolean (false)
  x = x == 0            // false, false != 0
  x = 0                 // x is 0
end
x = 1                   // x is 1
```

#### Files
```
name_of_file = file['name']             // "src/hello.py"
int_size = file['size']                 // in bytes
is_dir = file['is_dir']                 // boolean if it is directory
sub_files = file['subs']                 // See notes below
```
- A file can be either a file or a directory.
- The `subs` attribute gets the subfiles (and subdirectories) as a `stack`. If used on a file (not a directory), it returns `{}` (an empty stack).
- `subs` is **not** recursive; it only retrieves the immediate children.

#### Math Operations
```
1 < 2  // true
1 > 2  // false
1 == 2 // false
1 != 2 // true
1 + 2  // 3
1 - 2  // -1
1 >= 2 // false
1 <= 2 // true
```
- Supported operators: `+`, `-`, `<`, `>`, `==`, `!=`, `<=`, `>=`, `*`, `/`.
- Not supported: `%`
- No assignment operators (`+=`, `-=`)

#### Printing
```
print("Hello world!")     // Hello world!
print("1 + 2 = ", 1 + 2)   // 1 + 2 = 3
print("found file: ", file['name'], " with size: ", file['size'], " bytes")
// found file: 1.txt with size: 3 bytes
```
- `print` can display any supported data type.

#### Wildcard Matching
```
"foo.txt" == "*.txt"   // true
"foo.txt" == "foo.*"   // true
"foo.txt" == "*oo.t*"  // true
```
- The `*` wildcard can only be used at the beginning, end, or both ends of a string.
- `*.txt` matches any string ending in `.txt`.
- `foo.*` matches any string starting with `foo.`.
- `*oo.t*` acts like a "contains" operation.

### Sample
Task: count the number of txt files in a folder
```
root = open('examples/sample_dir')

txt_count = 0

dirs = {}
push(dirs, root)

while len(dirs) > 0 do
    dir = pop(dirs)
    sub_files = dir['subs']
    while len(sub_files) > 0 do
        sub = open(pop(sub_files))
        if sub['is_dir'] then
            push(dirs, sub)
        else
            if sub['name'] == '*.txt' then
                print('found txt file: ', sub['name'])
                txt_count = txt_count + 1
            end
        end
    end
end
print('total txt', txt_count)

```
