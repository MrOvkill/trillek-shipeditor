package shipeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.trillek.shipeditor.Main;

public class WindowLoad
{
	private JFrame frame;
	private JPanel panel;
	private JTextField nfield2;
	private JButton load;
	public void create(String name, int x, int y)
	{
		frame = new JFrame();
		panel = new JPanel();
		nfield2 = new JTextField();
		nfield2.setText("Ship's name here");
		nfield2.setBounds(10, 10, 200, 25);
		load = new JButton("Load");
		load.setFont(new Font("Arial", 12, 12));
		load.setText("Create");
		load.setBounds(213, 10, 80, 30);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(nfield2.getText().equalsIgnoreCase("Ship's name here") || nfield2.getText().equalsIgnoreCase(""))
				{
					return;
				}
				nfield2.setText(nfield2.getText().replace("\\", "-").replace("/", "-"));
				hide();
				//WindowLoadModel wlm = new WindowLoadModel();
				//wlm.create("Load Model", 300, 200);
				Main game = new Main();
				game.start();
				Main.ship = Main.load(nfield2.getText());
				Main.ship.init();
				game.initGL();
				game.run();
			}
		});
		panel.setLayout(null);
		panel.add(nfield2);
		panel.add(load);
		frame.setResizable(false);
		frame.setTitle(name);
		frame.setSize(x, y);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void hide()
	{
		if(frame.isVisible())
		{
			frame.setVisible(false);
		}
	}
	public void show()
	{
		if(!frame.isVisible())
		{
			frame.setVisible(true);
		}
	}
}
