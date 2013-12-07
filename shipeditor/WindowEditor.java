package shipeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.trillek.shipeditor.Main;

public class WindowEditor
{
	private JFrame frame;
	private JPanel panel;
	private JTextField nfield;
	private JTextField nfield2;
	private JButton create;
	public void create(String name, int x, int y)
	{
		frame = new JFrame();
		panel = new JPanel();
		nfield = new JTextField();
		nfield2 = new JTextField();
		nfield.setText("Author's name here");
		nfield2.setText("Ship's name here");
		nfield.setBounds(10, 10, 200, 25);
		nfield2.setBounds(10, 40, 200, 25);
		create = new JButton("Create");
		create.setFont(new Font("Arial", 12, 12));
		create.setText("Create");
		create.setBounds(213, 10, 80, 30);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(nfield.getText().equalsIgnoreCase("Author's name here") || nfield.getText().equalsIgnoreCase(""))
				{
					return;
				}
				else if(nfield2.getText().equalsIgnoreCase("Ship's name here") || nfield2.getText().equalsIgnoreCase(""))
				{
					return;
				}
				nfield.setText(nfield.getText().replace("\\", "-").replace("/", "-"));
				nfield2.setText(nfield2.getText().replace("\\", "-").replace("/", "-"));
				hide();
				Main.ship = new Ship(nfield.getText(), nfield2.getText());
				Main.ship.models.add("cube.obj");
				Main.ship.images.add("grey.png");
				Main.ship.models.add("half.obj");
				Main.ship.images.add("grey.png");
				Main.ship.models.add("cube.obj");
				Main.ship.images.add("cyan.png");
				Main.ship.models.add("half.obj");
				Main.ship.images.add("cyan.png");
				Main.ship.voxels.add(new float[] {0, 0, 0, 0, 0, 0, 0.6f, 0.6f, 0.6f, 0});
				Main game = new Main();
				game.start();
				Main.ship.init();
				game.initGL();
				game.run();
			}
		});
		panel.setLayout(null);
		panel.add(nfield);
		panel.add(nfield2);
		panel.add(create);
		frame.setResizable(false);
		frame.setTitle(name);
		frame.setSize(x, y);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
