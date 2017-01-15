package io.youtubebot.discordbot;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.soap.Text;

import org.apache.commons.codec.binary.StringUtils;
import org.objectweb.asm.commons.StaticInitMerger;
import org.w3c.dom.css.Counter;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sun.jna.StringArray;

import java.util.*;

import commands.AdminCommand;
import commands.ConnectCommand;
import commands.DEFPolicyCommand;
import commands.DenyCommand;
import commands.DestroyCommand;
import commands.FreedomCommand;
import commands.GameCommand;
import commands.HelpCommand;
import commands.KillMyselfCommand;
import commands.PingCommand;
import commands.PlayCommand;
import commands.RevokeCommand;
import commands.SaveCommand;
import commands.ShutdownCommand;
import commands.command;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter{
	
	public static ArrayList<String> admins = new ArrayList<String>();
	public static String tekID = "166031229970284546";
	public static String bellyID = "266443091156664320";
	public static boolean allowOthers = true;
	public static AudioPlayerManager audioPlayerManager;
	public static AudioPlayer audioPlayer;
	public static TrackScheduler trackScheduler = new TrackScheduler(audioPlayer);
	public static JDA jda;
	public static final CommandParser PARSER = new CommandParser();
	public static HashMap<String,command> commands = new HashMap<String,command>();
	private static final String token = "MjYzNzg0MDM3MzQ3MzYwNzcw.C0XKuQ.LGpV4hyCHwqtfj4Ls74NNfI3PP0";
	public static String[] words = new String[127142];
	public static GuildMusicManager musicManager;
	public static File file = new File("playlists.txt");
	public static FileWriter fileWriter;
	public static PrintWriter printWriter;
	public static java.util.List<String> savedSongs = new java.util.ArrayList<String>();
	public static HashMap<String, String> savedSongsHash = new HashMap<String,String>();
	public static String currentPlayingSong = "";
	public static Scanner playlistReader;
	public static String waitingForResponseID = "";
	public static boolean waitingForResponse = false;
	public static String caseMethod = "";
	public static String replacePlayList = "";
	public static String replacePlayListWith = "";
	public static int responseNumber = -1;
	public static java.util.List<String> yesResponses = new java.util.ArrayList<String>();
	public static String[] yesResponsesString = {"y","yea","yes","ye","ofc","do it","go ahead","idc","go"};
	public static java.util.List<String> noResponses = new java.util.ArrayList<String>();
	public static String[] noResponsesString = {"n","no","don't","dont","don","hell no","na","nah","not this time","nah"};
	public enum Songs {
		Lost,ChainSmoker,FrankOcean,Juice,ChanceTheRapper
	}
	
	Songs songs;
	
	public static int yesOrNo(String message){
		
		for(String x : yesResponses){
			if(message.contains(x)){
				return 1;
			}
		}
		
		for(String y : noResponses){
			if(message.contains(y)){
				return 0;
			}
		}
		
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException{
		yesResponses.addAll(Arrays.asList(yesResponsesString));
		noResponses.addAll(Arrays.asList(noResponsesString));
		fileWriter = new FileWriter(file, true);
		printWriter = new PrintWriter(fileWriter);
		try {
			playlistReader = new Scanner(file);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		while(playlistReader.hasNextLine()){
			String[] line = playlistReader.nextLine().split(" ");
			String songName = "";
			for(int i = 0; i<line.length-1;i++){
				songName+=line[i];
				if(i<line.length-2){
					songName+=" ";
				}
			}
			String urlLink = line[line.length-1];
			savedSongsHash.put(songName, urlLink);
			
		}
		admins.add(tekID);
		admins.add(bellyID);
		File file = new File("words.txt");
		try {
			int counter = 0;
			Scanner fileReader = new Scanner(file);
			while(fileReader.hasNextLine()){
				words[counter] = fileReader.nextLine();
				counter++;
			}
			fileReader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			jda = new JDABuilder(AccountType.BOT).addListener(new BotListener()).setToken(token).buildBlocking();
			jda.setAutoReconnect(false);
			jda.addEventListener(new Main());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		audioPlayerManager = new DefaultAudioPlayerManager();
		musicManager = new GuildMusicManager(audioPlayerManager);
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		AudioSourceManagers.registerLocalSource(audioPlayerManager);
		audioPlayer = audioPlayerManager.createPlayer();
		TrackScheduler trackScheduler = new TrackScheduler(audioPlayer);
		audioPlayer.addListener(trackScheduler);
		commands.put("save", new SaveCommand());
		commands.put("ping", new PingCommand());
		commands.put("shutdown", new ShutdownCommand());
		commands.put("policy", new DEFPolicyCommand());
		commands.put("connect", new ConnectCommand());
		commands.put("destroy", new DestroyCommand());
		commands.put("deny", new DenyCommand());
		commands.put("freedom", new FreedomCommand());
		commands.put("killmyself", new KillMyselfCommand());
		commands.put("play", new PlayCommand());
		commands.put("admin", new AdminCommand());
		commands.put("revoke", new RevokeCommand());
		commands.put("game", new GameCommand());
		commands.put("help", new HelpCommand());
		//AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		
	}
	
	public static void shutDown(){
		jda.shutdownNow(true);
		System.out.println("Shutdown complete");
	}
	
	public static void handleCommand(CommandParser.CommandContainer cmd){
		System.out.println("Handling Command...");
		if(commands.containsKey(cmd.invoke)){
			boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);
			System.out.println(safe);
			if(safe){
				commands.get(cmd.invoke).action(cmd.args, cmd.event);
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}else{
				commands.get(cmd.invoke).executed(safe, cmd.event);
			}
		}
	}
	
	public static void sendChatMessage(TextChannel channel,String text){
		channel.sendMessage(text).queue();
	}
	
}
