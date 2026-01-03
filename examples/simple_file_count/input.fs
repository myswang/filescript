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
