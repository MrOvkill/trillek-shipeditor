// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import java.io.*;
import java.util.*;

import org.lwjgl.opengl.GL11;

public class Ship
{
	public String type;
	public String name;
	public String author;
	public ArrayList<String> models;
	public ArrayList<String> images;
	public ArrayList<Object3D> objects;
	public ArrayList<float[]> voxels;
	public ArrayList<Texture> textures;
	
	public Ship()
	{
		voxels = new ArrayList<float[]>();
		models = new ArrayList<String>();
		images = new ArrayList<String>();
		objects = new ArrayList<Object3D>();
		textures = new ArrayList<Texture>();
		type = "Ship";
		author = "None";
		name = "None";
	}
	
	public Ship(String newauth, String newname)
	{
		voxels = new ArrayList<float[]>();
		models = new ArrayList<String>();
		images = new ArrayList<String>();
		objects = new ArrayList<Object3D>();
		textures = new ArrayList<Texture>();
		type = "Ship";
		author = newauth;
		name = newname;
	}
	public void init()
	{
		try
		{
			for(int i = 0; i < images.size(); i++)
			{
				Texture tmp = new Texture();
				tmp.load(images.get(i));
				textures.add(tmp);
			}
			BufferedReader br = null;
			for(int i = 0; i < models.size(); i++)
			{
				br = new BufferedReader(new FileReader("files" + OSDetect.sep() + "resources" + OSDetect.sep() + models.get(i)));
				objects.add(new Object3D(br, true));
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println("IT DONE DID FAILED!");
			e.printStackTrace();
		}
	}
	@Override
	public String toString()
	{
		return "Ship [Type=" + type + ", Name=" + name + ", Author=" + author + ", Models=" + models + ", Data=" + voxels + "]";
	}
}
