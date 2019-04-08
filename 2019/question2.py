

t = int(input()) # read a line with a single integer
for i in range(1, t + 1):
    input() # skip the reading n
    her_path = input()
    my_path = "".join('S' if step == 'E' else 'E' for step in her_path)
    print("Case #{}: {}".format(i, my_path))
