package commands;

import java.awt.List;
import java.lang.reflect.Member;
import java.util.Random;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AdminCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(Main.admins.contains(event.getAuthor().getId())){
			return true;
		}
		else{
			Random random = new Random();
			int rand = random.nextInt(5);
			switch (rand){
			
			case 0:
				event.getTextChannel().sendMessage("Heh.").queue();
				break;
			case 1:
				event.getTextChannel().sendMessage("No.").queue();
				break;
			case 2:
				event.getTextChannel().sendMessage("Go get permission first pls.").queue();
				break;
			case 3:
				event.getTextChannel().sendMessage("I dont wanna!").queue();
				break;
			case 4:
				event.getTextChannel().sendMessage("Let's go to TGI instead!").queue();
				break;
			}
			
			return false;
		}
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(Main.admins.contains(event.getAuthor().getId())){
			try{
			String[] words = event.getMessage().getContent().split(" ");
			String link = event.getMessage().getContent().replaceAll("!admin ", "");
			java.util.List<net.dv8tion.jda.core.entities.Member> adminMember = event.getGuild().getMembersByName(link, true);
			net.dv8tion.jda.core.entities.Member member = adminMember.get(0);
			if(!Main.admins.contains(member.getUser().getId())){
			Main.admins.add(member.getUser().getId());
			event.getTextChannel().sendMessage(member.getUser().getName() + " has been added to the cool list.").queue();
			}else{
				event.getTextChannel().sendMessage("They are already an admin.").queue();
			}
			}catch(Exception e){
				event.getTextChannel().sendMessage("There's no one here by that name.").queue();
			}
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
