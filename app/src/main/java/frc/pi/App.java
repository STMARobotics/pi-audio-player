/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package frc.pi;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import edu.wpi.first.networktables.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class App {

  private Clip foregroundClip;
  private Clip backgroundClip;

  public static void main(String[] args) throws Exception {
    new App().run(args[0]);
  }

  public App() throws Exception {
    backgroundClip = loadAudioClip("tank driving.wav");
    backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
    backgroundClip.start();
  }

  private Clip loadAudioClip(String clipName) {
    try {
      AudioInputStream tankFiringInputStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(clipName));
      var clip = AudioSystem.getClip();
      clip.open(tankFiringInputStream);
      return clip;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public void run(String serverName) {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Music");
    NetworkTableEntry playEntry = table.getEntry("Play");
    // inst.startClientTeam(7028);  // where TEAM=190, 294, etc, or use inst.startClient("hostname") or similar
    inst.startClient(serverName);
    playEntry.setBoolean(false);

    System.out.println("Main thread: " + Thread.currentThread().getName());
    table.addEntryListener("Play", (tabl, key, entry, value, flags) -> {
      if (value.getBoolean()) {
        System.out.println("Start playing: " + Thread.currentThread().getName());
        foregroundClip = loadAudioClip("tank fire.wav");
        foregroundClip.start();
        System.out.println("Done starting");
      } else {
        System.out.println("Stop playing");
        foregroundClip.stop();
        foregroundClip.setFramePosition(0);
        // foregroundClip.close();
        System.out.println("Done stopping");
      }
    }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);


    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        System.out.println("interrupted");
        return;
      }
    }
  }
}
