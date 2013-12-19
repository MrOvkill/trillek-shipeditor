package shipeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.trillek.shipeditor.Main;

public class WindowLoadModel
{
	private JFrame frame;
	private JPanel panel;
	private JButton resume;
	private JButton load;
	private JTextField mdl;
	private JTextField txt;
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
	public void redraw()
	{
		frame.repaint();
	}
	public void create() {
		frame = new JFrame();
		panel = new JPanel();
		mdl = new JTextField();
		txt = new JTextField();
		resume = new JButton("Resume");
		resume.setFont(new Font("Arial", 12, 12));
		resume.setText("Return");
		resume.setBounds(213, 10, 80, 30);
		resume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hide();
				Main.run = true;
				Main.run();
			}
		});
		load = new JButton("Load");
		load.setFont(new Font("Arial", 12, 12));
		load.setText("Load");
		load.setBounds(213, 40, 80, 30);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				hide();
				Main.ship.addModel(mdl.getText());
				Main.ship.addTexture(txt.getText());
				Main.ship.init();
				Main.run = true;
				Main.run();
			}
		});
		mdl.setBounds(10, 10, 200, 30);
		mdl.setText("Enter model name");
		txt.setBounds(10, 40, 200, 30);
		txt.setText("Enter texture name");
		panel.add(resume);
		panel.add(load);
		panel.add(mdl);
		panel.add(txt);
		panel.setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Menu");
		frame.setSize(300, 200);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
	}
}
