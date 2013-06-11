package ls;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LsReader {

	private StringBuilder path;
	private StringBuilder file;

	private long size;

	private static final int FILENAMEPOSITION = 8;
	private static final int FILESIZEPOSITION = 4;

	private String[] patternList;
	private boolean drillDownFlag;

	public LsReader() {
		path = new StringBuilder();
		file = new StringBuilder();
		size = 0;
		drillDownFlag = true;

		path.append("");
		file.append("");

		patternList = null;
	}

	/**
	 * ディレクトリの設定
	 *
	 * @param str
	 */
	public void setDirectory(String str) {
		int length = str.length();
		path.append(str.substring(0, length - 1));

		if (str.charAt(length - 1) != '/') {
			path.append('/');
		}
	}

	/**
	 * ディレクトリの取得
	 *
	 * @return
	 */
	public String getDirecotry() {
		return path.toString();
	}

	/**
	 * ディレクトリのクリア
	 */
	public void clearDirectory() {
		path.setLength(0);
	}

	/**
	 * ファイルの設定
	 *
	 * @param str
	 */
	public void setFile(String str) {
		String[] strArray = str.split("[\\s]+");
		file.append(strArray[FILENAMEPOSITION]);

		size = Integer.parseInt(strArray[FILESIZEPOSITION]);
	}

	/**
	 * ファイルの取得
	 *
	 * @return
	 */
	public String getFile() {
		return this.file.toString();
	}

	/**
	 * ファイルのクリア
	 */
	public void clearFile() {
		file.setLength(0);
	}

	/**
	 * ファイルのフルパス名での取得
	 *
	 * @return
	 */
	public String getFullPathFile() {
		return path.toString() + file.toString();
	}

	/**
	 * ファイルサイズの取得
	 *
	 * @return
	 */
	public long getSize() {
		return this.size;
	}

	/**
	 * パターンの設定
	 *
	 * @param str
	 */
	public void setPattern(String str) {
		patternList = str.split("/");
	}

	/**
	 * パターンマッチング
	 *
	 * @return
	 */
	public boolean isMatchPath() {
		String[] targetList = this.getDirecotry().split("/");
		int targetLength = targetList.length;

		if (targetLength < patternList.length) {
			// System.out.println("短い:target[" + this.getFullPathFile() + "]");
			return false;
		}

		if ((drillDownFlag == false) && (targetLength != patternList.length)) {
			// ドリルダウンしない場合深さが違うものは排除
			return false;
		}

		for (int i = 0; i < patternList.length; i++) {
			if (patternList[i].compareTo("*") == 0) {
				continue;
			}
			if (patternList[i].compareTo(targetList[i]) == 0) {
				;
			} else {
				return false;
			}
		}

		// System.out.println("match[" + this.getFullPathFile() + "]");
		return true;
	}

	/**
	 * ファイルタイプの取得
	 *
	 * @param tmpLine
	 * @return
	 */
	private static FileType getFileType(String tmpLine) {
		char c = tmpLine.charAt(0);
		if (c == '-') {
			return FileType.FILE;
		} else if (c == '/') {
			return FileType.DIRECTORY;
		}

		return FileType.OTHRE;
	}

	private void setDrillDownFlag(boolean flag) {
		// 0: 指定のパス配下も走査する
		// 1: 指定のパス配下は走査しない
		this.drillDownFlag = flag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println("args error.");
			System.exit(1);
		}

		LsReader lsReader = new LsReader();
		lsReader.setDrillDownFlag(Boolean.parseBoolean(args[1]));
		lsReader.setPattern(args[2]);

		try {
			FileReader fr = new FileReader(args[0]);
			BufferedReader bf = new BufferedReader(fr);

			long totalFileNum = 0;
			long totalSize = 0;
			String tmpLine;
			boolean matchFlag = false;
			while ((tmpLine = bf.readLine()) != null) {
				if (tmpLine.length() < 1) {
					continue;
				}

				// ファイルタイプの判定
				FileType fileType = getFileType(tmpLine);
				if ((fileType == FileType.FILE) && (matchFlag == true)) {
					// ファイルかつディレクトリパスが対象の場合
					lsReader.clearFile();
					lsReader.setFile(tmpLine);
					System.out.println(lsReader.getFullPathFile() + "\t"
							+ lsReader.getSize());

					totalFileNum++;
					totalSize += lsReader.getSize();

				} else if (fileType == FileType.DIRECTORY) {
					// ディレクトリ
					lsReader.clearDirectory();
					lsReader.setDirectory(tmpLine);

					// パターンマッチング
					if (lsReader.isMatchPath() == true) {
						matchFlag = true;
					} else {
						matchFlag = false;
					}
				} else {
					// その他
				}
			}

			System.out.println(totalFileNum + "\t" + totalSize);

			bf.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
