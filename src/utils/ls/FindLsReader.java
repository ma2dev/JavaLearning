package utils.ls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindLsReader {

	private static final String CONF_KEYLIST_KEY = "KEY_LIST";
	private static final String CONF_INPUTFILE_KEY = "INPUTFILE";

	private static final String SPLIT_CHAR = ",";

	private static final int LS_STRING_COLUMN_NUM = 11;
	private static final String LS_STRING_SPLIT_REGEX = "\\s+";
	private static final String VERIFICATION_MATCH_NUMBER = "^[0-9]+$";

	// file type by ls
	private static final int LS_STRING_COLUMN_INDEX_FILETYPE = 2;
	private static final char LS_STRING_COLUMN_IGNORE_FILETYPE_CHAR = 'd';
	// size info by ls
	private static final int LS_STRING_COLUMN_INDEX_SIZE = 6;
	// file name by ls
	private static final int LS_STRING_COLUMN_INDEX_FILENAME = 10;

	private static final String REPLACE_YYYYMMDD_FROM = "YYYYMMDD";
	private static final String REPLACE_YYYYMMDD_TO = "\\\\d{8}";

	private List<String> searchPathList;
	private List<String> replacedSearchPathList;
	private List<String> inputfileList;

	private PathInfo pathInfo;

	public FindLsReader(Properties properties) {
		searchPathList = getPropertiesValueList(properties);
		replacedSearchPathList = createMatchSearchPath(searchPathList);
		inputfileList = getInputfileList(properties);

		pathInfo = new PathInfo();
	}

	public boolean read() throws IOException {
		if (inputfileList == null) {
			System.err.println("properties: " + CONF_INPUTFILE_KEY + " is illegal.");
			return false;
		}

		for (String inputfile : inputfileList) {
			// open input file.
			BufferedReader br = new BufferedReader(new FileReader(inputfile));

			String line = null;
			while ((line = br.readLine()) != null) {
				if (isLsString(line) == false) {
					// System.out.println("Not target(illegal) -> " + line);
					continue;
				}

				String[] splitedStr = line.split(LS_STRING_SPLIT_REGEX, 0);
				long size = Long.parseLong(splitedStr[LS_STRING_COLUMN_INDEX_SIZE]);
				// System.out.println("size: " + size);
				String hit = getPath(splitedStr[LS_STRING_COLUMN_INDEX_FILENAME]);
				if (hit == null) {
					// System.out.println("Not target(not hit) -> " + line);
					continue;
				} else {
					// System.out.println("Target(hit) -> " + hit + ", " +
					// line);
				}

				pathInfo.add(hit, size);
			}

			br.close();
		}

		return true;
	}

	/**
	 * result
	 */
	public void printResult() {
		pathInfo.printOn();
	}

	private String getPath(String line) {
		if (line == null) {
			return null;
		}

		String result = null;
		for (String s : replacedSearchPathList) {
			Pattern p = Pattern.compile(s);
			Matcher m = p.matcher(line);

			// boolean judge = line.matches(s);
			boolean judge = m.find();
			if (judge) {
				// System.out.println("T: " + line + ", " + s);
				result = m.group(1);
				break;
			} else {
				// System.out.println("F: " + line + ", " + s);
			}
		}

		return result;
	}

	/**
	 * replace YYYYMMDD -> [0-9]+
	 * 
	 * @return
	 */
	private List<String> createMatchSearchPath(List<String> before) {
		if (before == null) {
			return null;
		}

		List<String> list = new ArrayList<String>();

		String newStr = null;
		for (String s : before) {
			newStr = new String("(" + s.replaceAll(REPLACE_YYYYMMDD_FROM, REPLACE_YYYYMMDD_TO) + ")" + "(.*)");
			list.add(newStr);
		}

		return list;
	}

	private boolean isLsString(String s) {
		boolean result = true;

		if (s == null) {
			return false;
		}

		// check1: coloumn number
		String[] splitedStr = s.split(LS_STRING_SPLIT_REGEX, 0);
		int num = splitedStr.length;
		if (num != LS_STRING_COLUMN_NUM) {
			return false;
		}

		// check2: 1st colomn is number.
		if (splitedStr[0].matches(VERIFICATION_MATCH_NUMBER) == false) {
			return false;
		}

		// check3: file type is normal file.
		String filetype = splitedStr[LS_STRING_COLUMN_INDEX_FILETYPE];
		if (filetype.charAt(0) == LS_STRING_COLUMN_IGNORE_FILETYPE_CHAR) {
			return false;
		}

		return result;
	}

	/**
	 * This function gets the list of files in the target input from a
	 * properties file.
	 * 
	 * @param properties
	 * @return
	 */
	private List<String> getInputfileList(Properties properties) {
		String fileValues = properties.getProperty(CONF_INPUTFILE_KEY);
		if (fileValues == null) {
			System.err.println("properties: " + CONF_INPUTFILE_KEY + " is illegal.");
			return null;
		}

		List<String> list = new ArrayList<String>();
		for (String s : fileValues.split(SPLIT_CHAR, 0)) {
			boolean flag = isExistFile(s);
			if (flag == true) {
				list.add(s);
			} else {
				return null;
			}
		}

		return list;
	}

	private boolean isExistFile(String targetfile) {
		File file = new File(targetfile);
		String msg = "Input file (" + file + ") does not exist.";
		if (file.exists() == false || file.isFile() == false) {
			// file does not exist.
			System.err.println(msg);
			return false;
		}

		return true;
	}

	/**
	 * This function gets a list of paths to be searched from a properties file.
	 * 
	 * @param properties
	 * @return
	 */
	private List<String> getPropertiesValueList(Properties properties) {
		String keys = properties.getProperty(CONF_KEYLIST_KEY);
		if (keys == null) {
			System.err.println("properties: " + CONF_KEYLIST_KEY + " is illegal.");
			return null;
		}
		List<String> propertiesKeyList = getPropertiesKyeList(keys);

		List<String> list = new ArrayList<String>();
		for (String key : propertiesKeyList) {
			String value = properties.getProperty(key);
			if (value == null) {
				System.err.println(key + " is illeagal.");
				return null;
			}
			if (value.equals("")) {
				continue;
			}
			list.add(value);
		}

		return list;
	}

	private List<String> getPropertiesKyeList(String keys) {
		List<String> list = new ArrayList<String>();

		for (String s : keys.split(SPLIT_CHAR, 0)) {
			list.add(s);
		}

		return list;
	}

	/**
	 * Debug print.
	 */
	public void printOnPropertiesValueList() {
		System.out.println("KeyList:");
		if (searchPathList != null) {
			for (String s : searchPathList) {
				System.out.println("\t" + s);
			}
		} else {
			System.out.println("searchPathList is null.");
		}

		System.out.println("InputfileList(replaced):");
		if (replacedSearchPathList != null) {
			for (String s : replacedSearchPathList) {
				System.out.println("\t" + s);
			}
		} else {
			System.out.println("replacedSearchPathList is null.");
		}

		System.out.println("InputfileList:");
		if (inputfileList != null) {
			for (String s : inputfileList) {
				System.out.println("\t" + s);
			}
		} else {
			System.out.println("inputfileList is null.");
		}

	}

	private class PathInfo {

		private List<String> keyList;
		private Map<String, PathDetailInfo> pathMap;

		public PathInfo() {
			keyList = new ArrayList<String>();
			pathMap = new HashMap<String, PathDetailInfo>();
		}

		public boolean add(String path, long size) {
			if (path == null) {
				return false;
			}

			PathDetailInfo detailInfo = pathMap.get(path);
			if (detailInfo == null) {
				detailInfo = new PathDetailInfo(path); // init
				detailInfo.add(size); // num = 1
				keyList.add(path);
				pathMap.put(path, detailInfo);
			} else {
				detailInfo.add(size);
			}

			return true;
		}

		public void printOn() {
			for (String key : keyList) {
				pathMap.get(key).printOn();
			}
		}

		/**
		 * Detail Information of path.
		 * 
		 * @author ma2dev
		 * 
		 */
		private class PathDetailInfo {
			private String path;
			private long size;
			private long num;

			public PathDetailInfo(String path) {
				this.path = path;
				size = 0;
				num = 0;
			}

			/**
			 * add
			 * 
			 * @param size
			 */
			public void add(long size) {
				this.size += size;
				num++;
			}

			/**
			 * print
			 */
			public void printOn() {
				System.out.println(path + "\t" + num + "\t" + size);
			}
		}
	}

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("usage: java FindLsReader properties");
			System.exit(1);
		}

		Properties properties = new Properties();
		try {
			BufferedReader br = new BufferedReader(new FileReader(args[0]));
			properties.load(br);
			FindLsReader findLsReader = new FindLsReader(properties);
			// findLsReader.printOnPropertiesValueList();
			findLsReader.read();
			findLsReader.printResult();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
