package commands;

import javax.activation.CommandObject;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand implements command{

	@Override
	public boolean called(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void action(String[] args, MessageReceivedEvent event) {
		// TODO Auto-generated method stub
		event.getTextChannel().sendMessage("Commands:\n!play <link> \n!connect \n!policy \n!ping \n!save <name> - saves current playing song to a custom name \n").queue();
		event.getTextChannel().sendMessage("Use \"bot\" and one of these words to do a command: \nlouder\nquiet\nnext\nstop\npause\nresume\nwhats good\nmax").queue();
		
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
