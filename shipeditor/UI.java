package shipeditor;

public class UI
{
	static WindowStart start;
	public static boolean isopen;
	public static void init()
	{
		start = new WindowStart();
	}
	public static void open()
	{
		start.create("Ship Editor", 300, 200);
	}
}
