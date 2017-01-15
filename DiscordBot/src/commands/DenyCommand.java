package commands;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DenyCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(event.getAuthor().getName().equals("tekwiz")){
		return true;
		}
		else{
			event.getTextChannel().sendMessage("Nice try.").queue();
			return false;
		}
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		Main.allowOthers = false;
		event.getTextChannel().sendMessage("Lock down initiated.").queue();
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
