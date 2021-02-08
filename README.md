# Raspberry PI Audio Player

This is a Network Table client that can run on a Raspberry Pi to play audio.

## Building
The project uses Gradle. Clone it to your Pi and be build it with the following command:
``` bash
./gradlew build
```

## Running
The project can easily be run using Gradle. It will run on a Raspberry Pi, but it will also run on Windows (and
probably Linux and Mac.)
The program takes the following arguments:
- The address of the Network Table server (the robot)

``` bash
./gradlew run --args="10.70.28.2"
```

## Pi Configuration
### Output device
First, make sure you have selected the right audio output device, the default is HDMI audio. To configure audio, right
click the speaker icon on the desktop and select the output device.

### Java configuration
You may need to edit the `sound.properties` file in the Java conf folder. These settings work on a Raspberry Pi B
running Raspberry Pi OS (Raspbian):

```
javax.sound.sampled.Clip=com.sun.media.sound.DirectAudioDeviceProvider
javax.sound.sampled.Port=com.sun.media.sound.PortMixerProvider
javax.sound.sampled.SourceDataLine=com.sun.media.sound.DirectAudioDeviceProvider
javax.sound.sampled.TargetDataLine=com.sun.media.sound.DirectAudioDeviceProvider
```
This solution was found [here](https://nealvs.wordpress.com/2017/08/11/java-sound-on-a-raspberry-pi-with-openjdk/).

## Using Outline Viewer
In a VS Code with WPILib extensions, press CTRL + SHIFT + P, select _WPILib: Start Tool_, then select _OutlineViewer_.
This will launch the Outline Viewer where you can see and edit Network Tables.

Make sure Server Mode and the Default Port setting are on, then press OK.

Finally, tart the Pi Audio Player application. The application should connect to the Network Table. Now, in Outline
Viewer you can change the Network Table values to trigger events in the player.

## Triggering sound from the robot
The robot sets a network table value to trigger playing the audio file.
``` java
    private final NetworkTableInstance inst = NetworkTableInstance.getDefault();
    private final NetworkTable table = inst.getTable("Music");
    private final NetworkTableEntry playEntry = table.getEntry("Play");

    public void playGun() {
        playEntry.setBoolean(true);
    }
```

## Note
This was tested on a Raspberry Pi B running [Zulu 11 JDK](https://www.azul.com/downloads/zulu-community/?version=java-11-lts&os=linux&architecture=arm-32-bit-hf&package=jdk).
It should work with OpenJDK on a modern Pi, but it has not been tested.