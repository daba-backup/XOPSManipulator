package com.daxie.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.daxie.tool.DateFunctions;

public class LogFile {
	private static String directory="./";
	private static String filename="log.txt";
	
	//|0|0|TRACE|DEBUG|INFO|WARN|ERROR|FATAL|
	public static final int LOG_LEVEL_FATAL=0b00000001;
	public static final int LOG_LEVEL_ERROR=0b00000010;
	public static final int LOG_LEVEL_WARN=0b00000100;
	public static final int LOG_LEVEL_INFO=0b00001000;
	public static final int LOG_LEVEL_DEBUG=0b00010000;
	public static final int LOG_LEVEL_TRACE=0b00100000;
	private static int log_level_flags=LOG_LEVEL_FATAL|LOG_LEVEL_ERROR;
	
	private static BufferedWriter bw=null;
	
	public static void SetLogDirectory(String a_directory) {
		directory=a_directory;
	}
	public static void SetLogFilename(String a_filename) {
		filename=a_filename;
	}
	public static void SetLogLevelFlags(int flags) {
		log_level_flags=flags;
	}
	
	public static void OpenLogFile() {
		File log_directory=new java.io.File(directory);
		if(log_directory.exists()==false)log_directory.mkdirs();
		
		try {
			bw=new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(directory+"/"+filename)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public static void CloseLogFile() {
		if(bw!=null) {
			try {
				bw.close();
				bw=null;
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void Flush() {
		if(bw!=null) {
			try {
				bw.flush();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void WriteString(String str) {
		if(bw!=null) {
			try {
				bw.write(str);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void WriteLine(String str) {
		if(bw!=null) {
			try {
				bw.write(str);
				bw.newLine();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//TRACE
	public static void WriteTrace(String str) {
		int op=log_level_flags&LOG_LEVEL_TRACE;
		if(op==0)return;
		
		String message="";
		
		message+="TRACE ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
	//DEBUG
	public static void WriteDebug(String str) {
		int op=log_level_flags&LOG_LEVEL_DEBUG;
		if(op==0)return;
		
		String message="";
		
		message+="DEBUG ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
	//INFO
	public static void WriteInfo(String str) {
		int op=log_level_flags&LOG_LEVEL_INFO;
		if(op==0)return;
		
		String message="";
		
		message+="INFO ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
	//WARN
	public static void WriteWarn(String str) {
		int op=log_level_flags&LOG_LEVEL_WARN;
		if(op==0)return;
		
		String message="";
		
		message+="WARN ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
	//ERROR
	public static void WriteError(String str) {
		int op=log_level_flags&LOG_LEVEL_ERROR;
		if(op==0)return;
		
		String message="";
		
		message+="ERROR ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
	//FATAL
	public static void WriteFatal(String str) {
		int op=log_level_flags&LOG_LEVEL_FATAL;
		if(op==0)return;
		
		String message="";
		
		message+="FATAL ";
		message+=DateFunctions.GetDateStringToMilliseconds();
		message+=" ";
		message+=str;
		
		WriteLine(message);
	}
}
