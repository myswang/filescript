foo = 'foobar.txt'

if 'foobar.txt' == 'foobar.txt' then
    print('sanity test passed')
end

if foo == 'foobar.txt' then
    print('standard test 1 passed')
end

if foo != 'barfoo.txt' then
    print('standard test 2 passed')
end

if foo == '*.txt' then
    print('wildcard test 1 passed')
end

if foo == 'foo*' then
    print('wildcard test 2 passed')
end

if foo == '*oba*' then
    print('wildcard test 3 passed')
end