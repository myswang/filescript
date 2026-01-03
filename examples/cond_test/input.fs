x = 6

if x > 3 then
    x = x + 10
end

print('Expected value of x:', 6 + 10)
print('Actual value of x:', x)

if true then
    print('True is true yes')
else
    print('ERROR: should never reach')
end

if false then
    print('ERROR: should never reach')
else
    print('False is false yes')
end

if false == false then
    print('False is false yes x2')
end

while false do
    print('ERROR: should never reach')
end
