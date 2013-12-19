// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.trillek.shipeditor.Main;

public class WindowCreate
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
				Ship s = new Ship(nfield.getText(), nfield2.getText());
				s.addModel("cube.obj");
				s.addTexture("grey.png");
				s.Voxels.add(new float[] {0, 0, 0, 0, 0, 0, 0.6f, 0.6f, 0.6f, 0});
				Main.ship = s;
				Main game = new Main();
				game.start();
				game.initGL();
				Main.ship.init();
				Main.run();
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
