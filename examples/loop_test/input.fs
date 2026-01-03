list = {}

i = 0
while i < 10 do
    push(list, i + 1)
    i = i + 1
end

i = 0
while i < len(list) do
    list[i] = list[i] * 2
    i = i + 1
end

i = 0
while i < len(list) do
    print(list[i])
    i = i + 1
end
