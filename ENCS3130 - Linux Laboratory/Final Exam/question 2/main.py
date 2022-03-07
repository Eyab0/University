allPre = []


def getPermutations(num, list, index):
    global allPre
    perm = ""
    if index == num:
        for index in range(0, num):
            perm += str(list[index])
        if perm.count('0') <= perm.count('1'):
            print(perm)
            allPre.append(perm)
        return
    list[index] = 0
    getPermutations(num, list, index + 1)
    list[index] = 1
    getPermutations(num, list, index + 1)


if __name__ == '__main__':
    print("final exam q2")
    num = input("please enter the length of the permutations : ")
    try:
        if not num.isdigit():
            raise ValueError("Error , invalid input")
    except ValueError as ve:
        print(str(ve))
        exit(-1)

    list = [None] * int(num)
    getPermutations(int(num), list, 0)
    print(allPre)

    try:
        file = open("output.txt", "w")
        for i in allPre:
            file.write(i + "\n")
    except IOError:
        print("Error: can\'t find file or read data")
    else:
        print("Written content in the file successfully")
        file.close()
