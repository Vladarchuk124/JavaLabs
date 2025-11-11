import java.util.List;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class lab7 {
    public static List<Integer> perfectUpTo(int limit) {
        IntPredicate isPerfect = n -> n > 1 && sumOfProperDivisors(n) == n;

        return IntStream.rangeClosed(1, limit)
                .filter(isPerfect)   
                .boxed()
                .collect(Collectors.toList());
    }

    private static int sumOfProperDivisors(int n) {
        if (n <= 1) return 0;

        int sqrt = (int) Math.sqrt(n);

        return IntStream.concat(
                    IntStream.of(1),
                    IntStream.rangeClosed(2, sqrt)
                             .filter(d -> n % d == 0)
                             .flatMap(d -> {
                                 int other = n / d;
                                 return (other == d) ? IntStream.of(d)
                                                     : IntStream.of(d, other);
                             })
               ).sum();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter border-number: ");
        int n = sc.nextInt();
        List<Integer> perfects = perfectUpTo(n);
        System.out.println("Perfect numbers up to " + n + ": " + perfects);
    }
}
