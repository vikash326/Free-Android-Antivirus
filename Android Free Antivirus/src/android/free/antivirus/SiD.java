package android.free.antivirus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.SQLException;
import free.an.droid.antivirus.rinix.R;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class SiD extends SQLiteOpenHelper {

	private static String DB_PATH;

	private static String DB_NAME = "vxoid.bin";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public SiD(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
		DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			/*
			 * File x = new File(DB_PATH + DB_NAME); x.delete();
			 * 
			 * this.getReadableDatabase();
			 * 
			 * try {
			 * 
			 * copyDataBase();
			 * 
			 * } catch (IOException e) {
			 * 
			 * throw new Error("Error copying database");
			 * 
			 * }
			 */
		} else {

			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase() {

		File dbFile = new File(DB_PATH + DB_NAME);
		return dbFile.exists();
	}

	private void copyDataBase() throws IOException {

		try {
			InputStream myInput = myContext.getAssets().open(DB_NAME);

			Dfr.d(myInput, DB_PATH + DB_NAME);

			// String outFileName = DB_PATH + DB_NAME;

			/*
			 * OutputStream myOutput = new FileOutputStream(outFileName);
			 * 
			 * byte[] buffer = new byte[1024]; int length; while ((length =
			 * myInput.read(buffer))>0){ myOutput.write(buffer, 0, length); }
			 * 
			 * myOutput.flush(); myOutput.close();
			 */
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public List<StStruct> getStrings(int fileID) {

		List<StStruct> ssList = new ArrayList<StStruct>();
		String query;

		switch (fileID) {
		case 0x0a786564:
		case 0x464c457f:
		case 0x11223344:
		case 0x214f3558:
			query = "SELECT * FROM stringInfo WHERE fid =" + fileID
					+ " ORDER BY vid ASC";
			break;
		default:
			query = "SELECT * FROM stringInfo WHERE fid =0x00";
			break;

		}

		Cursor c = myDataBase.rawQuery(query, null);

		while (c.moveToNext()) {
			StStruct s = new StStruct(toByteArray(c.getString(1)), c.getInt(3));
			ssList.add(s);
		}
		c.close();

		return ssList;

	}

	public int isInDNA(int id1, int id2) {
		String q = "SELECT vid FROM signatureInfo WHERE signature1 = " + id1
				+ " and signature2 = " + id2;
		Cursor c = myDataBase.rawQuery(q, null);
		int vid = -1;

		if (c.moveToNext()) {
			vid = c.getInt(0);
		}
		c.close();

		return vid;
	}

	public String getVname(int id) {
		String q = "SELECT * FROM virusName WHERE _id = " + id;
		Cursor c = myDataBase.rawQuery(q, null);
		String name = "";

		if (c.moveToNext()) {
			name = c.getString(1);
		}
		c.close();

		return name;
	}

	public String[] getDatInfo() {
		String q = "SELECT * FROM datInfo";
		Cursor c = myDataBase.rawQuery(q, null);

		c.moveToFirst();
		String[] t = { c.getInt(3) + "", c.getString(1) };
		c.close();

		return t;
	}

	private byte[] toByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
					.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public void crush() {
		File d = new File(DB_PATH + DB_NAME);
		d.delete();
	}

}
