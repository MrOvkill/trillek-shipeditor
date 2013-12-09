// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package org.trillek.shipeditor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.*;

import overkill.idonedid.conflib.ConfigParser;
import shipeditor.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main
{
	static FPSCamera player;
	int dx, dy;
	static boolean g = true;
	static boolean xz, m, xy, xs, ld, ls, lx, lz, zx, zy, ee, rm, em, zz;
	static float walkspeed;
	static Object3D cursor;
	static Vector3f cursorpos;
	public static Ship ship;
	public static boolean run = true;
	public static WindowLoadModel wlm = new WindowLoadModel();; 
	private static int idx = 0;
	private static boolean za;
	//WindowLoadModel wlm = new WindowLoadModel();
	//wlm.create("Load Model", 300, 200);
	public static void dump()
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(ship);
		try
		{
			File dir = new File("ships");
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter("ships" + OSDetect.sep() + ship.Name.toLowerCase().replace(" ", "") + ".json"));
			bw.write(json);
			bw.close();
		}
		catch(Exception e)
		{
			
		}
		System.out.println("Ship saved");
	}
	public static Ship load(String name)
	{
		Ship s = new Ship();
		try
		{
			Gson gson = new Gson();
			File dir = new File("ships");
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			BufferedReader br = new BufferedReader(new FileReader("ships" + OSDetect.sep() + name.toLowerCase().replace(" ", "") + ".json"));
			s = gson.fromJson(br, Ship.class);
			s.init();
		}
		catch(Exception e)
		{
			
		}
		return s;
	}
	private static String getTime(String fmt)
	{
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat (fmt);
		return ft.format(dNow);
	}
	private static void screenshot()
	{
		Date dNow = new Date( );
		String fmt = "png";
		try {
			fmt = ConfigParser.getStr("Screenshot Format");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd-hh-mm-ss");
	    if(!new File("screenshots").exists())
	    {
	    	new File("screenshots").mkdirs();
	    }
	    String name = "screenshots" + OSDetect.sep() + ft.format(dNow) + "." + fmt;
	    File file = new File(name);
	    glReadBuffer(GL_FRONT);
	    int width = Display.getDisplayMode().getWidth();
	    int height= Display.getDisplayMode().getHeight();
	    int bpp = 4;
	    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
	    glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer );
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    
	    for(int x = 0; x < width; x++)
	    {
	    	for(int y = 0; y < height; y++)
	    	{
	    		int i = (x + (width * y)) * bpp;
	    		int r = buffer.get(i) & 0xFF;
	    		int g = buffer.get(i + 1) & 0xFF;
	    		int b = buffer.get(i + 2) & 0xFF;
	    		image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
	    	}
	    }
	      
	    try {
	    	ImageIO.write(image, fmt, file);
	    } catch (Exception e) { e.printStackTrace(); }
	    System.out.println("[MCE] Screenshot taken!  Saved as: " + name);
	}
	private static void report()
	{
		SimpleSocket s = new SimpleSocket();
		try
		{
			System.out.println("[MCE] Creating Report...");
			String data = "S:Type=Report\n"+
					   "S:GameName=Trillek-Ship-Editor"+
					   "S:Date=" + getTime("MMM-dd-yyyy : hh:mm:ss") + "\n" +
					   "S:OpenGL Version=" + glGetString(GL_VERSION)+"\n"+
					   "S:OpenGL Vendor=" + glGetString(GL_VENDOR)+"\n"+
					   "S:OS Name=" + OSDetect.getName()+"\n"+
					   "S:OS Version=" + OSDetect.getVersion()+"\n"+
					   "S:OS Architecture=" + System.getProperty("os.arch")+"\n"+
					   "S:Java Vendor=" + System.getProperty("java.vendor")+"\n"+
					   "S:Java Version=" + System.getProperty("java.version")+"\n";
			s.send("overkill.idonedid.com", 55590, data);
			System.out.println("[MCE] Report sent successfully!");
			System.out.println("[MCE] Creating local copy...");
			File f = new File("reports");
			if(!f.exists())
			{
				f.mkdirs();
			}
			else
			{
				BufferedWriter br = new BufferedWriter(new FileWriter("reports" + OSDetect.sep() + getTime("MMM-dd-yyyy : hh:mm:ss") + ".report"));
				br.write(data);
				br.close();
			}
			System.out.println("[MCE] Done!");
		}
		catch(Exception e)
		{
			System.out.println("Could not create report!");
			e.printStackTrace();
			return;
		}
	}
	public void start()
	{
		ConfigParser.select("config.cfg");
		try
		{
			m = false;
			walkspeed = ConfigParser.getFloat("Walking Speed");
			//DisplayMode[] modes = Display.getAvailableDisplayModes();
			//Display.setDisplayMode(modes[modes.length-2]);
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle(ConfigParser.getStr("Title") + "v" + ConfigParser.getStr("Version"));
			//Display.setFullscreen(false);
			Display.setIcon(IconLoader.load("files" + OSDetect.sep() + "resources" + OSDetect.sep() + "Icon.png"));
			Display.create();
			BufferedReader br = new BufferedReader(new FileReader("files" + OSDetect.sep() + "resources" + OSDetect.sep() + "cube.obj"));
			cursor = new Object3D(br, true);
			cursorpos = new Vector3f(0, 2, 0);
			Mouse.setGrabbed(false);
			Display.setVSyncEnabled(true);
			dx = Mouse.getDX();
			dy = Mouse.getDY();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void initGL()
	{
		player = new FPSCamera(0, -8, 0);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(65.f, 800.0f / 600.0f, 0.01f, 500.0f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	private static void cube(float x, float y, float z, float xrot, float yrot, float zrot, float r, float g, float b, int id)
	{
		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, ship.getTexture(id).id);
		glColor4f(r, g, b, 1f);
		glScalef(1, 1, 1);
		glTranslatef(x, y, z);
		glRotatef(xrot, 1.f, 0.f, 0.f);
		glRotatef(yrot, 0.f, 1.f, 0.f);
		glRotatef(zrot, 0.f, 0.f, 1.f);
		ship.getObject(id).opengldraw();
		glPopMatrix();
	}
	
	private static void cursor()
	{
		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor4f(1, 0, 0, 0.1f);
		glTranslatef(cursorpos.x, cursorpos.y, cursorpos.z);
		glScalef(1.1f, 1.1f, 1.1f);
		cursor.opengldraw();
		glPopMatrix();
	}
	
	public static void display()
	{
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glLoadIdentity();
        player.lookThrough();
        
        for(int x = 0; x < ship.Voxels.size(); x++)
		{
			float[] temp = ship.Voxels.get(x);
			cube(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8], (int)temp[9]);
		}
		
		cursor();
	}
	public static void checkInput()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed())
		{
			shutdown();
		}
		if(Display.isCloseRequested())
		{
			shutdown();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && Mouse.isGrabbed())
		{
			player.walkForward(walkspeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && Mouse.isGrabbed())
		{
			player.walkBackwards(walkspeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A) && Mouse.isGrabbed())
		{
			player.strafeLeft(walkspeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && Mouse.isGrabbed())
		{
			player.strafeRight(walkspeed);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Mouse.isGrabbed())
		{
			player.goUp(walkspeed * 1.5f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Mouse.isGrabbed())
		{
			player.goDown(walkspeed * 1.5f);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F1) && Mouse.isGrabbed())
		{
			try {
				Display.setFullscreen(!Display.isFullscreen());
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F2) && Mouse.isGrabbed())
		{
			screenshot();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F3) && xz && Mouse.isGrabbed())
		{
			report();
			xz = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_F3) && !xz && Mouse.isGrabbed())
		{
			xz = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F) && xy)
		{
			Mouse.setGrabbed(!Mouse.isGrabbed());
			xy = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_F) && !xy)
		{
			xy = true;
		}
		
		if(Mouse.isGrabbed())
		{
			player.pitch(-(Mouse.getDY() * 0.1f));
			player.yaw((Mouse.getDX() * 0.1f));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && xs && Mouse.isGrabbed())
		{
			dump();
			xs = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !xs && Mouse.isGrabbed())
		{
			xs = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && ld && Mouse.isGrabbed())
		{
			cursorpos.x += 2;
			ld = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && !ld && Mouse.isGrabbed())
		{
			ld = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) && ls && Mouse.isGrabbed())
		{
			cursorpos.x -= 2;
			ls = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_LEFT) && !ls && Mouse.isGrabbed())
		{
			ls = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) && lx && Mouse.isGrabbed())
		{
			cursorpos.z -= 2;
			lx = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_UP) && !lx && Mouse.isGrabbed())
		{
			lx = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && lz && Mouse.isGrabbed())
		{
			cursorpos.z += 2;
			lz = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !lz && Mouse.isGrabbed())
		{
			lz = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_Z) && zx && Mouse.isGrabbed())
		{
			cursorpos.y -= 2;
			zx = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_Z) && !zx && Mouse.isGrabbed())
		{
			zx = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_X) && zy && Mouse.isGrabbed())
		{
			cursorpos.y += 2;
			zy = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_X) && !zy && Mouse.isGrabbed())
		{
			zy = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN) && ee && Mouse.isGrabbed())
		{
			ship.deleteCube(cursorpos.x, cursorpos.y, cursorpos.z);
			ship.addCube(cursorpos.x, cursorpos.y, cursorpos.z, 0, 0, 0, 0.6f, 0.6f, 0.6f, idx);
			ee = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !ee && Mouse.isGrabbed())
		{
			ee = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS) && rm && Mouse.isGrabbed())
		{
			ship.deleteCube(cursorpos.x, cursorpos.y, cursorpos.z);
			rm = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_MINUS) && !rm && Mouse.isGrabbed())
		{
			rm = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && em && Mouse.isGrabbed())
		{
			Mouse.setGrabbed(false);
			run = false;
			wlm.create();
			em = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_E) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !em && Mouse.isGrabbed())
		{
			em = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_P) && zz && Mouse.isGrabbed())
		{
			if(idx == (ship.getSize() - 1))
			{
				idx = 0;
			}
			else
			{
				idx++;
			}
			zz = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_P) && !zz && Mouse.isGrabbed())
		{
			zz = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L) && za && Mouse.isGrabbed())
		{
			if(idx == 0)
			{
				idx = (ship.getSize() - 1);
			}
			else
			{
				idx--;
			}
			za = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_L) && !za && Mouse.isGrabbed())
		{
			za = true;
		}
		// ld, ls, lx, lz, zx, zy
	}
	public static void run()
	{
		//wlm.hide();
		while (run)
		{
			display();
			checkInput();
            Display.sync(120);
			Display.update();
		}
	}
	public static void shutdown()
	{
		Display.destroy();
		System.out.println("Engine exiting");
		System.exit(0);
	}
	public static void main(String[] args) throws Exception
	{
		UI.init();
		UI.open();
		run = true;
	}
}
