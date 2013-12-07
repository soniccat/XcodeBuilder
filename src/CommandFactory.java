import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Vector;

public class CommandFactory {
	public Command createCommand(String name, Vector<String> parameters)
	{
		Command resultCommand = null;
		if (name.equals("-build")) {
			
			Iterator<String> iterator = parameters.iterator();
			String parameterName = null;
			String buildString = null;
			String namePrefix = "";
			String nameSuffix = "";
			Path outputPath = Paths.get(".");
			while(iterator.hasNext()) {
				parameterName = iterator.next();
				
				if (parameterName.equals("-c")) {
					buildString = iterator.next();
				
				} else if (parameterName.equals("-o")) {
					outputPath = Paths.get(iterator.next());
					
				} else if (parameterName.equals("-p")) {
					namePrefix = iterator.next();
					
				} else if (parameterName.equals("-s")) {
					nameSuffix = iterator.next();
				}
			}
			
			if (buildString == null) {
				System.out.println("-c (command) not found");
				return null;
			}
			
			XcodeBuildCommand consoleTool = new XcodeBuildCommand(buildString);
			consoleTool.outputPath = outputPath;
			consoleTool.namePrefix = namePrefix;
			consoleTool.nameSuffix = nameSuffix;
			resultCommand = consoleTool;
		
		} else if (name.equals("-symbolicate")) {
			
			Iterator<String> iterator = parameters.iterator();
			String parameterName = null;
			Path crashLogPath = null;
			Path outputPath = null;
			Path searchPath = Paths.get(".");
			String architecture = null;
			Path atosPath = Paths.get("/Applications/Xcode.app/Contents/Developer/usr/bin/atos");
			while(iterator.hasNext()) {
				parameterName = iterator.next();
				
				if (parameterName.equals("-c")) {
					crashLogPath = Paths.get(iterator.next());
				
				} else if (parameterName.equals("-o")) {
					outputPath = Paths.get(iterator.next());
					
				} else if (parameterName.equals("-arch")) {
					architecture = iterator.next();
					
				} else if (parameterName.equals("-atos")) {
					atosPath = Paths.get(iterator.next());
					
				} else if (parameterName.equals("-s")) {
					searchPath = Paths.get(iterator.next());
				}
			}
			
			if (crashLogPath == null) {
				System.out.println("-c (crashlog path) not found");
				return null;
			}
			
			if (outputPath == null) {
				System.out.println("-o (output path) not found");
				return null;
			}
			
			if (architecture == null) {
				System.out.println("-arch (architecture) not found");
				return null;
			}
			
			SymbolicateFilesCommand symbolicateAll = new SymbolicateFilesCommand();
	    	symbolicateAll.crashLogPath = crashLogPath;
	    	symbolicateAll.outputPath = outputPath;

	    	LocalFileSystem fs = new LocalFileSystem(searchPath);
	    	XcodePackageManager pm = new XcodePackageManager(fs);

	    	LocalFileSystem fs2 = new LocalFileSystem(Paths.get("."));
	    	XcodeCrashlogManager cm = new XcodeCrashlogManager(fs2);
	    	fs2.createFolder(symbolicateAll.outputPath);
	    	fs2.setPath(symbolicateAll.outputPath);

	    	symbolicateAll.packageManager = pm;
	    	symbolicateAll.crashLogManger = cm;
	    	symbolicateAll.architecture = architecture;
	    	symbolicateAll.atosPath = atosPath;
	    	
	    	resultCommand = symbolicateAll;
		}
		
		return resultCommand;
	}
}
