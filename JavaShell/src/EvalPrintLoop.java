import java.util.Scanner;

public class EvalPrintLoop {

	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		
		boolean exit = false;
		
		while (!exit) {
			System.out.print(">>");
			String nextLine = stdin.nextLine();
			Value result = Parser.evalCommand(nextLine);
			System.out.println(""+result);
			System.out.println("");
		}
		
		stdin.close();
		System.out.println("Good bye!");
		
	}
}
