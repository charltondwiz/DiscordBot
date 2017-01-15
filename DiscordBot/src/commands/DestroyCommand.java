package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class DestroyCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		String nuclearCode = event.getMessage().getContent().replaceAll("!destroy ", "");
		if(nuclearCode.equals("3721934873934732489237472365345430257432975349875423050948325"))
			return true;
		else{
			event.getTextChannel().sendMessage("Invalid nuclear code entered. <USAGE> !destroy <nuclearcode>").queue();		
			return false;
		}
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		int i = 0;
		String destruction = "";
		String destruct = "ANONYMOUS_";
		while( i <1000){
			destruction+= "A"+ "\n";
			i++;
		}
		int p = 0;
		while(p<3000) {event.getTextChannel().sendMessage(destruction).queue(); p++;}
		
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
