source_file = open('Team14/src')

print('Name:', source_file['name'])
// NOTE: directory entry sizes differ by OS/filesystem
print('Has size?', source_file['size'] > 0)
print('Last modified:', source_file['last_modified'])
print('Is directory?', source_file['is_dir'])

subs = source_file['subs']
i = 0
while i < len(subs) do
    print(subs[i])
    i = i + 1
end
