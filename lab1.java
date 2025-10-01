import java.util.Scanner;

public class lab1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter border-number: ");
        int n = sc.nextInt();

        System.out.println("Perfect numbers from 1 to " + n + ":");

        for (int i = 2; i <= n; i++) {
            if (isPerfect(i)) {
                System.out.println(i);
            }
        }

        sc.close();
    }

    public static boolean isPerfect(int num) {
        int sum = 1; 
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                sum += i;
            }
        }
        return sum == num && num != 1;
    }
}
