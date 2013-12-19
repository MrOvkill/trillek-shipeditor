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

public class WindowStart
{
	private JFrame frame;
	private JPanel panel;
	private JTextArea field;
	private JButton exit;
	private JButton create;
	private JButton load;
	private JButton update;
	public void create(String name, int x, int y)
	{
		frame = new JFrame();
		panel = new JPanel();
		field = new JTextArea();
		exit = new JButton("Quit");
		exit.setFont(new Font("Arial", 12, 12));
		exit.setText("Quit");
		exit.setBounds(213, 10, 80, 30);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		create = new JButton("Create");
		create.setFont(new Font("Arial", 12, 12));
		create.setText("Create");
		create.setBounds(213, 40, 80, 30);
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hide();
				WindowCreate wc = new WindowCreate();
				wc.create("Create Ship", 300, 100);
			}
		});
		load = new JButton("Load");
		load.setFont(new Font("Arial", 12, 12));
		load.setText("Load");
		load.setBounds(213, 70, 80, 30);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hide();
				WindowLoad wl = new WindowLoad();
				wl.create("Load Ship", 300, 100);
			}
		});
		update = new JButton("Update");
		update.setFont(new Font("Arial", 12, 12));
		update.setText("Update");
		update.setBounds(213, 100, 80, 30);
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hide();
				Updater.fetchUpdate();
				System.exit(0);
			}
		});
		field.setText("Welcome to the Ship Editor!\nClick new to create a new ship\nClick quit to exit");
		field.setBounds(10, 10, 200, 150);
		field.setEditable(false);
		panel.add(field);
		panel.add(exit);
		panel.add(create);
		panel.add(load);
		//panel.add(update);
		panel.setLayout(null);
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
