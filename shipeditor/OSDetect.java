// * * * * * * * * * * * * * * * * * * * * * * \\
// Author: Overkill                            \\
// Product: Trillek Ship Editor                \\
// License: GPL v3                             \\
// Date of Creation: December 6, 2013          \\
// * * * * * * * * * * * * * * * * * * * * * * \\

package shipeditor;

public class OSDetect
{
	public static boolean isWindows()
	{
		boolean windows = (System.getProperty("os.name").toLowerCase().indexOf("win") > 0);
		return windows;
	}
	public static boolean isMac()
	{
		boolean mac = (System.getProperty("os.name").toLowerCase().indexOf("mac") > 0);
		return mac;
	}
	public static boolean isLinux()
	{
		boolean linux = (System.getProperty("os.name").toLowerCase().indexOf("linux") > 0);
		return linux;
	}
	public static boolean isUnix()
	{
		boolean unix = (System.getProperty("os.name").toLowerCase().indexOf("nix") > 0);
		return unix;
	}
	public static String getName()
	{
		return System.getProperty("os.name");
	}
	public static String getVersion()
	{
		return System.getProperty("os.version");
	}
	public static String sep()
	{
		return System.getProperty("file.separator");
	}
}
