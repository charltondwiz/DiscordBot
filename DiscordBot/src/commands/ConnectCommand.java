package commands;

import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import io.youtubebot.discordbot.GuildMusicManager;
import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.entities.VoiceState;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class ConnectCommand implements command {
	public static Map<Long, GuildMusicManager> musicManagers;
	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		try{
		VoiceChannel channel = event.getJDA().getVoiceChannelById(event.getMember().getVoiceState().getChannel().getId());
		event.getChannel().sendMessage("We are in guild " + event.getMember().getVoiceState().getChannel().getName()).queue();
		AudioManager manager = channel.getGuild().getAudioManager();
		manager.openAudioConnection(channel);
		}catch(Exception e){
			if(!Main.admins.contains(event.getAuthor().getId()))
				event.getChannel().sendMessage("STOP fronting! You aren't in a channel. Go in one then come back n talk to me.").queue();
			else
				event.getChannel().sendMessage("You aren't in a channel, M'Lord.").queue();
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
