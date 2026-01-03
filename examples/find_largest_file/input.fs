stack = {}
push(stack, open('Team14'))
largest_name = ''
largest_size = 0

while len(stack) > 0 do
    cur = pop(stack)
    if cur['is_dir'] == false then
        if cur['size'] > largest_size then
            largest_name = cur['name']
            largest_size = cur['size']
        end
    end

    if cur['is_dir'] == true then
        paths = cur['subs']
        i = 0
        while i < len(paths) do
            push(stack, open(paths[i]))
            i = i + 1
        end
    end
end

print('The largest file is:', largest_name)
print('Size:', largest_size / 1024, 'KB')
