import javax.swing.JFrame;

public class Tester {
	public static FirstFrame frame1 = new FirstFrame();

	public static void main(String[] args) {
		frame1.setResizable(false);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
	}
}
