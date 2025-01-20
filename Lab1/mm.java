// Java matrix multiplication version 1.01 , revised by Daniel Yeung, adopted from Prof Cao adapting from https://csapp.cs.cmu.edu/3e/ics3/code/mem/matmult/mm.c */
public class mm {
    // constants initialization
    private static final int MAXN = 1024;
    private static double[][] ga = new double[MAXN][MAXN];
    private static double[][] gb = new double[MAXN][MAXN];
    private static double[][] gc = new double[MAXN][MAXN];

    // equivalent to test_funct from C
    @FunctionalInterface
    interface TestFunction {
        void test(double[][] A, double[][] B, double[][] C, int n);
    }

    // stubbornly included function, finds out if there is wrong multiplication the computer has made
    private static void checkResult(double[][] c, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (c[i][j] != (double) n) {
                    System.out.printf("Error: bad number (%f) in result matrix (%d,%d)%n", c[i][j], i, j);
                    System.exit(0);
                }
            }
        }
    }

    // the soul of this program, precisely capturing time required for the matrix multiplications
    private static double run(TestFunction f, int n) {
        long startTime = System.nanoTime();
        f.test(ga, gb, gc, n);
        long endTime = System.nanoTime();
        checkResult(gc, n);
        return (endTime - startTime) / 1_000_000_000.0;  // 1 ns  = 10^-9 s
    }

    // no surprises! All elements for the arrays are 1
    private static void init(double[][] a, double[][] b, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = 1.0;
                b[i][j] = 1.0;
            }
        }
    }

    // Daniel doesn't like debugging, hence, the printArray is omitted
    // Please implement it yourself if you intend to have one.
    private static void printArray(double[][] a, int n){
        // Refer to Recording 1 : 16:07 - 16:40
        System.out.printf("We do not debug algorithms - Professor Cao 16/1/2025 08:46:17");
    }

    // pov : you tried to run this program without giving it params
    private static void usage() {
        System.out.println("Usage: java mm <ijk|jik|jki|kji|kij|ikj>");
    }

    // the six similar pieces of code with permutations of ijk
    private static void ijk(double[][] A, double[][] B, double[][] C, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] += sum;
            }
        }
    }

    private static void jik(double[][] A, double[][] B, double[][] C, int n) {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += A[i][k] * B[k][j];
                }
                C[i][j] += sum;
            }
        }
    }

    private static void ikj(double[][] A, double[][] B, double[][] C, int n) {
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                double r = A[i][k];
                for (int j = 0; j < n; j++) {
                    C[i][j] += r * B[k][j];
                }
            }
        }
    }

    private static void kij(double[][] A, double[][] B, double[][] C, int n) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                double r = A[i][k];
                for (int j = 0; j < n; j++) {
                    C[i][j] += r * B[k][j];
                }
            }
        }
    }

    private static void kji(double[][] A, double[][] B, double[][] C, int n) {
        for (int k = 0; k < n; k++) {
            for (int j = 0; j < n; j++) {
                double r = B[k][j];
                for (int i = 0; i < n; i++) {
                    C[i][j] += A[i][k] * r;
                }
            }
        }
    }

    private static void jki(double[][] A, double[][] B, double[][] C, int n) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                double r = B[k][j];
                for (int i = 0; i < n; i++) {
                    C[i][j] += A[i][k] * r;
                }
            }
        }
    }


    // main part, has some slight variations from og code, please revise if buggy

    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
            return;
        }

        char option = 0;
        for (int i = 0; i < 3 && i < args[0].length(); i++) {
            option = (char) ((option << 1) + args[0].charAt(i) - 'i');
        }

        if (option < 4 || option > 10) {
            usage();
            return;
        }

        int n = MAXN;
        init(ga, gb, n);

        double total_t;
        switch (option) {
            case 4:
                total_t = run(mm::ijk, n);
                break;
            case 5:
                total_t = run(mm::ikj, n);
                break;
            case 6:
                total_t = run(mm::jik, n);
                break;
            case 8:
                total_t = run(mm::jki, n);
                break;
            case 9:
                total_t = run(mm::kij, n);
                break;
            case 10:
                total_t = run(mm::kji, n);
                break;
            default:
                usage();
                return;
        }
        System.out.printf("It takes %.3f seconds to multiply two %d*%d double matrices.%n", total_t, n, n);
    }
}