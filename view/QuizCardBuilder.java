package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.QuizCard;
import controller.CardSaver;

public class QuizCardBuilder extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<QuizCard> cards;
	
	private static final Font BIGFONT = new Font("sanserif", Font.BOLD, 24);
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenuItem;
	
	private JScrollPane questionScroll;
	private JScrollPane answerScroll;
	
	private JPanel panelContainer;
	private JPanel questionPanel;
	private JPanel answerPanel;
	private JLabel lblQuestion;
	private JLabel lblAnswer;
	private JTextArea txtQuestion;
	private JTextArea txtAnswer;
	
	private JButton btnNextCard;
	
	public QuizCardBuilder(){
		super("Create Flash Cards");
		this.setLayout(new BorderLayout());
		cards = new ArrayList<QuizCard>();
		this.setSize(500, 550);
		this.setResizable(false);
		this.createComponents();
	}
	
	private void createComponents(){
		/* Menu Bar */
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new SaveMenuListener());
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		this.add(menuBar, BorderLayout.NORTH);
		
		panelContainer = new JPanel(new FlowLayout());
		questionPanel = new JPanel(new BorderLayout());
		answerPanel = new JPanel(new BorderLayout());
		
		lblQuestion = new JLabel("Question");
		txtQuestion = new JTextArea(6, 20);
		txtQuestion.setLineWrap(true);
		txtQuestion.setWrapStyleWord(true);
		txtQuestion.setFont(BIGFONT);
		questionScroll = new JScrollPane(txtQuestion);
		questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		questionPanel.add(lblQuestion, BorderLayout.NORTH);
		questionPanel.add(questionScroll, BorderLayout.CENTER);
		
		lblAnswer = new JLabel("Answer");
		txtAnswer = new JTextArea(6, 20);
		txtAnswer.setLineWrap(true);
		txtAnswer.setWrapStyleWord(true);
		txtAnswer.setFont(BIGFONT);
		answerScroll = new JScrollPane(txtAnswer);
		answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		answerPanel.add(lblAnswer, BorderLayout.NORTH);
		answerPanel.add(answerScroll, BorderLayout.CENTER);
		
		panelContainer.add(questionPanel);
		panelContainer.add(answerPanel);
		
		this.add(panelContainer, BorderLayout.CENTER);

		btnNextCard = new JButton("Add Card to Pile");
		btnNextCard.addActionListener(new NextCardListener());
		this.add(btnNextCard, BorderLayout.SOUTH);
	}
	
	private class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileSave = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
			fileSave.setFileFilter(filter);
			fileSave.showSaveDialog(QuizCardBuilder.this);
			CardSaver cs = new CardSaver(cards, fileSave.getSelectedFile());
			if(cs.saveCards()){
				JOptionPane.showMessageDialog(QuizCardBuilder.this, "Cards saved successfuly");
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(QuizCardBuilder.this, "Cards save unsuccessful", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	private class NextCardListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cards.add(new QuizCard(txtQuestion.getText(), txtAnswer.getText()));
			txtQuestion.setText("");
			txtAnswer.setText("");
			txtQuestion.requestFocus();
		}
		
	}
}
