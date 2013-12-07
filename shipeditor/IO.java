// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

import java.util.jar.*;
import java.util.zip.*;
import java.io.*;

public class IO
{
	public void extractromjar(String jarname, String name){
	     try {
	      JarFile jar = new JarFile(jarname);
	      ZipEntry entry = jar.getEntry(name);
	      File efile = new File(name);

	      InputStream in = 
	         new BufferedInputStream(jar.getInputStream(entry));
	      OutputStream out = 
	         new BufferedOutputStream(new FileOutputStream(efile));
	      byte[] buffer = new byte[4096];
	      for (;;)  {
	        int nBytes = in.read(buffer);
	        if (nBytes <= 0) break;
	        out.write(buffer, 0, nBytes);
	      }
	      out.flush();
	      out.close();
	      in.close();
	      jar.close();
	     }
	     catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
