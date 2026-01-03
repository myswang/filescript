if true && true then
    print('true and true')
end

if true && false then
    print('Error')
end

if false && true then
    print('Error')
end

if false && false then
    print('Error')
end

if true || true then
    print('true or true')
end

if true || false then
    print('true or false')
end

if false || true then
    print('false or true')
end

if false || false then
    print('Error')
end

if true && true || false then
    print('yeah')
end

if false || true && true || false || false || true && true then
    print('yes')
end

if true && false || false && true then
    print('ERROR')
end

if 1 && true then
    print('mismatched types error')
end