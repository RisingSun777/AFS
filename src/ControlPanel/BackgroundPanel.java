package ControlPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BackgroundPanel extends JPanel implements ActionListener {
	public static final String[] backgroundlist_name = {"Main", "DauTruong", "Caesar", "ThongDiep", "ToOng", "MainScore", "Cover"};
	public static final String[] roundnamelist_name = {"ĐỐI ĐẦU", "ĐẤU TRƯỜNG", "MẬT MÃ CAESAR", "THÔNG ĐIỆP", "ONG XÂY TỔ", "SỨC MẠNH Đ.ĐỘI", "HỢP LỰC"};
	public final String background_action = "Background list";
	public final String roundname_action = "Roundname list";
	
	private ControlPanel controlpanel;
	private JComboBox<String> backgroundlist;
	private JComboBox<String> roundnamelist;
	private JButton backgroundbutton;
	private JButton roundnamebutton;
	
	public BackgroundPanel(ControlPanel controlpanel) {
		this.controlpanel = controlpanel;
		setLayout(new FlowLayout());
		
		this.setBorder(
                BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Background Manipulator"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		backgroundlist = new JComboBox(backgroundlist_name);
		backgroundlist.setSelectedIndex(0);
		//backgroundlist.addActionListener(this);
		
		roundnamelist = new JComboBox(roundnamelist_name);
		roundnamelist.setSelectedIndex(0);
		roundnamelist.setActionCommand(roundname_action);
		//roundnamelist.addActionListener(this);
		
		backgroundbutton = new JButton("Set background");
		backgroundbutton.setActionCommand(background_action);
		backgroundbutton.addActionListener(this);
		
		roundnamebutton = new JButton("Set roundname");
		roundnamebutton.setActionCommand(roundname_action);
		roundnamebutton.addActionListener(this);
		
		add(backgroundlist); add(backgroundbutton);
		add(roundnamelist); add(roundnamebutton);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case background_action:
			controlpanel.getMainframe().updateBackground((String) backgroundlist.getSelectedItem());
			break;
		case roundname_action:
			controlpanel.getMainframe().updateRoundName((String) roundnamelist.getSelectedItem());
			break;
		}
	}
}
