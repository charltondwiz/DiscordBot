package commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool.ManagedBlocker;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SaveCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		String playlistName = event.getMessage().getContent().replaceAll("!save ", "");
		if(!Main.savedSongsHash.containsKey(playlistName)){
		Main.printWriter.println(playlistName + " " + Main.currentPlayingSong);
		Main.printWriter.flush();
		event.getTextChannel().sendMessage("Song saved as " + playlistName).queue();
		Main.savedSongsHash.put(playlistName, Main.currentPlayingSong);
		}else{
			event.getTextChannel().sendMessage("That playlist name already exists. Replace it?").queue();
			Main.caseMethod = "ReplacePlaylist";
			Main.replacePlayList = playlistName;
			Main.replacePlayListWith = Main.currentPlayingSong;
			Main.waitingForResponseID = event.getAuthor().getId();
			Main.waitingForResponse = true;
		}
	}
	
	public static void replacePlaylist(MessageReceivedEvent event){
		if(Main.responseNumber==1){
			event.getTextChannel().sendMessage("Replaced the playlist: " + Main.replacePlayList + " with: " + Main.replacePlayListWith).queue();
			Main.savedSongsHash.replace(Main.replacePlayList, Main.replacePlayListWith);
			
		}else if(Main.responseNumber ==0){
			event.getTextChannel().sendMessage("Understood.").queue();
		}else{
			event.getTextChannel().sendMessage("Invalid answer.").queue();
		}
		
		Main.replacePlayList = "";
		Main.replacePlayListWith = "";
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
