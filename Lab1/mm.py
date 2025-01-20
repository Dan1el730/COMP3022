# Another migration code written and revised by Daniel Yeung, Python matrix multiplication version 1.01, adopted from Prof Cao adapting from https://csapp.cs.cmu.edu/3e/ics3/code/mem/matmult/mm.c 
import time
import sys

# As a good habit learnt from COMP1002, we write docstrings in functions/procedures

MAXN = 1024

# Initializing global matrix constants with all elements set to 0
ga = [[0.0 for _ in range(MAXN)] for _ in range(MAXN)]
gb = [[0.0 for _ in range(MAXN)] for _ in range(MAXN)]
gc = [[0.0 for _ in range(MAXN)] for _ in range(MAXN)]

# Unlike C and Java, there is no need for declaring a function pointer (Dynamic typing!)

def check_result(c, n):
    """multiplication check for correct results."""
    for i in range(n):
        for j in range(n):
            if c[i][j] != float(n):
                print("Error: bad number ({:.1f}) in result matrix ({},{})".format(c[i][j], i, j))
                sys.exit(0)

def run(func, n):
    """simply runs the program, more importantly we care about time*"""
    start_time = time.time()
    func(ga, gb, gc, n)
    end_time = time.time()
    check_result(gc, n)
    return end_time - start_time

def init(a, b, n):
    """set every elements in arrays as 1."""
    for i in range(n):
        for j in range(n):
            a[i][j] = 1.0
            b[i][j] = 1.0

def ijk(A, B, C, n):
    """ijk way"""
    for i in range(n):
        for j in range(n):
            sum = 0.0
            for k in range(n):
                sum += A[i][k] * B[k][j]
            C[i][j] += sum

def jik(A, B, C, n):
    """jik way"""
    for j in range(n):
        for i in range(n):
            sum = 0.0
            for k in range(n):
                sum += A[i][k] * B[k][j]
            C[i][j] += sum

def ikj(A, B, C, n):
    """ikj way"""
    for i in range(n):
        for k in range(n):
            r = A[i][k]
            for j in range(n):
                C[i][j] += r * B[k][j]

def kij(A, B, C, n):
    """kij way"""
    for k in range(n):
        for i in range(n):
            r = A[i][k]
            for j in range(n):
                C[i][j] += r * B[k][j]

def kji(A, B, C, n):
    """kji way"""
    for k in range(n):
        for j in range(n):
            r = B[k][j]
            for i in range(n):
                C[i][j] += A[i][k] * r

def jki(A, B, C, n):
    """jki way"""
    for j in range(n):
        for k in range(n):
            r = B[k][j]
            for i in range(n):
                C[i][j] += A[i][k] * r

def print_array(A, n):
    """Nope."""
    print("array")

def usage():
    """reminds that you didn't parse anything"""
    print("Usage: python mm.py <ijk|jik|jki|kji|kij|ikj>")

def main():
    if len(sys.argv) < 2:
        usage()
        return

    option = 0
    for i in range(3):
        if i < len(sys.argv[1]):
            option = (option << 1) + ord(sys.argv[1][i]) - ord('i')

    if option < 4 or option > 10:
        usage()
        return

    n = MAXN
    init(ga, gb, n)

    total_t = 0
    if option == 4:
        total_t = run(ijk, n)
    elif option == 5:
        total_t = run(ikj, n)
    elif option == 6:
        total_t = run(jik, n)
    elif option == 8:
        total_t = run(jki, n)
    elif option == 9:
        total_t = run(kij, n)
    elif option == 10:
        total_t = run(kji, n)
    
    print("It takes {:.3f} seconds to multiply two {}*{} double matrices.".format(total_t, n, n))

if __name__ == "__main__":
    main()