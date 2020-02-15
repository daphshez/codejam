# todo: this still fails with RE on codejam test

from collections import defaultdict


def propegate(assignment, constraints, i):
    for c in constraints[i]:
        if c not in assignment:
            assignment[c] = 1 - assignment[i]
            propegate(assignment, constraints, c)


def solve(adv):
    constraints = defaultdict(set)
    for i in range(1, len(adv) + 1, 2):
        constraints[i].add(i + 1)
        constraints[i + 1].add(i)

    for i in range(len(adv) - 1, 0, -2):
        constraints[adv[i]].add(adv[i - 1])
        constraints[adv[i - 1]].add(adv[i])

    assignment = {}
    for i in range(1, len(adv) + 1):
        if i not in assignment:
            assignment[i] = 0

        propegate(assignment, constraints, i)

    return ''.join('L' if assignment[i] == 0 else 'R' for i in range(1, len(adv) + 1))


t = int(input())
for i in range(1, t + 1):
    _ = input()
    s = [int(x) for x in input().split()]
    print("Case #{}: {}".format(i, solve(s)))

# import unittest
#
#
# class TestSolve(unittest.TestCase):
#     def test1(self):
#         self.assertEqual('LRRL', solve([3, 1, 2, 4]))
#
#     def test2(self):
#         self.assertEqual('LRLR', solve([1, 2, 3, 4]))
#
#     def test3(self):
#         self.assertEqual('LRR', solve([2, 3, 1]))
#
#     def test4(self):
#         self.assertEqual('L', solve([1]))
#
#     def test5(self):
#         self.assertEqual('LRRLLR', solve([5, 3, 1, 6, 4, 2]))
#
#
# if __name__ == '__main__':
#     unittest.main()
#
#

# import random
# import sys
#
#
# def simulate(adv):
#     solution = solve(adv)
#     weight_l = weight_r = 0
#     for i, a in enumerate(solution):
#         if a == 'L':
#             weight_l += 1
#         else:
#             weight_r += 1
#         if abs(weight_l - weight_l) > 1:
#             raise Exception(str(i))
#     for a in adv:
#         if solution[a - 1] == 'L':
#             weight_l -= 1
#         else:
#             weight_r -= 1
#         if abs(weight_l - weight_l) > 1:
#             raise Exception(str(a))
#     print("success")
#
#
# for i in range(100):
#     n = random.randint(2, 1000)
#     adv = list(range(1, n+1))
#     random.shuffle(adv)
#     print(adv)
#     simulate(adv)
