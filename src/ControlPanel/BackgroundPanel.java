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
	private JCheckBox playclip;
	
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
		
		playclip = new JCheckBox("Play rule clips");
		playclip.setSelected(true);
		
		add(backgroundlist); add(backgroundbutton);
		add(roundnamelist); add(roundnamebutton);
		add(playclip);
	}
	
	private void playClip() {
		controlpanel.getMainframe().getSm().stopAll();
		
		switch((String) roundnamelist.getSelectedItem()) {
		case "ĐỐI ĐẦU":
			VideoManager.VideoMain.location_current = VideoManager.VideoMain.location_doidau;
			break;
		case "ONG XÂY TỔ":
			VideoManager.VideoMain.location_current = VideoManager.VideoMain.location_ongxayto;
			break;
		case "HỢP LỰC":
			VideoManager.VideoMain.location_current = VideoManager.VideoMain.location_hopluc;
			break;
		case "ĐẤU TRƯỜNG":
			VideoManager.VideoMain.location_current = VideoManager.VideoMain.location_dautruong;
			break;
		}
		//VideoManager.VideoMain aaa = new VideoManager.VideoMain();
		//aaa.test();
		VideoManager.VideoMain.wakeUp();
		new Thread((new VideoManager.VideoMain())).start();
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case background_action:
			controlpanel.getMainframe().updateBackground((String) backgroundlist.getSelectedItem());
			break;
		case roundname_action:
			if(playclip.isSelected())
				playClip();
			controlpanel.getMainframe().updateRoundName((String) roundnamelist.getSelectedItem());
			break;
		}
	}
}
