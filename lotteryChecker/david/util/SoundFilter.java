package david.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SoundFilter extends FileFilter
	{
	// Accept all directories and all wav files.
	public boolean accept(File f)
		{
		if (f.isDirectory())
			return true;

		String extension=getExtension(f);
		return extension!=null && extension.equals("wav");
		}

	// The description of this filter
	public String getDescription()
		{
		return "*.wav";
		}

	//get the file extension
	public static String getExtension(File f)
		{
		String ext=null;
		String s=f.getName();
		int i=s.lastIndexOf('.');

		if (i>0&&i<s.length()-1)
			{
			ext=s.substring(i+1).toLowerCase();
			}
		return ext;
		}
	}
