package io.youtubebot.discordbot;

import java.awt.geom.Ellipse2D;

import javax.print.MultiDocPrintService;
import javax.sound.midi.Track;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.w3c.dom.events.EventException;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

import commands.GameCommand;
import commands.SaveCommand;
import net.dv8tion.jda.client.events.group.GroupJoinEvent;
import net.dv8tion.jda.client.events.group.GroupUserJoinEvent;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import java.util.Random;

public class BotListener extends ListenerAdapter{
	public static String[] responses = {"Sure.","Alright coo.","OK.","Alright.","No Problemo.","Fo sho.","For sure." ,"You got it!", "No problem.","Your wish is my command.","Yes sir.","Yessir.","Ye I gotchu fam.","OK one sec."};
	public static Random rand = new Random();
	@Override
	public void onMessageReceived(MessageReceivedEvent event){
		
		String message = event.getMessage().getContent().toLowerCase();
		String c = Main.caseMethod;
		if(Main.waitingForResponse){
			if(event.getAuthor().getId().equals(Main.waitingForResponseID)){
				Main.responseNumber = Main.yesOrNo(message);
				Main.waitingForResponse= false;
				SaveCommand.replacePlaylist(event);
			}
		}
		
		if((event.getAuthor().getId().equals(Main.tekID) || Main.allowOthers || Main.admins.contains(event.getAuthor().getId())) && event.getMessage().getContent().toLowerCase().contains("bot") && (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())){
			if(event.getMessage().getContent().toLowerCase().contains("resume")){
				event.getTextChannel().sendMessage(responses[rand.nextInt(responses.length)]).queue();
				Main.musicManager.player.setPaused(false);
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("pause")){
				event.getTextChannel().sendMessage(responses[rand.nextInt(responses.length)]).queue();
				Main.musicManager.player.setPaused(true);
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("whats good")){
				event.getTextChannel().sendMessage("What's good tho, " + event.getAuthor().getName() + "?").queue();
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("quiet")){
				event.getTextChannel().sendMessage(responses[rand.nextInt(responses.length)]).queue();
				Main.musicManager.player.setVolume(3);
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("next")){
				event.getTextChannel().sendMessage("Next track coming right up!").queue();
				Main.musicManager.scheduler.nextTrack();
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("stop")){
				event.getTextChannel().sendMessage("Stoppin this track real quick.").queue();
				Main.musicManager.player.stopTrack();
			}else if(event.getMessage().getContent().toLowerCase().contains("outta here")){
				event.getTextChannel().sendMessage("Peace.").queue();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Main.jda.shutdown();
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("louder")){
				event.getTextChannel().sendMessage("Bumpin it up a little louder.").queue();
				if(Main.musicManager.player.getVolume()<=90)
					Main.musicManager.player.setVolume(Main.musicManager.player.getVolume()+10);;
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("max")){
				event.getTextChannel().sendMessage("Max volume set.").queue();
				Main.musicManager.player.setVolume(100);;
				return;
			}else if(event.getMessage().getContent().toLowerCase().contains("get")){
				try{
					VoiceChannel channel = event.getJDA().getVoiceChannelById(event.getMember().getVoiceState().getChannel().getId());
					event.getChannel().sendMessage("Connected to room: " + event.getMember().getVoiceState().getChannel().getName()).queue();
					AudioManager manager = channel.getGuild().getAudioManager();
					manager.openAudioConnection(channel);
					}catch(Exception e){
						if(!Main.admins.contains(event.getAuthor().getId()))
							event.getChannel().sendMessage("STOP fronting! You aren't in a channel. Go in one then come back n talk to me.").queue();
						else
							event.getChannel().sendMessage("You aren't in a channel, M'Lord.").queue();
					}
			}else if(event.getMessage().getContent().toLowerCase().contains("love")){
				event.getTextChannel().sendMessage("Love ya too, " + event.getAuthor().getName() + "!").queue();
				return;
			}else if(message.contains("help")){
				event.getTextChannel().sendMessage("Commands:\n!play <link> \n!connect \n!policy \n!ping \n!save <name> - saves current playing song to a custom name \n").queue();
				event.getTextChannel().sendMessage("Use \"bot\" and one of these words to do a command: \nlouder\nquiet\nnext\nstop\npause\nresume\nwhats good\nmax").queue();
			}else if(message.contains("reset")){
				event.getTextChannel().sendMessage("Resetting queue.").queue();
				Main.musicManager.scheduler.queue.removeAll(Main.musicManager.scheduler.queue);
				
			}else if(message.contains("current") && message.contains("song")){	
				event.getTextChannel().sendMessage("The current song is: " + Main.musicManager.player.getPlayingTrack().getInfo().title).queue();
				return;
			}else if(message.contains("debug")){
				event.getTextChannel().sendMessage(Main.waitingForResponseID + " " + Main.waitingForResponse).queue();
				return;
			}
			
			
			return;
			
		}else if(!Main.allowOthers && event.getMessage().getContent().toLowerCase().contains("bot") && (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())){
			event.getTextChannel().sendMessage("Denied Access.").queue();
			return;
		}
		if(GameCommand.nextWord && (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())){
			GameCommand.checkAnswer(event.getMessage().getContent(), event);
		}
		if((event.getAuthor().getId().equals(Main.tekID) || Main.allowOthers || Main.admins.contains(event.getAuthor().getId())) && event.getMessage().getContent().contains("bot") && event.getMessage().getContent().contains("whats good") && (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())){
			event.getTextChannel().sendMessage("What's good tho, " + event.getAuthor().getName() + "?").queue();
		}
		
		if((event.getAuthor().getId().equals(Main.tekID) || Main.allowOthers || Main.admins.contains(event.getAuthor().getId())) && event.getMessage().getContent().startsWith("!") && (event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())){
			Main.handleCommand(Main.PARSER.parse(event.getMessage().getContent().toLowerCase(),event));
			System.out.println("Successfully Handled Message: " + event.getMessage().getContent());
		}else if((event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId() && (!event.getAuthor().getId().equals(Main.tekID) && event.getMessage().getContent().startsWith("!")))){
			event.getTextChannel().sendMessage("Denied Access.").queue();;
		}
	}
	
	@Override
	public void onReady(ReadyEvent event){
		//Main.log("status","Logged in as " + event.getJDA().getSelfUser().getName());
	}
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event){
		event.getMember().getGuild().getPublicChannel().sendMessage("Welcome to the club, " + event.getMember().getUser().getName() + ".").queue();
		System.out.println("Member " + event.getMember().getUser().getName() + " has joined.");
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event){
		String[] messages = {"Welcome back, ", "Yo welcome back ","Glad to have you back, "};
		Random random = new Random();
		if(event.getJDA().getSelfUser().getId() != event.getMember().getUser().getId())
			event.getMember().getGuild().getPublicChannel().sendMessage(messages[random.nextInt(messages.length)] + event.getMember().getUser().getName() + ".").queue();
	}
}
