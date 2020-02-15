def next_state(state, count, c):
    new_states = {}
    if c == 'i':
        if state[0] == 0:
            new_states[(1, state[1], state[2], state[3])] = count
        if state[1] == 0:
            new_states[(state[0], 1, state[2], state[3])] = count
    if c == 'I':
        if state[2] == 0:
            new_states[(state[0], state[1], 1, state[3])] = count
        if state[3] == 0:
            new_states[(state[0], state[1], state[2], 1)] = count
    if c == 'o':
        if state[0] == 1:
            new_states[(0, state[1], state[2], state[3])] = count
        if state[2] == 1:
            new_states[(state[0], state[1], 0, state[3])] = count
    if c == 'O':
        if state[1] == 1:
            new_states[(state[0], 0, state[2], state[3])] = count
        if state[3] == 1:
            new_states[(state[0], state[1], state[2], 0)] = count if c == 'o' else count + 1
    return new_states


def solve(s):
    activate_states = {(0, 0, 0, 0): 0}

    for c in s:
        new_active_states = {}
        for state, count in activate_states.items():
            items = next_state(state, count, c).items()
            for new_active_state, new_count in items:
                if new_active_state not in new_active_states or new_active_states[new_active_state] < new_count:
                    new_active_states[new_active_state] = new_count
        activate_states = new_active_states

    return activate_states[(0, 0, 0, 0)]


t = int(input())
for i in range(1, t + 1):
    s = input()
    print("Case #{}: {}".format(i, solve(s)))


#
# import unittest
#
#
# class TestSolve(unittest.TestCase):
#     def test1(self):
#         self.assertEqual(1, solve('IO'))
#
#     def test2(self):
#         self.assertEqual(1, solve('IiOO'))
#
#     def test3(self):
#         self.assertEqual(1, solve('IiOioO'))
#
#     def test4(self):
#         self.assertEqual(1, solve('IiOioIoO'))
#
#
# if __name__ == '__main__':
#     unittest.main()
