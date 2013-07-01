package com.ourfirm.transcriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.UnsupportedAudioFileException;

import edu.cmu.sphinx.frontend.util.AudioFileDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

/*
 * This class will be actually performing the transcriptions
 * This class will be instantiated when the application will come up first.
 * Transcribe contains multiple constructors.. This design may change in future
 * @author: Partho Ghosh
 * */

public class Transcribe {

	private String mAudioFilePath="";
	private String mConfigFilePath="";
	private String mAudioSource="";
	private URL mAudioUrl;
	private URL mConfigUrl;
	private File outputFile;
	ConfigurationManager mConfigurationManager;
	Recognizer mRecognizer;
	AudioFileDataSource mAudioFileDataSource;		
	Result mResult;
	static boolean isConverted=false;
	
	//This constructor initializes the audio path
	public Transcribe(String mAudioFilePath)
	{
		this.mAudioFilePath = mAudioFilePath;
		
	}
	
	
	//This Constructor initializes audio file path, configuration file path and audio source
	public Transcribe(String mAudioFilePath, String mConfigFilePath, String mAudioSource)
	{
		this.mAudioFilePath = mAudioFilePath;
		this.mConfigFilePath = mConfigFilePath;
		this.mAudioSource = mAudioSource;		
	}
	
	//Initialize the transcription engine
	public void initialize() throws IOException
	{
	    
		System.out.println("mAudioFilePath: "+mAudioFilePath);
		mAudioUrl=TranscriberMain.class.getResource(mAudioFilePath);		
		System.out.println("mAudioUrl: "+mAudioUrl);
		
		if(mConfigFilePath.equals(""))
		{
			
			mConfigUrl=getClass().getResource("/config.xml");
		}
		else
		{
			mConfigUrl=getClass().getResource(mConfigFilePath);
		}
		
		System.out.println("mConfigUrl: "+ mConfigUrl);
		mConfigurationManager= new ConfigurationManager(mConfigUrl); //Configuration activated
		lookupConfiguration();		
	}
	
	//perform configuration lookup to get recognizer and audio source
	private void lookupConfiguration()
	{
		mRecognizer= (Recognizer)mConfigurationManager.lookup("recognizer");
		
		if(mAudioSource.equals(""))
		{			
			mAudioFileDataSource= (AudioFileDataSource)mConfigurationManager.lookup("audioFileDataSource");	
		}
		else
		{
			mAudioFileDataSource= (AudioFileDataSource)mConfigurationManager.lookup(mAudioSource);
		}
	
	}
	
	//This method is performing the actual transcription conversion
	public void performTranscription() throws UnsupportedAudioFileException, FileNotFoundException
	{
		mRecognizer.allocate();		
		mAudioFileDataSource.setAudioFile(mAudioUrl, null);
		outputFile= new File("D:/TranscriptionOutput/output.rtf");
		
			try {
				if(!outputFile.exists())
					outputFile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FileOutputStream toOutputFile= new FileOutputStream(outputFile);
			while((mResult=mRecognizer.recognize())!=null)
			{
				String mResultText= mResult.getBestFinalResultNoFiller()+"\n";				
				System.out.println(mResultText);
				try {
					toOutputFile.write(mResultText.getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		System.out. println("File exists: "+ outputFile.exists());
		System.out.println("File path: "+outputFile.getAbsolutePath());
	}

	
	
}
