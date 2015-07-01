package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.QuizCard;
import controller.CardReader;

public class QuizCardPlayer extends JFrame{	
	
	private static final long serialVersionUID = 1L;
	private ArrayList<QuizCard> cards;
	private int currentCardIndex;
	private boolean answerShown;
	private static final Font BIGFONT = new Font("sanserif", Font.BOLD, 24);
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem createCardsMenuItem;
	private JMenuItem openMenuItem;
	
	private JPanel panelContainer;
	private JScrollPane scroller;
	private JTextArea txtContent;
	private JButton btnToggleQA;
	
	private JPanel panelNavButtons;
	private JButton btnPrevCard;
	private JButton btnNextCard;
	
	public QuizCardPlayer(){
		super("View Flash Cards");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(700, 500);
		this.setResizable(false);
		this.createComponents();
	}
	
	private void createComponents(){
		/* Menu Bar */
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		createCardsMenuItem = new JMenuItem("Create Flash Cards");
		createCardsMenuItem.addActionListener(new CreateCardsMenuListener());
		openMenuItem = new JMenuItem("Load Cards");
		openMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(createCardsMenuItem);
		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
		
		/* Main Content */
		panelContainer = new JPanel(new BorderLayout());
		panelContainer.setBorder(new EmptyBorder(20, 20, 20, 20));
		txtContent = new JTextArea(10,20);
		txtContent.setFont(BIGFONT);
		txtContent.setLineWrap(true);
		txtContent.setEditable(false);
		
		scroller = new JScrollPane(txtContent);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btnToggleQA = new JButton("Awaiting Card load");
		btnToggleQA.setEnabled(false);
		btnToggleQA.addActionListener(new ToggleListener());
		panelContainer.add(scroller, BorderLayout.CENTER);
		panelContainer.add(btnToggleQA, BorderLayout.SOUTH);
		
		panelNavButtons = new JPanel(new GridLayout(1,2));
		btnPrevCard = new JButton("Previous Card");
		btnPrevCard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentCardIndex == 0){ 
					currentCardIndex = cards.size() - 1;
					updateQuestion();
				}
				else{
					currentCardIndex--;
					updateQuestion();
				}
			}
			
		});
		btnPrevCard.setEnabled(false);
		btnNextCard = new JButton("Next Card");
		btnNextCard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				currentCardIndex = ((currentCardIndex + 1) % cards.size());
				updateQuestion();
			}
			
		});
		btnNextCard.setEnabled(false);
		panelNavButtons.add(btnPrevCard);
		panelNavButtons.add(btnNextCard);
		
		
		this.getContentPane().add(BorderLayout.CENTER, panelContainer);
		this.getContentPane().add(BorderLayout.SOUTH, panelNavButtons);
	}
	
	private void updateQuestion(){
		txtContent.setText(cards.get(currentCardIndex).getQuestion());
		answerShown = false;
	}
	
	private class CreateCardsMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new QuizCardBuilder().setVisible(true);
		}
		
	}
	
	private class OpenMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				JFileChooser fileSave = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
				fileSave.setFileFilter(filter);
				fileSave.showSaveDialog(QuizCardPlayer.this);
				CardReader cr = new CardReader(fileSave.getSelectedFile());
				cards = cr.getCards();
				currentCardIndex = 0;
				txtContent.setText(cards.get(currentCardIndex).getQuestion());
				btnToggleQA.setText("Show Answer");
				btnToggleQA.setEnabled(true);
				btnPrevCard.setEnabled(true);
				btnNextCard.setEnabled(true);
				answerShown = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(QuizCardPlayer.this, "Invalid File Format");
			}
		}
		
	}
	
	private class ToggleListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(answerShown){
				answerShown = false;
				btnToggleQA.setText("Show Answer");
				txtContent.setText(cards.get(currentCardIndex).getQuestion());
			}
			else{
				answerShown = true;
				btnToggleQA.setText("Show Question");
				txtContent.setText(cards.get(currentCardIndex).getAnswer());
			}
		}
		
	}
}
