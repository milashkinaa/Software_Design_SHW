// used https://metanit.com/java

import java.util.Scanner;

// вводим кол-во чисел, затем выводим получившиеся числа тут же
public class Main {
	public static void main(String[] args) {
	    Scanner in = new Scanner(System.in);
        System.out.print("Введите количество чисел: ");
	    int n = in.nextInt();
	    for (int i = 0; i <= n; ++i) {
	        System.out.println(fibonacci(i));
	    }
	}
	
// метод, в котором мы вычисляем числа Фибоначчи
	public static int fibonacci(int num) {
	    if (num == 1 || num == 0) {
	        return num;
	    }
	    return fibonacci(num - 1) + fibonacci(num - 2);
	}
}