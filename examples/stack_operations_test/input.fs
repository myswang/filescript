num_stack = {}
push(num_stack, 1)
push(num_stack, 2)
while len(num_stack) > 0 do
    num = pop(num_stack)
    print('number: ', num)
end

stack = {}
print('stack start:', len(stack) == 0)

push(stack, open('examples/sample_dir/first-text.txt'))
print('stack size after 1 push: ', len(stack))
file = pop(stack)
print('stack size after 1 pop: ', len(stack))
print('popped file name: ', file['name'])

stack0 = {}
stack1 = open('examples/sample_dir/')
push(stack0, stack1)
stack2 = open('examples/sample_dir/')

push(stack0, stack2)

file0 = stack0[0]
file1 = stack0[1]
print('first file in stack: ', file0['name'])
print('second file in stack: ', file1['name'])
while len(stack0) > 0 do
    file = pop(stack0)
    print(file['name'])
end
file = pop(stack0)
print('Null file: ', file) // this throws runtime error
