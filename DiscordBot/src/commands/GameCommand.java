package commands;

import java.awt.Event;
import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GameCommand implements command{
	
	public static boolean nextWord;
	
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		Random random = new Random();
		String word1 = Main.words[random.nextInt(127142)];
		String word2 = Main.words[random.nextInt(127142)];
		
		event.getTextChannel().sendMessage("If A is " + word1 + ", and B is " + word2 + ", what is C?").queue();
		nextWord = true;
	}
	
	public static void checkAnswer(String answer, MessageReceivedEvent event){
		if(answer.toLowerCase().startsWith("c is ")){
			event.getTextChannel().sendMessage("Correct!").queue();
			nextWord = false;
		}else{
			event.getTextChannel().sendMessage("Wrong!").queue();
			nextWord = false;
			}
		
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

}
