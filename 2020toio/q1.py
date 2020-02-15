t = int(input())
for i in range(1, t + 1):
    s = input()
    available_capital_o = 0
    available_small_o = 0
    count = 0
    for c in reversed(s):
        if c == 'i':
            if available_small_o > 0:
                available_small_o -= 1
            else:
                available_capital_o -= 1
        if c == 'I':
            if available_capital_o > 0:
                available_capital_o -= 1
                count += 1
            else:
                available_small_o -= 1
        if c == 'o':
            available_small_o += 1
        if c == 'O':
            available_capital_o += 1
    print("Case #{}: {}".format(i, count))
