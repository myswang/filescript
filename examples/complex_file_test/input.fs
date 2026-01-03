only_child = {}
only_grandchild = {}
has_siblings = {}
solution = {}

root = open('examples/sample_dir')
push(has_siblings, root)


while len(has_siblings) > 0 do
    curr = pop(has_siblings)
    sub_files = curr['subs']

    if len(sub_files) == 1 then
        push(only_child, open(pop(sub_files)))
    end
    if len(sub_files) >= 2 then
        while len(sub_files) > 0 do
            push(has_siblings, open(pop(sub_files)))
        end
    end

    while len(only_child) > 0 do
        curr = pop(only_child)
        sub_files = curr['subs']

        if len(sub_files) == 1 then
            push(only_grandchild, open(pop(sub_files)))
        end
        if len(sub_files) >= 2 then
            while len(sub_files) > 0 do
                push(has_siblings, open(pop(sub_files)))
            end
        end
    end

    while len(only_grandchild) > 0 do
        curr = pop(only_grandchild)
        sub_files = curr['subs']

        if len(sub_files) == 0 then
            push(solution, curr)
        end
        if len(sub_files) == 1 then
            push(only_grandchild, open(pop(sub_files)))
        end
        if len(sub_files) >= 2 then
            while len(sub_files) > 0 do
                push(has_siblings, open(pop(sub_files)))
            end
        end
    end
end

while len(solution) > 0 do
    curr = pop(solution)

    if curr['name'] == '*/foo/*' then
        print('file: ', curr['name'])
    end
end