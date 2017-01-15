package io.youtubebot.discordbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import commands.PlayCommand;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.collections4.map.LinkedMap;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
  private final AudioPlayer player;
  public final BlockingQueue<AudioTrack> queue;
  public static ArrayList<AudioTrack> tracks = new ArrayList<AudioTrack>();
  public static boolean isPlaying = false;
  public static BlockingQueue<String> songNames;
  public static Stack<String> stack;
  private int counter = 0;
  /**
   * @param player The audio player this scheduler uses
   */
  public TrackScheduler(AudioPlayer player) {
    this.player = player;
    this.queue = new LinkedBlockingQueue<>();
    this.songNames = new LinkedBlockingQueue<>();
    this.stack = new Stack<String>();
  }

  /**
   * Add the next track to queue or play right away if nothing is in the queue.
   *
   * @param track The track to play or add to queue.
   */
  public void queue(AudioTrack track, String url) {
    // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
    // something is playing, it returns false and does nothing. In that case the player was already playing so this
    // track goes to the queue instead.
    if (!player.startTrack(track, true)) {
      Main.musicManager.scheduler.songNames.offer(url);
      Main.musicManager.scheduler.stack.push(url);
      Main.musicManager.scheduler.queue.offer(track);
    }
    
    //tracks.add(track);
    //if(!isPlaying){
    	//isPlaying = true;
    	///player.startTrack(tracks.get(counter), false);
    	//counter++;
    //}
    
  }
  
  @Override
  public void onPlayerPause(AudioPlayer player) {
    // Player was paused
  }

  @Override
  public void onPlayerResume(AudioPlayer player) {
    // Player was resumed
  }

  @Override
  public void onTrackStart(AudioPlayer player, AudioTrack track) {
    // A track started playing
	  //YOU LEFT OFF HERE DECIDING WHTHER TO COMMENT THIS OUT OR NOT
	  //player.startTrack(track, true);
  }

  /**
   * Start the next track, stopping the current one if it is playing.
   */
  public void nextTrack() throws EmptyStackException{
    // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
    // giving null to startTrack, which is a valid argument and will simply stop the player.
	  ////Main.currentPlayingSong = songNames.poll();
	  if(!stack.isEmpty())
		  Main.currentPlayingSong = stack.pop();
	  Main.musicManager.player.startTrack(queue.poll(), false);
	  System.out.println("Currently playing song: " + Main.currentPlayingSong);
	  //player.startTrack(tracks.get(counter), true);
	  //counter++;
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
    if (endReason.mayStartNext) {
      nextTrack();
    }
  }
}