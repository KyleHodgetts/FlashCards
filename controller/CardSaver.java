package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.QuizCard;

public class CardSaver {
	private ArrayList<QuizCard> cardsToSave;
	private File file;
	
	public CardSaver(ArrayList<QuizCard> cards, File fileToSave){
		cardsToSave = cards;
		file = fileToSave;
	}
	
	public boolean saveCards(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(QuizCard qc : cardsToSave){
				writer.write(qc.getQuestion() + "/");
				writer.write(qc.getAnswer() + "\n");
			}
			writer.close();
			return true;
		} 
		catch (IOException e) {
			System.out.println("Card saving failed");
			e.printStackTrace();
			return false;
		}
	}
}
