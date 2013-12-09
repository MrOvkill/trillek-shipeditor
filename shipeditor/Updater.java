package shipeditor;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.apache.commons.io.*;

public class Updater
{
	static ArrayList<String> list;
	public static void fetchUpdate()
	{
		try
		{
			list = new ArrayList<String>();
			String full = "";
			FileUtils.copyURLToFile(new URL("http://overkill.idonedid.com/shipeditor/updates.txt"), new File("updates.txt"));
			full = FileUtils.readFileToString(new File("updates.txt"), "UTF-8");
			//System.out.println(full);
			String[] lines = full.split("\n");
			for(int i = 0; i < lines.length; i++)
			{
				list.add(lines[i]);
			}
			for(int i = 0; i < lines.length; i++)
			{
				System.out.println(list.get(i));
				String[] temp = list.get(i).split("|");
				System.out.println(temp.length);
				String location = list.get(i).split("|")[0];
				String destination = list.get(i).split("|")[1];
				if(OSDetect.isWindows())
				{
					destination = destination.replace("/", "\\");
				}
				File test = new File("files");
				if(!test.exists())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "jar");
				if(!test.exists())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "resources");
				if(!test.exists())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "native");
				if(!test.exists())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "native" + OSDetect.sep() + "windows");
				if(!test.exists() && OSDetect.isWindows())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "native" + OSDetect.sep() + "macosx");
				if(!test.exists() && OSDetect.isMac())
				{
					test.mkdirs();
				}
				test = new File("files" + OSDetect.sep() + "native" + OSDetect.sep() + "linux");
				if(!test.exists() && OSDetect.isLinux())
				{
					test.mkdirs();
				}
				if(location.startsWith("WINDOWS:") && !OSDetect.isWindows())
				{
					continue;
				}
				else
				{
					location = location.replace("WINDOWS:", "");
				}
				if(location.startsWith("LINUX:") && !OSDetect.isLinux())
				{
					continue;
				}
				else
				{
					location = location.replace("LINUX:", "");
				}
				if(location.startsWith("MAC:") && !OSDetect.isMac())
				{
					continue;
				}
				else
				{
					location = location.replace("MAC:", "");
				}
				
				System.out.println("File to get: " + location + " File Destination: " + destination);
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
