package commands;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class FreedomCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(Main.admins.contains(event.getAuthor().getId())){
		Main.allowOthers = true;
		event.getTextChannel().sendMessage("All defense mechanisms have been lifted.").queue();
		}else{
			event.getTextChannel().sendMessage("Man if there wasn't already freedom yo dumbass wouldn't even be talking right now.").queue();
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
