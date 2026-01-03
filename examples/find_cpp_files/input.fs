code_dir = open('Team14/src')
num_files = 0

code_files = code_dir['subs']

i = 0
while i < len(code_files) do
    cur_file = open(code_files[i])
    if cur_file['name'] == '*.cpp' then
        print(cur_file['name'])
        num_files = num_files + 1
    end
    i = i + 1
end

print('Total number of .cpp files found:', num_files)
