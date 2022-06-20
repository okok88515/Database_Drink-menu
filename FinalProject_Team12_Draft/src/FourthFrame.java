import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class FourthFrame extends JFrame {

	private static final String server = "jdbc:mysql://140.119.19.73:3315/";
	private static final String database = "tuegroup12";
	private static final String url = server + database + "?useSSL=false&characterEncoding=utf-8"; // changeEncoding
	private static final String username = "tuegroup12";
	private static final String password = "sbg6514";
	private JTextArea textArea = new JTextArea();
	private JLabel resultLabel, titleLabel;
	private JScrollPane scrollPane;
	private JButton btnNewButton1, btnNewButton2;
	private JTextField startextField, reviewtextField;
	private DrinkingShop shop;

	public FourthFrame(DrinkingShop shop) {
		setTitle("現在是飲料時間");
		createTextArea();
		setTextArea(shop);
		createButton();
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("評星");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 359, 78, 22);
		getContentPane().add(lblNewLabel);

		startextField = new JTextField();
		startextField.setBounds(82, 360, 65, 21);
		getContentPane().add(startextField);
		startextField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("評語");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(157, 359, 78, 22);
		getContentPane().add(lblNewLabel_1);

		reviewtextField = new JTextField();
		reviewtextField.setColumns(10);
		reviewtextField.setBounds(235, 360, 235, 21);
		getContentPane().add(reviewtextField);

		resultLabel = new JLabel("");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setBounds(82, 405, 235, 22);
		getContentPane().add(resultLabel);

		titleLabel = new JLabel(shop.getName() + "評論區");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(235, 0, 175, 22);
		getContentPane().add(titleLabel);
		setSize(700, 500);
	}

	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 24, 670, 300);
		textArea.setEditable(false);
		textArea.setBounds(10, 24, 670, 300);
		this.getContentPane().add(scrollPane);
	}

	public void setTextArea(DrinkingShop shop) {
		try {
			textArea.setText(shop.reviewInfo());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createButton() {
		btnNewButton1 = new JButton("新增評論");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (FourthFrame.this.startextField.getText().equals("")
						|| Double.parseDouble(FourthFrame.this.startextField.getText()) <= 0
						|| Double.parseDouble(FourthFrame.this.startextField.getText()) > 5
						|| (Double.parseDouble(FourthFrame.this.startextField.getText()) * 10) % 10 != 0) {
					JOptionPane.showMessageDialog(null, "評星須為1-5之間的整數", "錯誤訊息", JOptionPane.WARNING_MESSAGE);
					FourthFrame.this.startextField.setText("");
				} else {
					try (Connection conn = DriverManager.getConnection(url, username, password)) {
						Statement stat = conn.createStatement();
						String query;
						query = String.format("INSERT INTO tuegroup12.評論 (店家, 評星, 評語) VALUES ('%s', %s, '%s')",
								shop.getName(), startextField.getText(), reviewtextField.getText());
						stat.execute(query);
						resultLabel.setText("成功新增評論！");
						FourthFrame.this.setTextArea(shop);
						FourthFrame.this.startextField.setText("");
						FourthFrame.this.reviewtextField.setText("");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		});
		btnNewButton1.setBounds(395, 405, 101, 29);
		this.getContentPane().add(btnNewButton1);

		btnNewButton2 = new JButton("回上一頁");
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SecondFrame.frame.setVisible(true);
				FourthFrame.this.setVisible(false);
			}
		});
		btnNewButton2.setBounds(537, 405, 101, 29);
		this.getContentPane().add(btnNewButton2);
	}

	public void setDrinkingShop(DrinkingShop shop) {
		this.shop = shop;
	}
}
