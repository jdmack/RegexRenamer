/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regexrenamer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class RegexRenamer {
	
    private String directoryPath;
    private String regex;
    private String nameFormat;

    ArrayList<File> files;

    public RegexRenamer()
    {
		files = new ArrayList<File>();
    }

    public RegexRenamer(String directoryPath)
    {
        this();
        this.directoryPath = directoryPath;
    }

	public void setDirectory(String directoryPath)
    {
		this.directoryPath = directoryPath;
    }

	public void setRegex(String regex)
    {
		this.regex = regex;
    }

	public void setNameFormat(String nameFormat)
    {
		this.nameFormat = nameFormat;
    }
	
	public boolean execute()
    {
		System.out.println("Executing with directory: " + directoryPath + ", regex: " + regex + ", nameFormat: " + nameFormat);
		populateFiles(directoryPath, files);	
		matchFiles(regex, files);	
        renameAll(regex, nameFormat, files);


        return true;
    }

	private void populateFiles(String directoryPath, ArrayList<File> files)
    {
		System.out.println("populating files for directory: " + directoryPath);
        if (!files.isEmpty()) {
			files.clear();		
        }

        File dir = new File(directoryPath);
        files.addAll(Arrays.asList(dir.listFiles()));
		System.out.println("files found: " + files.size());

    }

    private void matchFiles(String regexPattern, ArrayList<File> files)
    {
		System.out.println("matching files");
        int i = 0;

		while (i < files.size()) {

            File thisFile = files.get(i);
			
            if (!thisFile.getName().matches(regexPattern)) {
				files.remove(i);
            }
            else {
				++i;
            }
        }
		System.out.println("matched files: " + files.size());
    }
	
	private void renameAll(String regexPattern, String nameFormat, ArrayList<File> files)
    {
		System.out.println("renaming all files");
		for (File thisFile: files) {
			try {
            	renameFile(regexPattern, nameFormat, thisFile);
			}
			catch (java.lang.IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			}
        }
    }

	private void renameFile(String regexExpression, String nameFormat, File file) throws java.lang.IndexOutOfBoundsException
    {
		System.out.println("\tRenaming file: " + file.getName());

		String name = file.getName();
		String newName = name.replaceAll(regexExpression, nameFormat);

		System.out.println("\t\tRenaming [" + file.getName() + "] to [" + newName + "]");

		File parent = file.getParentFile();

		File newFile = new File(parent.getAbsolutePath() + File.separator + newName);

		file.renameTo(newFile);

    }

}
