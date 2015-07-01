package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.QuizCard;

public class CardReader {
	private File fileToRead;
	private ArrayList<QuizCard> cards;
	
	public CardReader(File f){
		this.fileToRead = f;
		cards = new ArrayList<QuizCard>();
	}
	
	public ArrayList<QuizCard> getCards() throws IOException{
		this.createCards();
		return cards;
	}
	
	private void createCards() throws IOException{
		try{
			FileReader fr = new FileReader(this.fileToRead);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line = br.readLine()) != null){
				String[] parts = line.split("/");
				cards.add(new QuizCard(parts[0], parts[1]));
			}
			br.close();
		}
		catch(NullPointerException npe){
			System.out.println("Cancelled");
		}

	}
}
