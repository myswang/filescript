stack = {}
file = open('examples/sample_dir/')
push(stack, file)

first_file = stack[0]
no_file = stack[1]
print(first_file['name'])
print(no_file['name']) // throw error