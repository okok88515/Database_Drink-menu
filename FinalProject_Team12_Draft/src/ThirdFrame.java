import javax.swing.*;
import java.awt.Font;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Color;

public class ThirdFrame extends JFrame {
	private JTextArea textArea;
	private JLabel label;
	private JScrollPane scrollPane;
	private ArrayList<JButton> buttonGroup = new ArrayList<JButton>();
	private ArrayList<DrinkingShop> shopList;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button9_1;
	private Statement statement;
	private String tea;
	protected static FourthFrame frame4;
	private DrinkingShop drinkingShop = null;

	private static final String server = "jdbc:mysql://140.119.19.73:3315/";
	private static final String database = "tuegroup12";
	private static final String url = server + database + "?useSSL=false&characterEncoding=utf-8"; // changeEncoding
	private static final String username = "tuegroup12";
	private static final String password = "sbg6514";

	public ThirdFrame(String tea) {
		this.tea = tea;
		setTitle("現在是飲料時間");
		createLabel();
		createTextArea();
		initialize();
		createButtonGroup();
		try {
			setButton();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(null);
		setSize(700, 500);
	}

	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		textArea.setEditable(false);
		scrollPane.setBounds(17, 37, 500, 300);
		this.getContentPane().add(scrollPane);
	}

	private void initialize() {
		button9_1 = new JButton("回首頁");
		button9_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tester.frame1.setVisible(true);
				ThirdFrame.this.setVisible(false);
			}
		});
		button9_1.setBounds(395, 401, 117, 38);
		getContentPane().add(button9_1);

		button1 = new JButton("酪農");
		button1.setFont(new Font("Serif", Font.PLAIN, 13));
		button1.setBackground(new Color(255, 228, 225));
		button1.setBounds(541, 77, 117, 29);
		this.getContentPane().add(button1);

		button2 = new JButton("50嵐");
		button2.setFont(new Font("Serif", Font.PLAIN, 13));
		button2.setBackground(new Color(255, 228, 225));
		button2.setBounds(541, 117, 117, 29);
		this.getContentPane().add(button2);

		button3 = new JButton("龍角");
		button3.setFont(new Font("Serif", Font.PLAIN, 13));
		button3.setBackground(new Color(255, 228, 225));
		button3.setBounds(541, 157, 117, 29);
		this.getContentPane().add(button3);

		button4 = new JButton("政大茶亭");
		button4.setFont(new Font("Serif", Font.PLAIN, 13));
		button4.setBackground(new Color(255, 228, 225));
		button4.setBounds(541, 197, 117, 29);
		this.getContentPane().add(button4);

		button5 = new JButton("清心福全");
		button5.setFont(new Font("Serif", Font.PLAIN, 13));
		button5.setBackground(new Color(255, 228, 225));
		button5.setBounds(541, 237, 117, 29);
		this.getContentPane().add(button5);

		button6 = new JButton("迷客夏");
		button6.setFont(new Font("Serif", Font.PLAIN, 13));
		button6.setBackground(new Color(255, 228, 225));
		button6.setBounds(541, 277, 117, 29);
		this.getContentPane().add(button6);

		button7 = new JButton("CowBanana牛香蕉");
		button7.setFont(new Font("Serif", Font.PLAIN, 13));
		button7.setBackground(new Color(255, 228, 225));
		button7.setBounds(541, 317, 117, 29);
		this.getContentPane().add(button7);

		button8 = new JButton("可不可熟成紅茶");
		button8.setFont(new Font("Serif", Font.PLAIN, 13));
		button8.setBackground(new Color(255, 228, 225));
		button8.setBounds(541, 37, 117, 29);
		this.getContentPane().add(button8);

		button9 = new JButton("評論區");
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame4 = new FourthFrame(drinkingShop);
				frame4.setDrinkingShop(drinkingShop);
				frame4.setResizable(false);
				frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame4.setVisible(true);
				ThirdFrame.this.setVisible(false);
			}
		});
		button9.setBounds(541, 401, 117, 38);
		this.getContentPane().add(button9);

	}

	public void createLabel() {
		label = new JLabel("");
		label.setFont(new Font("Serif", Font.PLAIN, 14));
		label.setBounds(219, 10, 221, 16);
		this.getContentPane().add(label);
	}

	public void createButtonGroup() {
		buttonGroup.add(button1);
		buttonGroup.add(button2);
		buttonGroup.add(button3);
		buttonGroup.add(button4);
		buttonGroup.add(button5);
		buttonGroup.add(button6);
		buttonGroup.add(button7);
		buttonGroup.add(button8);
	}

	public void setDrinkingShop(DrinkingShop shop) {
		this.drinkingShop = shop;
	}

	public void setLabel(String message) {
		this.label.setText(message);
	}

	public void setTextArea(String string) {
		this.textArea.setText(string);
	}

	public void setButton() throws SQLException {
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			this.statement = connection.createStatement();
			this.shopList = new ShopList(this.statement).getShopList();
			for (JButton button : this.buttonGroup) {
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						for (DrinkingShop shop : ThirdFrame.this.shopList) {
							if (button.getText().equals(shop.getName())) {
								drinkingShop = shop;
								break;
							}
						}
						label.setText(drinkingShop.getName());
						try {
							if (drinkingShop.getName().equals("50嵐") || drinkingShop.getName().equals("清心福全")) {
								textArea.setText(drinkingShop.basicInfo() + drinkingShop.teaInfo(tea)
										+ drinkingShop.ingredientsInfo());
							} else {
								textArea.setText(drinkingShop.basicInfo() + drinkingShop.teaInfo(tea)
										+ drinkingShop.specialIngredientsInfo());
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
