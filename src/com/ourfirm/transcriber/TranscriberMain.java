package com.ourfirm.transcriber;

import java.io.FileNotFoundException;
import java.io.IOException;


import javax.sound.sampled.UnsupportedAudioFileException;

/* Main Application class. This will be instantiating Transcribe when this will be executed.
 * This is the entry point. In future, the GUI will be interacting with this class to pass in data
 * @author: Partho Ghosh
 * */
public class TranscriberMain {

	public static void main(String[] args) 
	{	
		Transcribe mTranscribeInstance= new Transcribe("/CSOD.wav");
        try {
			mTranscribeInstance.initialize();
		} catch (IOException ioe) {

			ioe.printStackTrace();
		}
        
        try {
			mTranscribeInstance.performTranscription();
		} catch (UnsupportedAudioFileException uafe) {			
			uafe.printStackTrace();
		}
        catch(FileNotFoundException fe)
        {
        	fe.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
