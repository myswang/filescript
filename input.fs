// NOTE: this script is just an illustrative example.
// For real-world test cases, see the examples/ directory

files = open('/home/foo')
i = 0

print('Files that are greater than 4096 KB in size:')

while i < len(files) do
    x = 0 + 1 + 2 * 3
    curr = files[i]
	if curr['size'] > 4096 then
    		print('Name:', curr['name'], 'Size:', curr['size'], 'KB')
	end
	i = i + 1
end

stack = {}
push(stack, open('/home/foo')) // add a dir to begin searching from
numFiles = 0
while len(stack) > 0 do
    cur = pop(stack) // pops the last file from the stack and returns it
    if cur['is_dir'] == false then // compare filename using wildcard operator
        if cur['name'] == '*.txt' then
            numFiles = numFiles + 1 // increment file count
        end
    end

    if cur['is_dir'] == true then // get the sub directories/files
        subs = cur['subs']
        i = 0
        while i < len(subs) do
            push(stack, open(subs[i])) // push them onto the stack for further searching
        end
    end
end

print('Number of .txt files found:', numFiles)
