package commands;

import java.util.List;

import javax.print.attribute.standard.MediaName;

import org.w3c.dom.events.EventException;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RevokeCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(Main.admins.contains(event.getAuthor().getId())){
			return true;
		}
		else
			return false;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		String user;
		Member member;
		try{
			user = event.getMessage().getContent().replaceAll("!revoke ", "");
			String id = event.getAuthor().getId();
			List<Member> adminRekoveId = event.getGuild().getMembersByName(user, true);
			member = adminRekoveId.get(0);
		}catch(Exception e){
			event.getTextChannel().sendMessage("No one here by that name.").queue();
			return;
		}
		if(member.getUser().getId().equals(Main.tekID)){
			event.getTextChannel().sendMessage("Charlton activates his hidden trap card! Your admin powers have been revoked.").queue();
			Main.admins.remove(event.getAuthor().getId());
		}else if(Main.admins.contains(member.getUser().getId()) && !member.getUser().getId().equals(event.getAuthor().getId())){
			event.getTextChannel().sendMessage(user + "'s admin powers have been revoked.").queue();
			Main.admins.remove(member.getUser().getId());		
		}else if (Main.admins.contains(member.getUser().getId()) && member.getUser().getId().equals(event.getAuthor().getId())){
			event.getTextChannel().sendMessage("What are you, some kind of masochist?").queue();
		}else{
				event.getTextChannel().sendMessage("They aren't an admin tho.").queue();		
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
