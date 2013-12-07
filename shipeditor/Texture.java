// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture
{
	public int id;
	public Texture()
	{
	}
	public void load(String location)
	{
		glBindTexture(GL_TEXTURE_2D, id);
		InputStream in;
		try {
			in = new FileInputStream("files" + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + location);
		    PNGDecoder decoder = new PNGDecoder(in);
		    ByteBuffer buf;
		    IntBuffer tmp=BufferUtils.createIntBuffer(1);
		    GL11.glGenTextures(tmp);
		    tmp.rewind();
		    buf = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		    decoder.decode(buf, decoder.getWidth()*4, Format.RGBA);
		    buf.flip();
		    id = tmp.get(0);
		    glBindTexture(GL11.GL_TEXTURE_2D, id);
		    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
		    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
		    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		    
		    glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		    in.close();
		    System.out.println("Loaded texture: " + location);
		} catch (Exception e)
		{
			System.out.println("Failed to load: " + location);
			e.printStackTrace();
		}
	}
}
