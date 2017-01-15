package commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.PlayerPauseEvent;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;

import javax.print.MultiDocPrintService;

import org.codehaus.jackson.JsonFactory;

import io.youtubebot.discordbot.GuildMusicManager;
import io.youtubebot.discordbot.Main;
import io.youtubebot.discordbot.TrackScheduler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildManager;

public class PlayCommand implements command{
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
//		AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();
//		musicManager = new GuildMusicManager(audioPlayerManager);
		String songName = event.getMessage().getContent().replaceAll("!play ", "");
		if(Main.savedSongsHash.containsKey(songName)){
			event.getGuild().getAudioManager().setSendingHandler(Main.musicManager.getSendHandler());
			Main.audioPlayerManager.loadItem(Main.savedSongsHash.get(songName), new AudioLoadResultHandler() {
				  @Override
				  public void trackLoaded(AudioTrack track) {
				    //trackScheduler.queue(track);
				    Main.musicManager.scheduler.queue(track,Main.savedSongsHash.get(songName));
				    if(Main.musicManager.scheduler.queue.size()==0){
				    	Main.currentPlayingSong = Main.savedSongsHash.get(songName);
				    	System.out.println("Currently playing song: " + Main.currentPlayingSong);
				    }
				    event.getTextChannel().sendMessage("Queued Track #"+Main.musicManager.scheduler.queue.size() + ": " + track.getInfo().title + " With Volume: " + Main.audioPlayer.getVolume()).queue();
					//trackScheduler.queue(track);
				  }

				  @Override
				  public void playlistLoaded(AudioPlaylist playlist) {
				    for (AudioTrack track : playlist.getTracks()) {
				      Main.musicManager.scheduler.queue(track,Main.savedSongsHash.get(songName));
				      if(Main.musicManager.scheduler.queue.size()==0){
					    	Main.currentPlayingSong = Main.savedSongsHash.get(songName);
					    	System.out.println("Currently playing song: " + Main.currentPlayingSong);
					    }
				    }
				    
				    event.getTextChannel().sendMessage("Playing playlist: " + playlist.getName()).queue();
				  }

				  @Override
				  public void noMatches() {
				    // Notify the user that we've got nothing
					 event.getTextChannel().sendMessage("Nothing matched with the url.").queue();
				  }

				  @Override
				  public void loadFailed(FriendlyException throwable) {
				    // Notify the user that everything exploded
				  }
				});
			return;
		}
		String[] words = event.getMessage().getContent().split(" ");
		String link = words[1];
		try{
	    String volume = words[2];
		switch (volume){
		case "quietly":
			Main.audioPlayer.setVolume(10);
			break;
		case "loudly":
			Main.audioPlayer.setVolume(75);
			break;
		case "MAX":
			Main.audioPlayer.setVolume(100);
			break;
		case "normal":
			Main.audioPlayer.setVolume(20);
			break;
		default:
			Main.audioPlayer.setVolume(20);
			break;
		}
		}catch(Exception e){
			Main.audioPlayer.setVolume(10);
		}
//		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
//		AudioSourceManagers.registerLocalSource(audioPlayerManager);
//		AudioPlayer audioPlayer = audioPlayerManager.createPlayer();
//		TrackScheduler trackScheduler = new TrackScheduler(audioPlayer);
//		audioPlayer.addListener(trackScheduler);
//		event.getGuild().getAudioManager().setSendingHandler(musicManager.getSendHandler());
		event.getGuild().getAudioManager().setSendingHandler(Main.musicManager.getSendHandler());
		Main.audioPlayerManager.loadItem(link, new AudioLoadResultHandler() {
			  @Override
			  public void trackLoaded(AudioTrack track) {
			    //trackScheduler.queue(track);
				Main.savedSongs.add(link);
			    Main.musicManager.scheduler.queue(track,link);
			    if(Main.musicManager.scheduler.queue.size()==0){
			    	Main.currentPlayingSong = link;
			    	System.out.println("Currently playing song: " + Main.currentPlayingSong);
			    }
			    event.getTextChannel().sendMessage("Queued Track #"+Main.musicManager.scheduler.queue.size() + ": " + track.getInfo().title + " With Volume: " + Main.audioPlayer.getVolume()).queue();
				//trackScheduler.queue(track);
			  }

			  @Override
			  public void playlistLoaded(AudioPlaylist playlist) {
			    for (AudioTrack track : playlist.getTracks()) {
			      Main.savedSongs.add(link);
			      Main.musicManager.scheduler.queue(track,link);
			      if(Main.musicManager.scheduler.queue.size()==0){
				    	Main.currentPlayingSong = link;
				    	System.out.println("Currently playing song: " + Main.currentPlayingSong);
				    }
			    }
			    
			    event.getTextChannel().sendMessage("Playing playlist: " + playlist.getName()).queue();
			  }

			  @Override
			  public void noMatches() {
			    // Notify the user that we've got nothing
				 event.getTextChannel().sendMessage("Nothing matched with the url.").queue();
			  }

			  @Override
			  public void loadFailed(FriendlyException throwable) {
			    // Notify the user that everything exploded
			  }
			});
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
