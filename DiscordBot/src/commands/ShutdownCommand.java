package commands;

import io.youtubebot.discordbot.Main;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ShutdownCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		if(Main.admins.contains(event.getAuthor().getId())){
		return true;
		}else{
			event.getTextChannel().sendMessage("Uh my boys, YOU can't shut me down.").queue();	
			return false;
		}
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		event.getTextChannel().sendMessage("Shutting down...").queue();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Main.shutDown();
		
		
	}

	@Override
	public String help() {
		// TODO Auto-generated method stub
		return "help";
	}

	@Override
	public void executed(boolean success, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return;
	}

}
