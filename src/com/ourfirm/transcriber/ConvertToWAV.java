package com.ourfirm.transcriber;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class ConvertToWAV {

	public AudioInputStream convertAudioInputStream(AudioInputStream sourceAis, AudioFormat targetFormat) {
		AudioFormat baseFormat = sourceAis.getFormat();
		AudioFormat intermediateFormat;
		AudioInputStream convertedAis = sourceAis;
		
		// First convert the encoding, if necessary
		if (!baseFormat.getEncoding().equals(targetFormat.getEncoding())) {		
			intermediateFormat = new AudioFormat(
					targetFormat.getEncoding(),
					baseFormat.getSampleRate(), baseFormat.getSampleSizeInBits(), baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			convertedAis = AudioSystem.getAudioInputStream(intermediateFormat, sourceAis);
			//this.writeConvertedFile(convertedAis, "C:\\encoding.wav");
			baseFormat = intermediateFormat;
			sourceAis = convertedAis;				
		}

		// Then convert the sample rate
		if (baseFormat.getSampleRate() != targetFormat.getSampleRate()) {			
			intermediateFormat = new AudioFormat(
					baseFormat.getEncoding(),
					targetFormat.getSampleRate(), baseFormat.getSampleSizeInBits(), baseFormat.getChannels(),
					baseFormat.getChannels() * 2, targetFormat.getSampleRate(),
				false);
			convertedAis = AudioSystem.getAudioInputStream(intermediateFormat, sourceAis);
			//this.writeConvertedFile(convertedAis, "C:\\sample.wav");
			baseFormat = intermediateFormat;
			sourceAis = convertedAis;			
		}
	
		// Then convert the number of channels
		if (baseFormat.getChannels() > targetFormat.getChannels()) {
			intermediateFormat = new AudioFormat(
					baseFormat.getEncoding(),
					baseFormat.getSampleRate(), baseFormat.getSampleSizeInBits(), targetFormat.getChannels(),
					targetFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			convertedAis = AudioSystem.getAudioInputStream(intermediateFormat, sourceAis);
			//this.writeConvertedFile(convertedAis, "C:\\channels.wav");
			baseFormat = intermediateFormat;
			sourceAis = convertedAis;	
			
		}
		Transcribe.isConverted=true;
		return convertedAis;
	}
    
	public File writeConvertedFile(AudioInputStream sourceAis, String fileName)
	{
		File tempfile = null;
		fileName = "tempwavfile.wav";
		//fileName = fileName.substring(6, fileName.length()-4) + "_new.wav";

		try
		{
			//This just takes an audio stream, writes it to disk, then plays it the way TALL usually does.
			//it's a test to see if the input stream is readable by the Java audio providers like Tritonus
			//System.out.println(fileName);
			tempfile = new File(fileName);
			AudioSystem.write(sourceAis, AudioFileFormat.Type.WAVE, tempfile);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return  tempfile;
	}
}
