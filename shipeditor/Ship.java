// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import java.io.*;
import java.util.*;

public class Ship
{
	public String Type;
	public String Name;
	public String Author;
	public ArrayList<String> Models;
	public ArrayList<String> Images;
	private transient ArrayList<Object3D> objects;
	public ArrayList<float[]> Voxels;
	private transient ArrayList<Texture> textures;
	
	public Ship()
	{
		Voxels = new ArrayList<float[]>();
		Models = new ArrayList<String>();
		Images = new ArrayList<String>();
		objects = new ArrayList<Object3D>();
		textures = new ArrayList<Texture>();
		Type = "Ship";
		Author = "None";
		Name = "None";
	}
	
	public Ship(String newauth, String newname)
	{
		Voxels = new ArrayList<float[]>();
		Models = new ArrayList<String>();
		Images = new ArrayList<String>();
		objects = new ArrayList<Object3D>();
		textures = new ArrayList<Texture>();
		Type = "Ship";
		Author = newauth;
		Name = newname;
	}
	public void init()
	{
		objects = new ArrayList<Object3D>();
		textures = new ArrayList<Texture>();
		try
		{
			for(int i = 0; i < Images.size(); i++)
			{
				Texture tmp = new Texture();
				tmp.load(Images.get(i));
				textures.add(tmp);
			}
			BufferedReader br = null;
			for(int i = 0; i < Models.size(); i++)
			{
				br = new BufferedReader(new FileReader("files" + OSDetect.sep() + "resources" + OSDetect.sep() + Models.get(i)));
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
	public Texture getTexture(int index)
	{
		return textures.get(index);
	}
	public Object3D getObject(int index)
	{
		return objects.get(index);
	}
	public void addModel(String location)
	{
		Models.add(location);
	}
	public void addTexture(String location)
	{
		Images.add(location);
	}
	public void addCube(float x, float y, float z, float xrot, float yrot, float zrot, float r, float g, float b, int id)
	{
		Voxels.add(new float[] {x, y, z, xrot, yrot, zrot, r, g, b, id});
	}
	public void deleteCube(float x, float y, float z)
	{
		int index = 0;
		for(int i = 0; i < Voxels.size(); i++)
		{
			index = i;
			float[] temp = Voxels.get(index);
			if(temp[0] == x && temp[1] == y && temp[2] == z)
			{
				Voxels.remove(index);
			}
			else
			{
				continue;
			}
		}
	}
	public int getSize()
	{
		return objects.size();
	}
	@Override
	public String toString()
	{
		return "Ship [Type=" + Type + ", Name=" + Name + ", Author=" + Author + ", Models=" + Models + ", Data=" + Voxels + "]";
	}
}
