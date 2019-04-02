import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Graphics;
/*
 *
 * Beschreibung/Description
 *
 * @version 1.0, 25.01.2017
 * @author
 */

public class ImgPro extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Anfang Attribute/Start attributes
	private JButton jButtonLoad = new JButton();
	private JRadioButton JRadioButtonSepia = new JRadioButton();
	private JRadioButton JRadioButtonBlackWhite = new JRadioButton();
	private JButton jButtonSave = new JButton();
	private JCheckBox JCheckBoxColor = new JCheckBox();
	private JRadioButton JRadioButtonHeat = new JRadioButton();
	private JRadioButton JRadioButtonRed = new JRadioButton();
	private JRadioButton JRadioButtonGreen = new JRadioButton();
	private JRadioButton JRadioButtonBlue = new JRadioButton();
	private JCheckBox JCheckBoxBlur = new JCheckBox();
	private JCheckBox JCheckBoxMotionBlur = new JCheckBox();
	private JRadioButton JRadioButtonSoft1 = new JRadioButton();
	private JRadioButton JRadioButtonMedium1 = new JRadioButton();
	private JRadioButton JRadioButtonStrong1 = new JRadioButton();
	private JCheckBox JCheckBoxEdge = new JCheckBox();
	private JRadioButton JRadioButtonOldComic = new JRadioButton();
	private JCheckBox JCheckBoxPixelate = new JCheckBox();
	private JCheckBox JCheckBoxPixelateHard = new JCheckBox();
	private JCheckBox JCheckBox4Small = new JCheckBox();
	private JCheckBox JCheckBoxVortex = new JCheckBox();
	private JCheckBox JCheckBoxInvert = new JCheckBox();
	private JCheckBox JCheckBoxContrast = new JCheckBox();
	private JCheckBox JCheckBoxContrastHard = new JCheckBox();
	private JCheckBox JCheckBoxGrain = new JCheckBox();
	private JCheckBox JCheckBoxGrainBars = new JCheckBox();
	private JCheckBox JCheckBoxGrainColor = new JCheckBox();
	private JCheckBox JCheckBoxFlip = new JCheckBox();
	private JCheckBox JRadioButtonHori = new JCheckBox();
	private JCheckBox JRadioButtonVerti = new JCheckBox();
	private JCheckBox JCheckBoxHighlight = new JCheckBox();
	private JRadioButton JRadioButtonSpot = new JRadioButton();
	private JRadioButton JRadioButtonFrameFade = new JRadioButton();
	private JTextField JTextFieldXSpot = new JTextField();
	private JTextField JTextFieldDegree = new JTextField();
	private JTextField JTextFieldYSpot = new JTextField();
	private JTextField JTextFieldRadius = new JTextField();
	private JTextField JTextFieldEdge = new JTextField();
	private JTextField JTextFieldCourse = new JTextField();
	private JTextField JTextFieldInt = new JTextField();
	private JTextField JTextFieldRows = new JTextField();
	private JCheckBox JCheckBoxEffects = new JCheckBox();
	private JLabel jLabel5 = new JLabel();
	private JLabel jLabel6 = new JLabel();
	private JLabel jLabel7 = new JLabel();
	private JLabel jLabel8 = new JLabel();
	private JLabel jLabel9 = new JLabel();
	private JLabel jLabel10 = new JLabel();
	private JLabel jLabel11 = new JLabel();
	private JLabel jLabel12 = new JLabel();
	private JLabel jLabel13 = new JLabel();
	private JLabel jLabel14 = new JLabel();
	private JLabel jLabel15 = new JLabel();
	private JLabel jLabel16 = new JLabel();
	private JButton jButtonUndo = new JButton();
	private JButton jButtonHelp = new JButton();
	private JButton jButtonInfo = new JButton();
	private JButton jButtonApply = new JButton();
	private JButton jButtonExample = new JButton();
	private MyCanvasPanel paintPanel = new MyCanvasPanel();
	int postionx = 0; // the x position of the window
	int postiony = 0; // the y position of the window
	public boolean drawlarge1 = false;
	public boolean drawlarge2 = false;
	public boolean drawlarge3 = false;
	public boolean drawlarge4 = false;
	String imgPath;
	BufferedImage img = null;

	int imgWidth = 0;
	int imgHeight = 0;
	int[][][] imgData; // 3D: height x width x rgb
	int[][][][] backup; // array for the undo funktion
	int undoCounter = 0; // counts how many times undo was pressed
	int backupCounter = 0; // counts the length of the actuall backpupdata

	double ratio; // Variablen zur richtigen Darstellung auf der Gui/ variables
					// to ensure correct display on interface
	int newHeight;
	int newWidth;

	// Ende Attribute/ end of attributes

	public ImgPro() {
		// Frame-Initialisierung/ initializing frames
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 1025;
		int frameHeight = 600;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2; // to set the window in the
													// middle of the screen
		int y = (d.height - getSize().height) / 2;
		this.postionx = x; // to tell the dialog windows, where to appear
		this.postiony = y;
		setLocation(x, y);
		setTitle("IMG PRO");
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);

		// Anfang Komponenten /start components

		jButtonLoad.setBounds(8, 152, 150, 25);
		jButtonLoad.setText("Load Image");
		jButtonLoad.setMargin(new Insets(2, 2, 2, 2));
		jButtonLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonLoad_ActionPerformed(evt);
			}
		});
		cp.add(jButtonLoad);
		jButtonSave.setBounds(8, 216, 150, 25);
		jButtonSave.setText("Save");
		jButtonSave.setMargin(new Insets(2, 2, 2, 2));
		jButtonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonSave_ActionPerformed(evt);
			}
		});
		cp.add(jButtonSave);
		jButtonUndo.setBounds(8, 312, 150, 25);
		jButtonUndo.setText("Undo");
		jButtonUndo.setMargin(new Insets(2, 2, 2, 2));
		jButtonUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonUndo_ActionPerformed(evt);
			}
		});
		cp.add(jButtonUndo);
		jButtonHelp.setBounds(824, 5, 75, 25);
		jButtonHelp.setText("Help");
		jButtonHelp.setMargin(new Insets(2, 2, 2, 2));
		jButtonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonHelp_ActionPerformed(evt);
			}
		});
		cp.add(jButtonHelp);
		jButtonInfo.setBounds(912, 5, 75, 25);
		jButtonInfo.setText("Info");
		jButtonInfo.setMargin(new Insets(2, 2, 2, 2));
		jButtonInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonInfo_ActionPerformed(evt);
			}
		});
		cp.add(jButtonInfo);
		jButtonApply.setBounds(8, 280, 150, 25);
		jButtonApply.setText("Apply filters");
		jButtonApply.setMargin(new Insets(2, 2, 2, 2));
		jButtonApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonApply_ActionPerformed(evt);
			}
		});
		cp.add(jButtonApply);
		jButtonExample.setBounds(8, 184, 150, 28);
		jButtonExample.setText("Load sample");
		jButtonExample.setMargin(new Insets(2, 2, 2, 2));
		jButtonExample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jButtonExample_ActionPerformed(evt);
			}
		});
		cp.add(jButtonExample);
		JCheckBoxColor.setBounds(8, 352, 110, 20);
		JCheckBoxColor.setText("Color filters");
		ActionListener actionListenerColor = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {

					JRadioButtonSepia.setVisible(true);
					JRadioButtonBlackWhite.setVisible(true);
					JRadioButtonHeat.setVisible(true);
					JRadioButtonRed.setVisible(true);
					JRadioButtonGreen.setVisible(true);
					JRadioButtonBlue.setVisible(true);
					JRadioButtonOldComic.setVisible(true);
				} else {

					JRadioButtonSepia.setVisible(false);
					JRadioButtonBlackWhite.setVisible(false);
					JRadioButtonHeat.setVisible(false);
					JRadioButtonRed.setVisible(false);
					JRadioButtonGreen.setVisible(false);
					JRadioButtonBlue.setVisible(false);
					JRadioButtonOldComic.setVisible(false);
				}
			}
		};
		JCheckBoxColor.addActionListener(actionListenerColor);
		cp.add(JCheckBoxColor);
		JRadioButtonSepia.setBounds(20, 424, 100, 20);
		JRadioButtonSepia.setText("Sepia");
		JRadioButtonSepia.setOpaque(false);
		JRadioButtonSepia.setVisible(false);
		cp.add(JRadioButtonSepia);
		JRadioButtonBlackWhite.setBounds(20, 392, 124, 20);
		JRadioButtonBlackWhite.setText("Black and white");
		JRadioButtonBlackWhite.setOpaque(false);
		JRadioButtonBlackWhite.setVisible(false);
		cp.add(JRadioButtonBlackWhite);
		JRadioButtonHeat.setBounds(20, 456, 100, 20);
		JRadioButtonHeat.setText("Heat");
		JRadioButtonHeat.setOpaque(false);
		JRadioButtonHeat.setVisible(false);
		cp.add(JRadioButtonHeat);
		JRadioButtonRed.setBounds(144, 392, 100, 20);
		JRadioButtonRed.setText("Red");
		JRadioButtonRed.setOpaque(false);
		JRadioButtonRed.setVisible(false);
		cp.add(JRadioButtonRed);
		JRadioButtonGreen.setBounds(144, 424, 100, 20);
		JRadioButtonGreen.setText("Green");
		JRadioButtonGreen.setOpaque(false);
		JRadioButtonGreen.setVisible(false);
		cp.add(JRadioButtonGreen);
		JRadioButtonBlue.setBounds(144, 456, 100, 20);
		JRadioButtonBlue.setText("Blue");
		JRadioButtonBlue.setOpaque(false);
		JRadioButtonBlue.setVisible(false);
		cp.add(JRadioButtonBlue);
		JRadioButtonOldComic.setBounds(20, 488, 100, 20);
		JRadioButtonOldComic.setText("Old Comic");
		JRadioButtonOldComic.setOpaque(false);
		JRadioButtonOldComic.setVisible(false);
		cp.add(JRadioButtonOldComic);

		JCheckBoxEffects.setBounds(222, 352, 110, 20);
		JCheckBoxEffects.setText("Effects");
		ActionListener actionListenerEffects = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {

					JCheckBoxBlur.setVisible(true);
					JCheckBoxMotionBlur.setVisible(true);
					if (JCheckBoxBlur.isSelected()) {
						JRadioButtonSoft1.setVisible(true);
						JRadioButtonMedium1.setVisible(true);
						JRadioButtonStrong1.setVisible(true);

					}
					if (JCheckBoxMotionBlur.isSelected()) {
						JRadioButtonSoft1.setVisible(true);
						JRadioButtonMedium1.setVisible(true);
						JRadioButtonStrong1.setVisible(true);

					}
					JCheckBoxPixelate.setVisible(true);
					JCheckBoxEdge.setVisible(true);
					JCheckBoxPixelateHard.setVisible(true);
					JCheckBox4Small.setVisible(true);
					JCheckBoxVortex.setVisible(true);
					JCheckBoxInvert.setVisible(true);
					JCheckBoxContrast.setVisible(true);
					JCheckBoxContrastHard.setVisible(true);
					JCheckBoxGrain.setVisible(true);
					JCheckBoxGrainBars.setVisible(true);
					if (JCheckBoxGrain.isSelected()) {
						JCheckBoxGrainColor.setVisible(true);
						JTextFieldInt.setVisible(true);
						jLabel14.setVisible(true);

					}
					if (JCheckBoxGrainBars.isSelected()) {
						JCheckBoxGrainColor.setVisible(true);
						JTextFieldInt.setVisible(true);
						JTextFieldRows.setVisible(true);
						jLabel14.setVisible(true);
						jLabel15.setVisible(true);
					}
				} else {

					JCheckBoxBlur.setVisible(false);
					JRadioButtonSoft1.setVisible(false);
					JRadioButtonMedium1.setVisible(false);
					JRadioButtonStrong1.setVisible(false);
					JCheckBoxEdge.setVisible(false);
					JCheckBoxPixelate.setVisible(false);
					JCheckBoxPixelateHard.setVisible(false);
					JCheckBox4Small.setVisible(false);
					JCheckBoxVortex.setVisible(false);
					JCheckBoxInvert.setVisible(false);
					JCheckBoxContrast.setVisible(false);
					JCheckBoxContrastHard.setVisible(false);
					JCheckBoxGrain.setVisible(false);
					JCheckBoxGrainBars.setVisible(false);

					JCheckBoxGrainColor.setVisible(false);
					JTextFieldInt.setVisible(false);
					JTextFieldRows.setVisible(false);
					jLabel14.setVisible(false);
					jLabel15.setVisible(false);
					JCheckBoxMotionBlur.setVisible(false);
					JTextFieldDegree.setVisible(false);
					jLabel16.setVisible(false);

				}
			}
		};
		JCheckBoxEffects.addActionListener(actionListenerEffects);
		cp.add(JCheckBoxEffects);
		JCheckBoxBlur.setBounds(235, 384, 100, 20);
		JCheckBoxBlur.setText("Blur");
		JCheckBoxBlur.setOpaque(false);
		ActionListener actionListenerBlur = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {
					JRadioButtonSoft1.setVisible(true);
					JRadioButtonMedium1.setVisible(true);
					JRadioButtonStrong1.setVisible(true);
				} else if (JCheckBoxMotionBlur.isSelected() == false) {
					JRadioButtonSoft1.setVisible(false);
					JRadioButtonMedium1.setVisible(false);
					JRadioButtonStrong1.setVisible(false);
				}
			}
		};
		JCheckBoxBlur.addActionListener(actionListenerBlur);
		JCheckBoxBlur.setVisible(false);
		cp.add(JCheckBoxBlur);

		JCheckBoxMotionBlur.setBounds(235, 416, 100, 20);
		JCheckBoxMotionBlur.setText("Motion Blur");
		JCheckBoxMotionBlur.setOpaque(false);
		ActionListener actionListenerMotionBlur = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {
					JRadioButtonSoft1.setVisible(true);
					JRadioButtonMedium1.setVisible(true);
					JRadioButtonStrong1.setVisible(true);
					JTextFieldDegree.setVisible(true);
					jLabel16.setVisible(true);
				} else if (JCheckBoxBlur.isSelected() == false) {
					JRadioButtonSoft1.setVisible(false);
					JRadioButtonMedium1.setVisible(false);
					JRadioButtonStrong1.setVisible(false);
					JTextFieldDegree.setVisible(false);
					jLabel16.setVisible(false);
				}
				else if(JCheckBoxBlur.isSelected() == true)
				{
					JTextFieldDegree.setVisible(false);
					jLabel16.setVisible(false);

				}
			}
		};
		JCheckBoxMotionBlur.addActionListener(actionListenerMotionBlur);
		JCheckBoxMotionBlur.setVisible(false);
		cp.add(JCheckBoxMotionBlur);
		JRadioButtonSoft1.setBounds(255, 440, 100, 20);
		JRadioButtonSoft1.setText("Soft");
		JRadioButtonSoft1.setOpaque(false);
		JRadioButtonSoft1.setVisible(false);
		cp.add(JRadioButtonSoft1);
		JRadioButtonMedium1.setBounds(255, 460, 100, 20);
		JRadioButtonMedium1.setText("Medium");
		JRadioButtonMedium1.setOpaque(false);
		JRadioButtonMedium1.setVisible(false);
		cp.add(JRadioButtonMedium1);
		JRadioButtonStrong1.setBounds(255, 480, 100, 20);
		JRadioButtonStrong1.setText("Strong");
		JRadioButtonStrong1.setOpaque(false);
		JRadioButtonStrong1.setVisible(false);
		cp.add(JRadioButtonStrong1);
		JTextFieldDegree.setBounds(300, 510, 30, 20);
		JTextFieldDegree.setText("");
		JTextFieldDegree.setVisible(false);
		cp.add(JTextFieldDegree);
		jLabel16.setBounds(245, 510, 80, 20);
		jLabel16.setText("degree:");
		jLabel16.setVisible(false);
		cp.add(jLabel16);
		
		JCheckBoxEdge.setBounds(360, 384, 100, 20);
		JCheckBoxEdge.setText("Edge");
		JCheckBoxEdge.setOpaque(false);
		JCheckBoxEdge.setVisible(false);
		cp.add(JCheckBoxEdge);
		JCheckBoxInvert.setBounds(360, 416, 100, 20);
		JCheckBoxInvert.setText("Invert");
		JCheckBoxInvert.setOpaque(false);
		JCheckBoxInvert.setVisible(false);
		cp.add(JCheckBoxInvert);
		JCheckBoxContrast.setBounds(360, 448, 100, 20);
		JCheckBoxContrast.setText("Contrast");
		JCheckBoxContrast.setOpaque(false);
		JCheckBoxContrast.setVisible(false);
		cp.add(JCheckBoxContrast);
		JCheckBoxContrastHard.setBounds(360, 480, 120, 20);
		JCheckBoxContrastHard.setText("Contrast hard");
		JCheckBoxContrastHard.setOpaque(false);
		JCheckBoxContrastHard.setVisible(false);
		cp.add(JCheckBoxContrastHard);

		JCheckBoxPixelate.setBounds(488, 384, 100, 20);
		JCheckBoxPixelate.setText("Pixelate");
		JCheckBoxPixelate.setOpaque(false);
		JCheckBoxPixelate.setVisible(false);
		cp.add(JCheckBoxPixelate);
		JCheckBoxPixelateHard.setBounds(488, 416, 110, 20);
		JCheckBoxPixelateHard.setText("Pixelate hard");
		JCheckBoxPixelateHard.setOpaque(false);
		JCheckBoxPixelateHard.setVisible(false);
		cp.add(JCheckBoxPixelateHard);
		JCheckBox4Small.setBounds(488, 448, 110, 20);
		JCheckBox4Small.setText("4 small");
		JCheckBox4Small.setOpaque(false);
		JCheckBox4Small.setVisible(false);
		cp.add(JCheckBox4Small);
		JCheckBoxVortex.setBounds(488, 480, 110, 20);
		JCheckBoxVortex.setText("Vortex");
		JCheckBoxVortex.setOpaque(false);
		JCheckBoxVortex.setVisible(false);
		cp.add(JCheckBoxVortex);
		JCheckBoxGrain.setBounds(592, 384, 100, 20);
		JCheckBoxGrain.setText("Grain");
		JCheckBoxGrain.setOpaque(false);
		JCheckBoxGrain.setVisible(false);
		ActionListener actionListenerGrain = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {
					JCheckBoxGrainColor.setVisible(true);
					JTextFieldInt.setVisible(true);
					jLabel14.setVisible(true);
				} else if (JCheckBoxGrainBars.isSelected() == false) {
					JCheckBoxGrainColor.setVisible(false);
					JTextFieldInt.setVisible(false);
					jLabel14.setVisible(false);
				}
			}
		};
		JCheckBoxGrain.addActionListener(actionListenerGrain);
		cp.add(JCheckBoxGrain);
		JCheckBoxGrainBars.setBounds(592, 416, 110, 20);
		JCheckBoxGrainBars.setText("Grain bars");
		JCheckBoxGrainBars.setOpaque(false);
		JCheckBoxGrainBars.setVisible(false);
		ActionListener actionListenerGrainBars = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {
					JCheckBoxGrainColor.setVisible(true);
					JTextFieldInt.setVisible(true);
					JTextFieldRows.setVisible(true);
					jLabel14.setVisible(true);
					jLabel15.setVisible(true);
				} else if (JCheckBoxGrain.isSelected() == false) {
					JCheckBoxGrainColor.setVisible(false);
					JTextFieldInt.setVisible(false);
					JTextFieldRows.setVisible(false);
					jLabel14.setVisible(false);
					jLabel15.setVisible(false);
				} else {
					JTextFieldRows.setVisible(false);
					jLabel15.setVisible(false);
				}
			}
		};
		JCheckBoxGrainBars.addActionListener(actionListenerGrainBars);
		cp.add(JCheckBoxGrainBars);
		JCheckBoxGrainColor.setBounds(595, 448, 60, 20);
		JCheckBoxGrainColor.setText("color");
		JCheckBoxGrainColor.setOpaque(false);
		JCheckBoxGrainColor.setVisible(false);
		cp.add(JCheckBoxGrainColor);
		JTextFieldInt.setBounds(650, 480, 30, 20);
		JTextFieldInt.setText("1");
		JTextFieldInt.setVisible(false);
		cp.add(JTextFieldInt);
		JTextFieldRows.setBounds(650, 512, 30, 20);
		JTextFieldRows.setText("10");
		JTextFieldRows.setVisible(false);
		cp.add(JTextFieldRows);

		JCheckBoxFlip.setBounds(697, 352, 75, 20);
		JCheckBoxFlip.setText("Flip");
		JCheckBoxFlip.setOpaque(false);
		ActionListener actionListenerFlip = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {

					JRadioButtonHori.setVisible(true);
					JRadioButtonVerti.setVisible(true);
				} else {

					JRadioButtonHori.setVisible(false);
					JRadioButtonVerti.setVisible(false);
				}
			}
		};
		JCheckBoxFlip.addActionListener(actionListenerFlip);
		cp.add(JCheckBoxFlip);
		JRadioButtonHori.setBounds(704, 384, 100, 20);
		JRadioButtonHori.setText("Horizontal");
		JRadioButtonHori.setOpaque(false);
		JRadioButtonHori.setVisible(false);
		cp.add(JRadioButtonHori);
		JRadioButtonVerti.setBounds(704, 408, 100, 20);
		JRadioButtonVerti.setText("Vertical");
		JRadioButtonVerti.setOpaque(false);
		JRadioButtonVerti.setVisible(false);
		cp.add(JRadioButtonVerti);
		JCheckBoxHighlight.setBounds(792, 352, 110, 20);
		JCheckBoxHighlight.setText("Highlight");
		ActionListener actionListenerExtract = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (selected) {

					JRadioButtonFrameFade.setVisible(true);
					JRadioButtonSpot.setVisible(true);
					JTextFieldXSpot.setVisible(true);
					JTextFieldYSpot.setVisible(true);
					JTextFieldRadius.setVisible(true);
					JTextFieldCourse.setVisible(true);
					JTextFieldEdge.setVisible(true);
					jLabel5.setVisible(true);
					jLabel6.setVisible(true);
					jLabel7.setVisible(true);
					jLabel8.setVisible(true);
					jLabel9.setVisible(true);
				} else {

					JRadioButtonFrameFade.setVisible(false);
					JRadioButtonSpot.setVisible(false);
					JTextFieldXSpot.setVisible(false);
					JTextFieldYSpot.setVisible(false);
					JTextFieldRadius.setVisible(false);
					JTextFieldEdge.setVisible(false);
					JTextFieldCourse.setVisible(false);
					jLabel5.setVisible(false);
					jLabel6.setVisible(false);
					jLabel7.setVisible(false);
					jLabel8.setVisible(false);
					jLabel9.setVisible(false);
				}
			}
		};
		JCheckBoxHighlight.addActionListener(actionListenerExtract);
		cp.add(JCheckBoxHighlight);
		JRadioButtonSpot.setBounds(808, 384, 100, 20);
		JRadioButtonSpot.setText("Spot");
		JRadioButtonSpot.setOpaque(false);
		JRadioButtonSpot.setVisible(false);
		cp.add(JRadioButtonSpot);
		JRadioButtonFrameFade.setBounds(904, 384, 100, 20);
		JRadioButtonFrameFade.setText("FrameFade");
		JRadioButtonFrameFade.setOpaque(false);
		JRadioButtonFrameFade.setVisible(false);
		cp.add(JRadioButtonFrameFade);
		JTextFieldXSpot.setBounds(840, 408, 43, 20);
		JTextFieldXSpot.setText("");
		JTextFieldXSpot.setVisible(false);
		cp.add(JTextFieldXSpot);
		JTextFieldYSpot.setBounds(840, 432, 43, 20);
		JTextFieldYSpot.setText("");
		JTextFieldYSpot.setVisible(false);
		cp.add(JTextFieldYSpot);
		JTextFieldRadius.setBounds(840, 456, 43, 20);
		JTextFieldRadius.setText("");
		JTextFieldRadius.setVisible(false);
		cp.add(JTextFieldRadius);
		JTextFieldEdge.setBounds(952, 408, 43, 20);
		JTextFieldEdge.setText("");
		JTextFieldEdge.setVisible(false);
		cp.add(JTextFieldEdge);
		JTextFieldCourse.setBounds(952, 432, 43, 20);
		JTextFieldCourse.setText("");
		JTextFieldCourse.setVisible(false);
		cp.add(JTextFieldCourse);
		jLabel5.setBounds(816, 408, 30, 20);
		jLabel5.setText("X:");
		jLabel5.setVisible(false);
		cp.add(jLabel5);
		jLabel6.setBounds(816, 432, 30, 20);
		jLabel6.setText("Y:");
		jLabel6.setVisible(false);
		cp.add(jLabel6);
		jLabel7.setBounds(795, 456, 46, 20);
		jLabel7.setText("Radius:");
		jLabel7.setVisible(false);
		cp.add(jLabel7);
		jLabel8.setBounds(885, 408, 65, 20);
		jLabel8.setText("edge width:");
		cp.add(jLabel8);
		jLabel8.setVisible(false);
		jLabel9.setBounds(892, 432, 60, 20);
		jLabel9.setText("transition:");
		cp.add(jLabel9);
		jLabel9.setVisible(false);
		jLabel10.setBounds(290, 5, 110, 20);
		jLabel10.setText("before");
		cp.add(jLabel10);
		jLabel11.setBounds(695, 5, 110, 20);
		jLabel11.setText("afterwards");
		cp.add(jLabel11);
		jLabel12.setBounds(395, 5, 110, 20);
		jLabel12.setText("height:");
		cp.add(jLabel12);
		jLabel13.setBounds(515, 5, 110, 20);
		jLabel13.setText("width:");
		cp.add(jLabel13);
		jLabel14.setBounds(595, 480, 60, 20);
		jLabel14.setText("intenstiy:");
		jLabel14.setVisible(false);
		cp.add(jLabel14);
		jLabel15.setBounds(595, 512, 60, 20);
		jLabel15.setText("rows");
		jLabel15.setVisible(false);
		cp.add(jLabel15);

		paintPanel.setBounds(0, 0, 1025, 600);
		cp.add(paintPanel);

		// RadioButtons werden in Gruppen gesteckt, damit man nur einenen auf
		// einmal auswählen kann /RadioButtons are separated in groups to make
		// it impossible to select more than one
		ButtonGroup colorfilterGroup = new ButtonGroup();
		colorfilterGroup.add(JRadioButtonBlackWhite);
		colorfilterGroup.add(JRadioButtonSepia);
		colorfilterGroup.add(JRadioButtonRed);
		colorfilterGroup.add(JRadioButtonGreen);
		colorfilterGroup.add(JRadioButtonBlue);
		colorfilterGroup.add(JRadioButtonHeat);

		ButtonGroup blurGroup = new ButtonGroup();
		blurGroup.add(JRadioButtonSoft1);
		blurGroup.add(JRadioButtonMedium1);
		blurGroup.add(JRadioButtonStrong1);

		ButtonGroup extractGroup = new ButtonGroup();
		extractGroup.add(JRadioButtonSpot);
		extractGroup.add(JRadioButtonFrameFade);

		// Ende Komponenten/ End of components

		setVisible(true);

	} // end of public ImageProcessor

	// Anfang Methoden/Start Methods

	public static void main(String[] args) {
		new ImgPro();
	} // end of main

	public void jButtonLoad_ActionPerformed(ActionEvent evt) {

		JFileChooser chooser = new JFileChooser();

		FileFilter filter = new FileNameExtensionFilter("Images", "png", "jpg");

		chooser.addChoosableFileFilter(filter);
		// Dialog zum Oeffnen von Dateien anzeigen/ Display file-choose prompt
		int returnValue = chooser.showOpenDialog(null);

		/* Abfrage, ob auf "Öffnen" geklickt wurde */ // continue when "open
														// file" is selected
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			imgPath = chooser.getSelectedFile().getPath();

			try {
				img = ImageIO.read(new File(imgPath));
			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println("cant read input file");
			}

			imgWidth = img.getWidth();
			imgHeight = img.getHeight();
			imgData = new int[imgHeight][imgWidth][3];
			backup = new int[5][imgHeight][imgWidth][3]; // creates a
															// backuparray with
															// 5 entries for
															// imagearrays
			undoCounter = 0;
			backupCounter = 0;

			// height und width werden auf der Gui angezeigt /displaying image
			// width and height
			jLabel13.setText("width:  " + imgWidth + " pixel");
			jLabel12.setText("height:  " + imgHeight + " pixel");
			// the Spot textfields get numbers to make a spot in the middle
			JTextFieldXSpot.setText("" + imgWidth / 2);
			JTextFieldYSpot.setText("" + imgHeight / 2);
			JTextFieldRadius.setText("" + (Math.min(imgHeight, imgWidth) / 3));
			// the framefade textfields get numbers
			JTextFieldCourse.setText("" + (Math.min(imgHeight, imgWidth) / 6));
			JTextFieldEdge.setText("" + (Math.min(imgHeight, imgWidth) / 3));

			// Das Bild Array kriegt die Daten vom Bild /imgData-array inherits
			// image measurements
			for (int h = 0; h < imgHeight; h++)
				for (int w = 0; w < imgWidth; w++)
					img.getRaster().getPixel(w, h, imgData[h][w]);

			Graphics g = getGraphics();
			// clears the image loaded before
			g.clearRect(200, 65, 700, 300);

			// berechnung für ein gleiches Seitenverhältnis mit einer width von
			// / calculate for same aspect ratio with 300px width
			// 300px
			if (imgWidth > imgHeight) {
				ratio = 300.0 / imgWidth;
				newHeight = (int) (imgHeight * ratio);
				// das Bild wird auf das Fenster gemalt /drawing image
				g.drawImage(img, 200, 65, 300, newHeight, null);
				g.drawImage(img, 600, 65, 300, newHeight, null);

			} else {
				ratio = 300.0 / imgHeight;
				newWidth = (int) (imgWidth * ratio);
				// das Bild wird auf das Fenster gemalt /drawing image
				g.drawImage(img, 200, 65, newWidth, 300, null);
				g.drawImage(img, 600, 65, newWidth, 300, null);

			}

		}

	} // end of jButtonLoad_ActionPerformed

	public void jButtonSave_ActionPerformed(ActionEvent evt) {

		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Images", "png", "jpg");
		chooser.addChoosableFileFilter(filter);
		int returnValue = chooser.showSaveDialog(null);

		/* Abfrage, ob auf "Öffnen" geklickt wurde */ // continue when "open
														// file" is selected
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			imgPath = chooser.getSelectedFile().getPath();
			try {
				ImageIO.write(img, "png", new File(imgPath));
			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println("Fehler beim Bildspeichern");
			}
		}
	} // end of jButtonSave_ActionPerformed

	public void jButtonExample_ActionPerformed(ActionEvent evt) {

		try {
			img = ImageIO.read(new File("img/sherif.png"));
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("Fehler beim Bildeinlesen");
		}

		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		imgData = new int[imgHeight][imgWidth][3];

		backup = new int[5][imgHeight][imgWidth][3]; // creates a backuparray
														// with 5 entries for
														// imagearrays
		undoCounter = 0;
		backupCounter = 0;

		// height und width werden auf der Gui angezeigt/height and width are
		// displayed on the Gui
		jLabel13.setText("width:  " + imgWidth + " pixel");
		jLabel12.setText("height:  " + imgHeight + " pixel");
		// the Spot textfields get numbers to make a spot in the middle
		JTextFieldXSpot.setText("" + imgWidth / 2);
		JTextFieldYSpot.setText("" + imgHeight / 2);
		JTextFieldRadius.setText("" + (Math.min(imgHeight, imgWidth) / 3));
		// the framefade textfields get numbers
		JTextFieldCourse.setText("" + (Math.min(imgHeight, imgWidth) / 6));
		JTextFieldEdge.setText("" + (Math.min(imgHeight, imgWidth) / 3));

		// Das Bild Array kriegt die Daten vom Bild/the image Array gets the
		// data from the image
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++)
				img.getRaster().getPixel(w, h, imgData[h][w]);

		// berechnung für ein gleiches Seitenverhältnis mit einer width von
		// / calculate for same aspect ratio with 300px width
		// 300px
		Graphics g = getGraphics();
		// clears the image loaded before
		g.clearRect(200, 65, 700, 300);

		if (imgWidth > imgHeight) {
			ratio = 300.0 / imgWidth;
			newHeight = (int) (imgHeight * ratio);
			// das Bild wird auf das Fenster gemalt /drawing image
			g.drawImage(img, 200, 65, 300, newHeight, null);
			g.drawImage(img, 600, 65, 300, newHeight, null);

		} else {
			ratio = 300.0 / imgHeight;
			newWidth = (int) (imgWidth * ratio);
			// das Bild wird auf das Fenster gemalt /drawing image
			g.drawImage(img, 200, 65, newWidth, 300, null);
			g.drawImage(img, 600, 65, newWidth, 300, null);

		}
	} // end of jButtonSave_ActionPerformed

	public void jButtonUndo_ActionPerformed(ActionEvent evt) {

		if (backupCounter > undoCounter) // only loads the backup imgdata if
											// there is an actual data in the
											// backup array
		{
			// gets the data for imgData from a backup array depending on how
			// many times undo was pressed
			for (int h = 0; h < imgHeight; h++)
				for (int w = 0; w < imgWidth; w++) {
					imgData[h][w][0] = backup[undoCounter][h][w][0];
					imgData[h][w][1] = backup[undoCounter][h][w][1];
					imgData[h][w][2] = backup[undoCounter][h][w][2];
				}
		}
		// can't do undo more than 5 times, because the backup array is only 5
		// long
		if (undoCounter < 4) {
			undoCounter++;
		}

		// Ein Bild wird aus dem imgData Array erstellt /picture is generated
		// from ImgData array
		WritableRaster raster = img.getRaster();
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++)
				raster.setPixel(w, h, imgData[h][w]);

		// berechnung für ein gleiches Seitenverhältnis mit einer width von
		// /calculate for same aspect ratio with 300px width
		// 300px

		if (imgWidth > imgHeight) {
			ratio = 300.0 / imgWidth;
			newHeight = (int) (imgHeight * ratio);
			// das Bild wird auf das Fenster gemalt /drawing image
			Graphics g = getGraphics();

			g.drawImage(img, 600, 65, 300, newHeight, null);

		} else {
			ratio = 300.0 / imgHeight;
			newWidth = (int) (imgWidth * ratio);
			// das Bild wird auf das Fenster gemalt /drawing image
			Graphics g = getGraphics();

			g.drawImage(img, 600, 65, newWidth, 300, null);

		}

	} // end of jButtonUndo_ActionPerformed

	public void jButtonApply_ActionPerformed(ActionEvent evt) {

		// shifts the image data in the backup array one index up
		for (int i = 3; i >= 0; i--) {
			for (int h = 0; h < imgHeight; h++)
				for (int w = 0; w < imgWidth; w++) {
					backup[i + 1][h][w][0] = backup[i][h][w][0];
					backup[i + 1][h][w][1] = backup[i][h][w][1];
					backup[i + 1][h][w][2] = backup[i][h][w][2];
				}
		}
		// saves the current image data at the positon 0 of the backup array
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				backup[0][h][w][0] = imgData[h][w][0];
				backup[0][h][w][1] = imgData[h][w][1];
				backup[0][h][w][2] = imgData[h][w][2];
			}

		backupCounter++; // counts how many times there are actual image data
							// int he backup array
		undoCounter = 0;

		if (JCheckBoxColor.isSelected() == true) {

			if (JRadioButtonBlackWhite.isSelected() == true) {
				greyscale();
			}
			if (JRadioButtonSepia.isSelected() == true) {
				sepia();
			}
			if (JRadioButtonHeat.isSelected() == true) {
				heat();
			}

			if (JRadioButtonRed.isSelected() == true) {
				redGreenBlue("red");
			}
			if (JRadioButtonGreen.isSelected() == true) {
				redGreenBlue("green");
			}
			if (JRadioButtonBlue.isSelected() == true) {
				redGreenBlue("blue");
			}
		}
		if (JCheckBoxEffects.isSelected() == true) {
			if (JCheckBoxBlur.isSelected() == true) {
				if (JRadioButtonSoft1.isSelected() == true) {
					blur();
				}
				if (JRadioButtonMedium1.isSelected() == true) {
					for (int i = 0; i < 5; i++)
						blur();
				}
				if (JRadioButtonStrong1.isSelected() == true) {
					for (int i = 0; i < 10; i++)
						blur();
				}
			}
			if (JCheckBoxMotionBlur.isSelected() == true) {
				if (JRadioButtonSoft1.isSelected() == true) {
					for (int i = 0; i < 3; i++)
						motionBlur();
				}
				if (JRadioButtonMedium1.isSelected() == true) {
					for (int i = 0; i < 10; i++)
						motionBlur();
				}
				if (JRadioButtonStrong1.isSelected() == true) {
					for (int i = 0; i < 20; i++)
						motionBlur();
				}
			}
			if (JCheckBoxEdge.isSelected() == true) {
				edge();
			}
			if (JCheckBoxInvert.isSelected() == true) {
				inverted();
			}
			if (JCheckBoxContrast.isSelected() == true) {
				contrast();
			}
			if (JCheckBoxContrastHard.isSelected() == true) {
				contrastHard();
			}
			if (JCheckBoxVortex.isSelected() == true) {
				vortex();
			}
			if (JRadioButtonOldComic.isSelected() == true) {
				oldComic();
			}
			if (JCheckBoxPixelate.isSelected() == true) {
				pixelate();
			}
			if (JCheckBoxPixelateHard.isSelected() == true) {
				pixelateHard();
			}
			if (JCheckBoxGrain.isSelected() == true) {
				if (JCheckBoxGrainColor.isSelected()) {
					grain(Integer.parseInt(JTextFieldInt.getText()), true);
				} else {
					grain(Integer.parseInt(JTextFieldInt.getText()), false);
				}
			}
			if (JCheckBoxGrainBars.isSelected() == true) {
				if (JCheckBoxGrain.isSelected()) {
					grainBars(Integer.parseInt(JTextFieldInt.getText()), true,
							Integer.parseInt(JTextFieldRows.getText()));
				} else {
					grainBars(Integer.parseInt(JTextFieldInt.getText()), false,
							Integer.parseInt(JTextFieldRows.getText()));
				}

			}
		}
		if (JCheckBoxFlip.isSelected() == true) {
			if (JRadioButtonHori.isSelected() == true) {
				imgData = flipHorizontal();
			}
			if (JRadioButtonVerti.isSelected() == true) {
				imgData = flipVertical();
			}
		}
		if (JCheckBoxHighlight.isSelected()) {
			if (JRadioButtonSpot.isSelected() == true) {
				spot();
			}
			if (JRadioButtonFrameFade.isSelected() == true) {
				framefade();
			}
		}
		if (JCheckBox4Small.isSelected() == true) {
			foursmall();
		}
		// Ein Bild wird aus dem imgData Array erstellt /image is generated from
		// ImgData array
		WritableRaster raster = img.getRaster();
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++)
				raster.setPixel(w, h, imgData[h][w]);

		// Bild wird dargestellt /generating image
		Graphics g = getGraphics();
		if (imgWidth > imgHeight) {
			ratio = 300.0 / imgWidth;
			newHeight = (int) (imgHeight * ratio);
			// das Bild wird auf das Fenster gemalt /drawing image
			g.drawImage(img, 600, 65, 300, newHeight, null);
		} else {
			ratio = 300.0 / imgHeight;
			newWidth = (int) (imgWidth * ratio);
			// das Bild wird auf das Fenster gemal /drawing image
			g.drawImage(img, 600, 65, newWidth, 300, null);
		}

	} // end of jButtonInfo_ActionPerformed

	public void jButtonHelp_ActionPerformed(ActionEvent evt) {

		JDialog helpDialog = new JDialog();

		JTextPane helpText = new JTextPane();
		helpText.setBackground(helpDialog.getBackground());
		helpText.setText("Hilfe!" + "\t" + "Hilfe!" + "\n" + "Hilfe!" + "\t" + "Hilfe!" + "\n" + "Hilfe!" + "\t"
				+ "Hilfe!" + "\n" + "Hilfe!" + "\t" + "Hilfe!");
		helpText.setEditable(false);

		helpDialog.add(helpText);
		// add components
		helpDialog.setLocation(this.postionx + 270, this.postiony + 70);
		helpDialog.setSize(500, 300);
		helpDialog.setVisible(true);
	} // end of jButtonHelp_ActionPerformed

	public void jButtonInfo_ActionPerformed(ActionEvent evt) {
		JDialog helpDialog = new JDialog();

		JTextPane helpText = new JTextPane();
		helpText.setBackground(helpDialog.getBackground());
		helpText.setText("IMG PRO by:" + "\n"
				+ "Kerem Cevik,   Julius Böcker,    Matthias Kruse,   Pavlos Saroglou,    Sándor Alpár Tóth,   Stuart Quiring,   Tim Berger");
		helpText.setEditable(false);

		helpDialog.add(helpText);
		// add components
		helpDialog.setLocation(this.postionx + 270, this.postiony + 70);
		helpDialog.setSize(500, 300);
		helpDialog.setVisible(true);
	} // end of jButtonInfo_ActionPerformed

	// Anfang Filter
	public void greyscale() {

		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {

				int r = imgData[h][w][0];
				int g = imgData[h][w][1];
				int b = imgData[h][w][2];

				int SepiaIntensity = 0; // 0 for black and white image //30 for
										// Sepia
				int SepiaDepth = 0; // 0 for black and white image //20 for
									// Sepia

				// turn red and green greyish
				int gry = (r + g + b) / 3;
				r = g = b = gry;
				r = r + (SepiaDepth * 2);
				g = g + SepiaDepth;

				// set to 255 if out of bounds
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}

				// 'blue' value is used to scale colors
				b -= SepiaIntensity;

				// set to 0/255 if out of bounds
				if (b < 0) {
					b = 0;
				}
				if (b > 255) {
					b = 255;
				}

				imgData[h][w][0] = r;
				imgData[h][w][1] = g;
				imgData[h][w][2] = b;

			}
	}

	public void sepia() {

		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {

				int r = imgData[h][w][0];
				int g = imgData[h][w][1];
				int b = imgData[h][w][2];

				int SepiaIntensity = 30; // 0 for black and white image //30 for
											// Sepia
				int SepiaDepth = 20; // 0 for black and white image //20 for
										// Sepia

				// turn red and green greyish
				int gry = (r + g + b) / 3;
				r = g = b = gry;
				r = r + (SepiaDepth * 2);
				g = g + SepiaDepth;

				// set to 255 if out of bounds
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}

				// 'blue' value is used to scale colors
				b -= SepiaIntensity;

				// set to 0/255 if out of bounds
				if (b < 0) {
					b = 0;
				}
				if (b > 255) {
					b = 255;
				}

				imgData[h][w][0] = r;
				imgData[h][w][1] = g;
				imgData[h][w][2] = b;

			}
	}

	public void heat() {
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				double intense = (imgData[h][w][0] * 0.2126) + (imgData[h][w][1] * 0.7152)
						+ (imgData[h][w][2] * 0.0722);

				if (intense > 250) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 0; // dark violet
					imgData[h][w][2] = 0;
				}

				if (228 < intense && intense <= 250) {
					imgData[h][w][0] = 200;
					imgData[h][w][1] = 0; // red
					imgData[h][w][2] = 0;
				}

				if (209 < intense && intense <= 228) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 30; // dark orange
					imgData[h][w][2] = 0;
				}

				if (190 < intense && intense <= 209) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 80; // orange
					imgData[h][w][2] = 0;
				}

				if (171 < intense && intense <= 190) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 140; // light orange
					imgData[h][w][2] = 0;
				}

				if (152 < intense && intense <= 171) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 180; // dark yellow
					imgData[h][w][2] = 0;
				}

				if (133 < intense && intense <= 152) {
					imgData[h][w][0] = 250;
					imgData[h][w][1] = 250; // yellow
					imgData[h][w][2] = 0;
				}

				if (114 < intense && intense <= 133) {
					imgData[h][w][0] = 130;
					imgData[h][w][1] = 250; // light green
					imgData[h][w][2] = 0;
				}

				if (95 < intense && intense <= 114) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 220; // green
					imgData[h][w][2] = 0;
				}

				if (76 < intense && intense <= 95) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 160; // turquoise
					imgData[h][w][2] = 125;
				}

				if (57 < intense && intense <= 76) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 100; // blue
					imgData[h][w][2] = 215;
				}

				if (38 < intense && intense <= 57) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 80; // dark blue
					imgData[h][w][2] = 240;
				}

				if (19 < intense && intense <= 38) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 60; // darker blue
					imgData[h][w][2] = 220;
				}

				if (intense <= 19) {
					imgData[h][w][0] = 0;
					imgData[h][w][1] = 40; // deep blue
					imgData[h][w][2] = 170;
				}
			}
	}

	public void redGreenBlue(String color) {
		// loop through the array
		for (int x = 0; x < imgData.length; x++) {
			for (int y = 0; y < imgData[0].length; y++) {

				// reading and saving the rgb value of every pixel (imgData
				// array)
				int r = imgData[x][y][0];
				int g = imgData[x][y][1];
				int b = imgData[x][y][2];

				// Entscheidung auf welche Farbe das Bild reduziert werden soll
				// choosing the new color for the picture
				switch (color) {
				// Nimmt nur den Rotwert von jedem Element im Array, alle
				// anderen werden auf 0 gesetzt
				// saves the red value of the pixels,blue and green values are
				// set to 0
				case "red":
					imgData[x][y][0] = r;
					imgData[x][y][1] = 0;
					imgData[x][y][2] = 0;
					break;

				// Nimmt nur den Grünwert von jedem Element im Array, alle
				// anderen werden auf 0 gesetzt
				// saves the green value of the pixels,red and blue values are
				// set to 0
				case "green":
					imgData[x][y][0] = 0;
					imgData[x][y][1] = g;
					imgData[x][y][2] = 0;
					break;
				// Nimmt nur den Blauwert von jedem Element im Array, alle
				// anderen werden auf 0 gesetzt
				// saves the blue value of the pixels,red and green values are
				// set to 0
				case "blue":
					imgData[x][y][0] = 0;
					imgData[x][y][1] = 0;
					imgData[x][y][2] = b;
					break;
				}
			}
		}

	}

	public void blur() {
		// start of blur filter

		for (int counter = 0; counter < 5; counter++) // executes filter
														// "counter" (5) times
		{
			for (int h = 1; h < imgHeight - 1; h++) // goes through pixels
													// vertically
			{

				for (int w = 1; w < imgWidth - 1; w++) // goes through pixels
														// horizontally

				{
					imgData[h][w][0] = (imgData[h - 1][w][0] + imgData[h + 1][w][0] + imgData[h][w - 1][0]
							+ imgData[h][w + 1][0] + imgData[h][w][0]) / 5;
					imgData[h][w][1] = (imgData[h - 1][w][1] + imgData[h + 1][w][1] + imgData[h][w - 1][1]
							+ imgData[h][w + 1][1] + imgData[h][w][1]) / 5;
					imgData[h][w][2] = (imgData[h - 1][w][2] + imgData[h + 1][w][2] + imgData[h][w - 1][2]
							+ imgData[h][w + 1][2] + imgData[h][w][2]) / 5;

					// original selected pixel rgb value gets overriden with the
					// average rgb value of the original selected pixel rgb
					// value added to
					// neighbouring pixels
				}

			}
		}
		// end of "blur" filter
	}


	public void motionBlur() {
		double degree = (double) Integer.parseInt(JTextFieldDegree.getText());
		degree = Math.toRadians(degree);
		//creates a copy of imgData, so the color wont't change where it shouldn't
		int[][][] imgData2 = new int[imgHeight][imgWidth][3];
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				imgData2[h][w][0] = imgData[h][w][0];
				imgData2[h][w][1] = imgData[h][w][1];
				imgData2[h][w][2] = imgData[h][w][2];
			}
		int wdiff1;
		int wdiff2;
		int hdiff1;
		int hdiff2;
		for (int h = 3; h < imgHeight - 3; h++)
			for (int w = 3; w < imgWidth - 3; w++) {
				//calculates how much the width value changes with 1 pixel length and 2 pixel length
				wdiff1 = (int) Math.round((Math.sin(degree) * 1));
				wdiff2 = (int) Math.round((Math.sin(degree) * 2));
				//calculates how much the height value changes with 1 pixel length and 2 pixel length
				hdiff1 = (int) Math.round((Math.cos(degree) * 1));
				hdiff2 = (int) Math.round((Math.cos(degree) * 2));
				
				//takes the average rgb value of 5 pixels in a row
				imgData[h][w][0] = (imgData2[h - hdiff2][w - wdiff2][0] + imgData2[h - hdiff1][w - wdiff1][0]
						+ imgData2[h][w][0] + imgData2[h + hdiff1][w + wdiff1][0] + imgData2[h + hdiff2][w + wdiff2][0]) / 5;
				imgData[h][w][1] = (imgData2[h - hdiff2][w - wdiff2][1] + imgData2[h - hdiff1][w - wdiff1][1]
						+ imgData2[h][w][1] + imgData2[h + hdiff1][w + wdiff1][1] + imgData2[h + hdiff2][w + wdiff2][1]) / 5;
				imgData[h][w][2] = (imgData2[h - hdiff2][w - wdiff2][2] + imgData2[h - hdiff1][w - wdiff1][2]
						+ imgData2[h][w][2] + imgData2[h + hdiff1][w + wdiff1][2] + imgData2[h + hdiff2][w + wdiff2][2]) / 5;
			}
	}

	public void edge() {
		// creates an array with the intensity values(greyscale)
		int intensity[][] = new int[imgHeight][imgWidth];
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				intensity[h][w] = 0;
				intensity[h][w] += imgData[h][w][0] * 0.2126;
				intensity[h][w] += imgData[h][w][1] * 0.7152;
				intensity[h][w] += imgData[h][w][2] * 0.0722;
			}

		for (int h = 1; h < imgHeight - 1; h++)
			for (int w = 1; w < imgWidth - 1; w++) {
				// Substracts the intensity values from 4 pixels around the
				// pixel from the the current pixel times 4.
				// If they differ a lot from the current pixel there is and edge
				// and the current pixel is set to light gray.
				// The result has to be an absolute value. For example, if the
				// current pixel is lighter,
				// than the average of the surrounding pixels the result will be
				// a negative value, which can't be an RGB value.

				imgData[h][w][0] = Math.abs(4 * intensity[h][w]
						- (intensity[h + 1][w] + intensity[h - 1][w] + intensity[h][w + 1] + intensity[h][w - 1]));
				imgData[h][w][1] = Math.abs(4 * intensity[h][w]
						- (intensity[h + 1][w] + intensity[h - 1][w] + intensity[h][w + 1] + intensity[h][w - 1]));
				imgData[h][w][2] = Math.abs(4 * intensity[h][w]
						- (intensity[h + 1][w] + intensity[h - 1][w] + intensity[h][w + 1] + intensity[h][w - 1]));
			}

	}

	public void inverted() {
		System.out.println(Math.sin(3));
		int[][][] imgData2 = new int[imgHeight][imgWidth][3];
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				imgData2[h][w][0] = imgData[h][w][0];
				imgData2[h][w][1] = imgData[h][w][1];
				imgData2[h][w][2] = imgData[h][w][2];
			}
		int newH;
		int newW;
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				newW=w;
				newH=h+((int)(Math.sin(w/50.0)*100.0));
				if (newW < 0) {
					newW = 0;
				} else if (newW >= imgWidth) {
					newW = imgWidth - 1;
				}
				if (newH < 0) {
					newH = 0;
				} else if (newH >= imgHeight) {
					newH = imgHeight - 1;
				}
				//sets the pixel to the new posstion
				imgData[h][w][0] = imgData2[newH][newW][0];
				imgData[h][w][1] = imgData2[newH][newW][1];
				imgData[h][w][2] = imgData2[newH][newW][2];
							
			}
	}

	public void vortex() {
		//creates a copy imgData so when the pixels get moved, they won't account already moved pixels 
		int[][][] imgData2 = new int[imgHeight][imgWidth][3];
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				imgData2[h][w][0] = imgData[h][w][0];
				imgData2[h][w][1] = imgData[h][w][1];
				imgData2[h][w][2] = imgData[h][w][2];
			}

		// double d;
		double degree;
		int middleW = imgWidth / 2;
		int middleH = imgHeight / 2;
		int newW;
		int newH;
		double length = 100;
		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				// d = Math.sqrt((middleW - w) * (middleW - w) + (middleH - h) *
				// (middleH - h));
				
				//calculates the degree from the current pixel to the middle
				degree = Math.toDegrees(Math.atan2(w - middleW, h - middleH));
				if (degree < 0) {
					degree += 360;
				}
				//adds 90° to the degree
				degree += 90;
				degree = Math.toRadians(degree);
				//calculates the new position of the pixel with the degree and a distance of 100
				newW = (int) (Math.sin(degree) * length + w);
				newH = (int) (Math.cos(degree) * length + h);
				
				//if the new position is out of bounds, it takes the last pixel of the image border
				if (newW < 0) {
					newW = 0;
				} else if (newW >= imgWidth) {
					newW = imgWidth - 1;
				}
				if (newH < 0) {
					newH = 0;
				} else if (newH >= imgHeight) {
					newH = imgHeight - 1;
				}
				//sets the pixel to the new posstion
				imgData[h][w][0] = imgData2[newH][newW][0];
				imgData[h][w][1] = imgData2[newH][newW][1];
				imgData[h][w][2] = imgData2[newH][newW][2];

			}
	}

	public void contrast() {
		// increases light colors values and decreases dark colors values for a
		// bigger contrast
		for (int x = 0; x < imgHeight; x++)
			for (int y = 0; y < imgWidth; y++)
				for (int z = 0; z < 3; z++)
					if (imgData[x][y][z] <= 127)
						imgData[x][y][z] = (int) (imgData[x][y][z] / 2);
					else
						imgData[x][y][z] = (int) ((imgData[x][y][z] + 255) / 2);
	}

	public void contrastHard() {
		// increases light colors values and decreases dark colors values for a
		// bigger contrast
		for (int x = 0; x < imgHeight; x++)
			for (int y = 0; y < imgWidth; y++)
				for (int z = 0; z < 3; z++) {
					if (imgData[x][y][z] <= 127)
						imgData[x][y][z] = 0;
					else
						imgData[x][y][z] = 255;
				}
	}

	public void oldComic() {

		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				double newc = (imgData[h][w][0] * 0.2126) + (imgData[h][w][1] * 0.7152) + (imgData[h][w][2] * 0.0722);

				if (newc <= 85) {
					imgData[h][w][0] = 42;
					imgData[h][w][1] = 42;
					imgData[h][w][2] = 42;
				}

				if (85 < newc && newc <= 170) {
					imgData[h][w][0] = 127;
					imgData[h][w][1] = 127;
					imgData[h][w][2] = 127;
				}

				if (170 < newc && newc <= 255) {
					imgData[h][w][0] = 212;
					imgData[h][w][1] = 212;
					imgData[h][w][2] = 212;
				}

			}
	}

	public void pixelate() {
		// copy the middle pixel of a 3x3 matrix to all other
		for (int x = 1; x < imgHeight - 3; x += 3) {
			for (int y = 1; y < imgWidth - 3; y += 3) {
				for (int z = 0; z < 3; z++) {
					imgData[x - 1][y - 1][z] = imgData[x][y][z];
					imgData[x - 1][y][z] = imgData[x][y][z];
					imgData[x - 1][y + 1][z] = imgData[x][y][z];
					imgData[x][y - 1][z] = imgData[x][y][z];
					imgData[x][y + 1][z] = imgData[x][y][z];
					imgData[x + 1][y - 1][z] = imgData[x][y][z];
					imgData[x + 1][y][z] = imgData[x][y][z];
					imgData[x + 1][y + 1][z] = imgData[x][y][z];
				}
			}
		}

	}

	public void pixelateHard() {

		// copy the middle pixel of a 5x5 matrix to all other
		for (int x = 2; x < imgHeight - 5; x += 5) {
			for (int y = 2; y < imgWidth - 5; y += 5) {
				for (int z = 0; z < 3; z++) {

					// 5 top pixel
					imgData[x - 2][y - 2][z] = imgData[x][y][z];
					imgData[x - 2][y - 1][z] = imgData[x][y][z];
					imgData[x - 2][y][z] = imgData[x][y][z];
					imgData[x - 2][y + 1][z] = imgData[x][y][z];
					imgData[x - 2][y + 2][z] = imgData[x][y][z];

					// 5 pixel 2nd row
					imgData[x - 1][y - 2][z] = imgData[x][y][z];
					imgData[x - 1][y - 1][z] = imgData[x][y][z];
					imgData[x - 1][y][z] = imgData[x][y][z];
					imgData[x - 1][y + 1][z] = imgData[x][y][z];
					imgData[x - 1][y + 2][z] = imgData[x][y][z];

					// 5 pixel middle row
					imgData[x][y - 2][z] = imgData[x][y][z];
					imgData[x][y - 1][z] = imgData[x][y][z];
					imgData[x][y][z] = imgData[x][y][z];
					imgData[x][y + 1][z] = imgData[x][y][z];
					imgData[x][y + 2][z] = imgData[x][y][z];

					// 5 pixel 4th row
					imgData[x + 1][y - 2][z] = imgData[x][y][z];
					imgData[x + 1][y - 1][z] = imgData[x][y][z];
					imgData[x + 1][y][z] = imgData[x][y][z];
					imgData[x + 1][y + 1][z] = imgData[x][y][z];
					imgData[x + 1][y + 2][z] = imgData[x][y][z];

					// 5 pixel last row
					imgData[x + 2][y - 2][z] = imgData[x][y][z];
					imgData[x + 2][y - 1][z] = imgData[x][y][z];
					imgData[x + 2][y][z] = imgData[x][y][z];
					imgData[x + 2][y + 1][z] = imgData[x][y][z];
					imgData[x + 2][y + 2][z] = imgData[x][y][z];
				}
			}
		}

	}

	public void foursmall() {

		// temp Array
		int[][][] imgDataTemp;
		imgDataTemp = new int[imgHeight][imgWidth][3];

		// copy into temp array
		for (int x = 0; x < imgHeight; x++) {
			for (int y = 0; y < imgWidth; y++) {
				for (int z = 0; z < 3; z++) {
					imgDataTemp[x][y][z] = imgData[x][y][z];
				}
			}
		}

		// copy every 4th pixel of temp array into original array and add some
		// effects to the for small picture
		for (int x = 0; x < imgHeight / 2; x++) {
			for (int y = 0; y < imgWidth / 2; y++) {
				for (int z = 0; z < 3; z++) {
					// upper left picture is invert
					imgData[x][y][z] = 255 - imgDataTemp[x * 2][y * 2][z];

					// lower left picture is with grain
					imgData[x + imgHeight / 2][y][z] = (int) ((imgDataTemp[x * 2][y * 2][z] + (Math.random() * 255)) / 2
							+ 0.5);

					// upper right picture is with more contrast
					if (imgDataTemp[x * 2][y * 2][z] <= 85)
						imgData[x][y + imgWidth / 2][z] = (int) (imgDataTemp[x * 2][y * 2][z] / 2);
					else if (imgDataTemp[x * 2][y * 2][z] > 170)
						imgData[x][y + imgWidth / 2][z] = (int) ((imgDataTemp[x * 2][y * 2][z] + 255) / 2);
					else
						imgData[x][y + imgWidth / 2][z] = (int) ((imgDataTemp[x * 2][y * 2][z] + 127) / 2);

					// lower right picture is transparent (1:1 ratio with
					// original pixel)
					imgData[x + imgHeight / 2][y + imgWidth / 2][z] = (int) ((imgDataTemp[x * 2][y * 2][z]
							+ imgData[x + imgHeight / 2][y + imgWidth / 2][z]) / 2);
				}
			}
		}

	}

	public void grain(int userInput, boolean grainColor) {

		// check for invalid user input
		userInput = Math.max(1, userInput);
		userInput = Math.min(10, userInput);

		for (int intensity = 0; intensity <= userInput; intensity++) {

			// loop for applying the color to only as many rows the user wanted
			// before changing it
			for (int x = 0; x < imgHeight; x++) {

				// loop for width
				for (int y = 0; y < imgWidth; y++) {

					// pick random color
					int rndColorR = (int) (Math.random() * 256);
					int rndColorG = (int) (Math.random() * 256);
					int rndColorB = (int) (Math.random() * 256);

					// if not colorful grain make all colors equal
					if (!grainColor) {
						rndColorR = rndColorG = rndColorB;
					}

					// mix random color with every pixel (4:1 ratio)
					imgData[x][y][0] = (int) ((imgData[x][y][0] * 4 + rndColorR) / 5);
					imgData[x][y][1] = (int) ((imgData[x][y][1] * 4 + rndColorG) / 5);
					imgData[x][y][2] = (int) ((imgData[x][y][2] * 4 + rndColorB) / 5);
				}
			}
		}
	}

	public void grainBars(int userInput, boolean grainColor, int rows) {
		rows = imgHeight / rows - rows;
		// check for invalid user input
		userInput = Math.max(1, userInput);
		userInput = Math.min(10, userInput);
		rows = Math.max(1, rows);
		rows = Math.min(rows, (int) ((0.5 + imgHeight) / 2));

		// loop for intensity of filter
		for (int intensity = 0; intensity <= userInput; intensity++) {

			// loop for changing color of bars and height of picture
			for (int colorLoop = 0; colorLoop < imgHeight - rows; colorLoop += rows) {

				// pick random color
				int rndColorR = (int) (Math.random() * 256);
				int rndColorG = (int) (Math.random() * 256);
				int rndColorB = (int) (Math.random() * 256);

				// if not colorful grain make all colors equal
				if (!grainColor) {
					rndColorR = rndColorG = rndColorB;
				}

				// loop for applying the color to only as many rows the user
				// wanted before changing it
				for (int x = 0; x < rows || x + colorLoop < imgHeight; x++) {

					// loop for width
					for (int y = 0; y < imgWidth; y++) {

						// mix random color with every pixel (4:1 ratio)
						imgData[colorLoop + x][y][0] = (int) ((imgData[colorLoop + x][y][0] * 4 + rndColorR) / 5);
						imgData[colorLoop + x][y][1] = (int) ((imgData[colorLoop + x][y][1] * 4 + rndColorG) / 5);
						imgData[colorLoop + x][y][2] = (int) ((imgData[colorLoop + x][y][2] * 4 + rndColorB) / 5);
					}
				}
			}
		}

	}

	public int[][][] flipVertical() {
		// neues Array für die neuen Pixelkoordinaten
		// new array for the new pixel coordinates
		int[][][] flippedPixels = new int[imgData.length][imgData[0].length][3];

		// loop through "imgData"
		for (int x = 0; x < imgData.length; x++) {
			// jedes Mal wenn eine neue Reihe angefangen wird, wird die Länge
			// der Reihe in int pos gespeichert
			// "pos" length will be saved at the beginning of every new
			// horizontal line

			// Da das Quellarray angefangen bei 0 erzeugt wird muss pos -1
			// gerechnet werden
			// "1" is subtracted from "pos" lenght because the array must start
			// with "0"

			int pos = imgData[0].length - 1;

			for (int y = 0; y < imgData[0].length; y++) {
				// Auslesen und Speichern der einzelnen RGB-Werte von jedem
				// Element im Array
				// reading and saving the individual rgb value of every element
				// of the array

				int r = imgData[x][y][0];
				int g = imgData[x][y][1];
				int b = imgData[x][y][2];

				flippedPixels[x][pos][0] = r;
				flippedPixels[x][pos][1] = g;
				flippedPixels[x][pos][2] = b;

				// decrement pos für jeden Loop-Schritt
				// decrease value of "pos" by "1" at the end of every single
				// loop
				pos--;
			}
		}
		// returns "flippedPixels" (vertically flipped picture)
		return flippedPixels;

	}

	public int[][][] flipHorizontal() {
		// neues Array für die neuen Pixelkoordinaten
		// new array "flippedPixels" for the new pixel coordinates

		int[][][] flippedPixels = new int[imgData.length][imgData[0].length][3];

		// Loop durch das Array (immer eine Reihe horizontal)
		// goes through the array horizontally

		for (int y = 0; y < imgData[0].length; y++) {
			// jedes Mal wenn eine neue Reihe angefangen wird, wird die Länge
			// der Reihe in int pos gespeichert
			// Da das Quellarray angefangen bei 0 erzeugt wird muss pos -1
			// gerechnet werden

			// "imgData" length will be saved into "pos" at the beginning of
			// every new horizontal line
			// "1" is subtracted from "imgData" lenght because the source array
			// must start with "0"

			int pos = imgData.length - 1;

			for (int x = 0; x < imgData.length; x++) {
				// Auslesen und Speichern der einzelnen RGB-Werte von jedem
				// Element im Array
				// reading and saving the individual rgb value of every element
				// of the array

				int r = imgData[x][y][0];
				int g = imgData[x][y][1];
				int b = imgData[x][y][2];

				// das neue Array wird gefüllt, die y Werte bleiben beim
				// horizontalen Spiegeln unberührt
				// the new array is filled ,the vertical values ("y") remain the
				// same

				// die x-Werte werden gespiegelt in das neue Array übertragen
				// (pos = maximale Länge der Reihe - 1 für jeden vorherigen
				// Schritt)
				// the vertical values ("x") are mirrored and saved in the new
				// array

				flippedPixels[pos][y][0] = r;
				flippedPixels[pos][y][1] = g;
				flippedPixels[pos][y][2] = b;

				// decrement pos für jeden Loop-Schritt
				// decrease value of "pos" by "1" at the end of every single
				// loop
				pos--;
			}
		}
		// returns "flippedPixels" (horizontally flipped picture)
		return flippedPixels;

	}

	public void spot() {
		// variables for the filter
		// r=radius
		double r = Integer.parseInt(JTextFieldRadius.getText());

		// sh and sh for heigh and width
		int sw = Integer.parseInt(JTextFieldXSpot.getText());
		int sh = Integer.parseInt(JTextFieldYSpot.getText());

		// for height
		for (int x = 0; x < imgHeight; x++) {
			// for width
			for (int y = 0; y < imgWidth; y++) {
				// for color channels
				for (int z = 0; z < 3; z++) {

					// find 3rd point of the triangle to use pythagorean law
					int a = sh - x;
					int b = sw - y;

					// pythagorean law a^2 + b^2 = c^2
					double d = Math.sqrt(a * a + b * b);

					// color pixels
					if (d <= r)
						imgData[x][y][z] -= (int) (imgData[x][y][z] / r * d);
					else
						imgData[x][y][z] = 0;

				}

			}

		}

	}

	public void framefade() {
		int edgeLength = Integer.parseInt(JTextFieldEdge.getText());
		int transitionLength = Integer.parseInt(JTextFieldCourse.getText());
		// The double ratio determines how much blacker a pixel gets. From 0 for
		// no black to 1 for total black.
		double ratio;

		for (int h = 0; h < imgHeight; h++)
			for (int w = 0; w < imgWidth; w++) {
				// condition for the top edge
				if (h < edgeLength) {
					// The ratio is calculated.
					// For example, if you have an edgeLenght of 100 and a
					// courseLenght of 50.
					// The pixel where the fade starts is pixel 99. So you
					// calculate (100-99)/50 which is very small, so just a
					// little blacker.
					// The pixel where the fade ends is 50. SO you calculate
					// (100-50)/50 which is 1, so total black.
					// Everything further than one stays one with Math.min.
					ratio = (double) (edgeLength - h) / transitionLength;
					imgData[h][w][0] -= (int) (imgData[h][w][0] * Math.min(1, ratio)); 
					imgData[h][w][1] -= (int) (imgData[h][w][1] * Math.min(1, ratio));
					imgData[h][w][2] -= (int) (imgData[h][w][2] * Math.min(1, ratio));
				}
				// condition for the bottom edge
				else if (h > imgHeight - edgeLength) {
					ratio = (double) (edgeLength - (imgHeight - h)) / transitionLength;
					imgData[h][w][0] -= (int) (imgData[h][w][0] * Math.min(1, ratio));
					imgData[h][w][1] -= (int) (imgData[h][w][1] * Math.min(1, ratio));
					imgData[h][w][2] -= (int) (imgData[h][w][2] * Math.min(1, ratio));
				}
				// condition for the left edge
				if (w < edgeLength) {
					ratio = (double) (edgeLength - w) / transitionLength;
					imgData[h][w][0] -= (int) (imgData[h][w][0] * Math.min(1, ratio));
					imgData[h][w][1] -= (int) (imgData[h][w][1] * Math.min(1, ratio));
					imgData[h][w][2] -= (int) (imgData[h][w][2] * Math.min(1, ratio));

				}
				// condition for the right edge
				else if (w > imgWidth - edgeLength) {
					ratio = (double) (edgeLength - (imgWidth - w)) / transitionLength;
					imgData[h][w][0] -= (int) (imgData[h][w][0] * Math.min(1, ratio));
					imgData[h][w][1] -= (int) (imgData[h][w][1] * Math.min(1, ratio));
					imgData[h][w][2] -= (int) (imgData[h][w][2] * Math.min(1, ratio));
				}

			}
	}
	// Ende Filter
	// end of filter methods

	// Klasse nur um auf dem jPanel das Logo zu malen
	// class for displaying Logo on jPanel

	private class MyCanvasPanel extends JPanel {

		private static final long serialVersionUID = -4780087310256581347L;

		public MyCanvasPanel() {

		}

		@Override
		public void paintComponent(Graphics g) {

			BufferedImage icon = null;
			super.paintComponent(g);
			try {
				icon = ImageIO.read(new File("img/Icon.PNG"));

				// meine Zeichnung
				// my drawing
				g.drawImage(icon, 5, 5, 158, 143, null);

			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println("Error displaying icon");
			}
			g.setColor(Color.blue);
			g.draw3DRect(390, 2, 240, 25, true);
			g.draw3DRect(5, 345, 210, 200, true);
			g.draw3DRect(220, 345, 470, 200, true);
			g.draw3DRect(695, 345, 90, 200, true);
			g.draw3DRect(790, 345, 210, 200, true);

		}
	}

	// end of methods
} // end of "Bildprozessor" class
