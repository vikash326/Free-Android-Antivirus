package android.free.antivirus;

import free.an.droid.antivirus.rinix.R;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class H extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Log";
	private static final String TABLE_1 = "Infections";
	private static final String TABLE_2 = "LastScan";
	private static final String TABLE_3 = "Statistics";
	private static final String TABLE_4 = "LastInstalledApp";
	private static final String TABLE_5 = "LastInstalledSafeApp";

	private static final String KEY_ID = "_id";
	private static final String KEY_TITLE = "app_title";
	private static final String KEY_PACKAGE = "package_name";
	private static final String KEY_VERSION = "version_name";
	private static final String KEY_VERSION_CODE = "version_code";
	private static final String KEY_SRC = "src";
	private static final String KEY_SIZE = "size";
	private static final String KEY_COMMENT = "desc";

	private static final String KEY_INFECTIONS = "no_infections";
	private static final String KEY_FILES = "no_files";
	private static final String KEY_DELETIONS = "no_deletions";
	private static final String KEY_DURATION = "duration";
	private static final String KEY_TIME = "time";

	private static final String KEY_TOTAL_INFECTIONS = "total_infections";
	private static final String KEY_TOTAL_FILES = "total_files";
	private static final String KEY_TOTAL_DELETIONS = "total_deletions";
	private static final String KEY_UPDATE_DATE = "update_date";

	public H(Context context) {

		super(context, DATABASE_NAME, null, 1);
	}

	public void onCreate(SQLiteDatabase db) {

		String CREATE_LOG_TABLE = "CREATE TABLE " + TABLE_1 + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
				+ KEY_PACKAGE + " TEXT," + KEY_VERSION + " TEXT,"
				+ KEY_VERSION_CODE + " TEXT," + KEY_SRC + " TEXT," + KEY_SIZE
				+ " TEXT," + KEY_COMMENT + " TEXT" + ")";
		db.execSQL(CREATE_LOG_TABLE);

		String CREATE_LASTSCAN_TABLE = "CREATE TABLE " + TABLE_2 + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FILES
				+ " INTEGER," + KEY_INFECTIONS + " INTEGER," + KEY_DELETIONS
				+ " INTEGER," + KEY_DURATION + " TEXT," + KEY_TIME + " TEXT"
				+ ")";
		db.execSQL(CREATE_LASTSCAN_TABLE);

		String CREATE_STAT_TABLE = "CREATE TABLE " + TABLE_3 + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TOTAL_FILES
				+ " INTEGER," + KEY_TOTAL_INFECTIONS + " INTEGER,"
				+ KEY_TOTAL_DELETIONS + " INTEGER," + KEY_UPDATE_DATE + " TEXT"
				+ ")";
		db.execSQL(CREATE_STAT_TABLE);

		String CREATE_LASTAPP_TABLE = "CREATE TABLE " + TABLE_4 + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT,"
				+ KEY_PACKAGE + " TEXT," + KEY_VERSION + " TEXT,"
				+ KEY_VERSION_CODE + " TEXT," + KEY_SRC + " TEXT," + KEY_SIZE
				+ " TEXT," + KEY_COMMENT + " TEXT" + ")";
		db.execSQL(CREATE_LASTAPP_TABLE);

		String CREATE_LASTSAFEAPP_TABLE = "CREATE TABLE " + TABLE_5 + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PACKAGE
				+ " TEXT" + ")";
		db.execSQL(CREATE_LASTSAFEAPP_TABLE);

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void newInfection(In a) {

		SQLiteDatabase db = this.getReadableDatabase();
		String q = "SELECT * FROM " + TABLE_1 + " WHERE " + KEY_SRC + " = "
				+ "\"" + a.getInstallDir() + "\"";
		Cursor c = db.rawQuery(q, null);
		if (c.getCount() == 0) {
			db = this.getWritableDatabase();
			q = "INSERT INTO " + TABLE_1 + " (" + KEY_TITLE + "," + KEY_PACKAGE
					+ "," + KEY_VERSION + "," + KEY_VERSION_CODE + ","
					+ KEY_SRC + ") VALUES(" + "\"" + a.getTitle() + "\"" + ","
					+ "\"" + a.getPackageName() + "\"" + "," + "\""
					+ a.getVersionName() + "\"" + "," + "\""
					+ a.getVersionCode() + "\"" + "," + "\""
					+ a.getInstallDir() + "\"" + ")";
			db.execSQL(q);
		}

		c.close();
		db.close();
	}

	public void lastAppIsThreat(In a) {

		SQLiteDatabase db = this.getWritableDatabase();

		String q = "INSERT INTO " + TABLE_4 + " (" + KEY_TITLE + ","
				+ KEY_PACKAGE + "," + KEY_VERSION + "," + KEY_VERSION_CODE
				+ "," + KEY_SRC + ") VALUES(" + "\"" + a.getTitle() + "\""
				+ "," + "\"" + a.getPackageName() + "\"" + "," + "\""
				+ a.getVersionName() + "\"" + "," + "\"" + a.getVersionCode()
				+ "\"" + "," + "\"" + a.getInstallDir() + "\"" + ")";
		db.execSQL(q);
		db.close();
	}

	public In getLastAppThreat() {

		SQLiteDatabase db = this.getReadableDatabase();

		String q = "SELECT * FROM " + TABLE_4;

		Cursor c = db.rawQuery(q, null);
		In t = null;

		while (c.moveToNext()) {

			t = new In(c.getString(1), c.getString(2), c.getString(3),
					c.getInt(4), c.getString(5), c.getString(6), c.getString(7));

		}

		c.close();
		db.close();

		return t;
	}

	public List<In> getAllInfections() {

		SQLiteDatabase db = this.getReadableDatabase();

		List<In> infectionsList = new ArrayList<In>();

		String q = "SELECT * FROM " + TABLE_1;

		Cursor c = db.rawQuery(q, null);

		while (c.moveToNext()) {

			In t = new In(c.getString(1), c.getString(2), c.getString(3),
					c.getInt(4), c.getString(5), c.getString(6), c.getString(7));
			infectionsList.add(t);
		}

		c.close();
		db.close();

		return infectionsList;
	}

	public void emptyTable() {

		SQLiteDatabase db = this.getWritableDatabase();
		String q = "SELECT * FROM " + TABLE_1;
		Cursor c = db.rawQuery(q, null);
		int start = 0;
		if (c.moveToNext()) {
			start = c.getInt(0);
		}
		for (int i = 1; i <= c.getCount(); i++, start++) {
			String q1 = "DELETE FROM " + TABLE_1 + " WHERE _id = " + start;
			db.execSQL(q1);

		}

		c.close();
		db.close();

	}

	public void threatDeleted(String name) {
		SQLiteDatabase db = this.getWritableDatabase();
		String q = "DELETE FROM " + TABLE_1 + " WHERE package_name = " + "\""
				+ name + "\"";
		db.execSQL(q);
		db.close();

	}

	public void updateLastScan(int files, int infections, int deletions,
			String duration, String when) {

		SQLiteDatabase db = this.getWritableDatabase();
		String q = "SELECT * FROM " + TABLE_2;
		Cursor c = db.rawQuery(q, null);
		int start = 0;
		if (c.moveToNext()) {
			start = c.getInt(0);
		}
		String q1 = "DELETE FROM " + TABLE_2 + " WHERE _id = " + start;
		db.execSQL(q1);

		String q2 = "INSERT INTO " + TABLE_2 + " (" + KEY_FILES + ","
				+ KEY_INFECTIONS + "," + KEY_DELETIONS + "," + KEY_DURATION
				+ "," + KEY_TIME + ") VALUES ( " + files + "," + infections
				+ "," + deletions + "," + "\"" + duration + "\"" + "," + "\""
				+ when + "\"" + ")";
		db.execSQL(q2);
		c.close();
		db.close();

	}

	public void updateLastDeletions(int deletions) {

		SQLiteDatabase db = this.getWritableDatabase();
		String q = "UPDATE " + TABLE_2 + " SET " + KEY_DELETIONS + "="
				+ deletions;
		db.execSQL(q);
		db.close();

	}

	public LScan getLastScan() {

		SQLiteDatabase db = this.getReadableDatabase();
		String q = "SELECT * FROM " + TABLE_2;

		Cursor c = db.rawQuery(q, null);
		if (c.moveToNext() && c.getCount() > 0) {
			LScan t = new LScan(c.getInt(1), c.getInt(2), c.getInt(3),
					c.getString(4), c.getString(5));
			c.close();
			db.close();
			return t;
		} else {
			c.close();
			db.close();
			return null;
		}

	}

	public void LastSafeApp(String t) {
		SQLiteDatabase db = this.getWritableDatabase();

		String q = "INSERT INTO " + TABLE_5 + " (" + KEY_PACKAGE + ") VALUES ("
				+ "\"" + t + "\"" + ")";
		db.execSQL(q);
		db.close();

	}

	public String getLastSafeApp() {

		SQLiteDatabase db = this.getReadableDatabase();

		String q = "SELECT * FROM " + TABLE_5;

		Cursor c = db.rawQuery(q, null);
		String t = null;

		while (c.moveToNext()) {

			t = c.getString(1);

		}

		c.close();
		db.close();

		return t;
	}

	public int getInfectionsCount() {

		SQLiteDatabase db = this.getReadableDatabase();

		String q = "SELECT * FROM " + TABLE_1;

		Cursor c = db.rawQuery(q, null);
		int t = c.getCount();

		c.close();
		db.close();

		return t;

	}

	public void updateStat(int t1, int t2) {
		SQLiteDatabase db = this.getReadableDatabase();
		String q = "SELECT * FROM " + TABLE_3;
		Cursor c = db.rawQuery(q, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			int tot_files = c.getInt(1);
			int tot_inf = c.getInt(2);

			t1 += tot_files;
			t2 += tot_inf;
			q = "UPDATE " + TABLE_3 + " SET " + KEY_TOTAL_FILES + "=" + t1
					+ "," + KEY_TOTAL_INFECTIONS + "=" + t2;
		} else {
			q = "INSERT INTO " + TABLE_3 + " (" + KEY_TOTAL_FILES + ","
					+ KEY_TOTAL_INFECTIONS + ") VALUES (" + t1 + "," + t2 + ")";
		}
		db = this.getWritableDatabase();

		db.execSQL(q);
		c.close();
		db.close();

	}

	public void updateStat_del(int t) {
		SQLiteDatabase db = this.getReadableDatabase();
		String q = "SELECT * FROM " + TABLE_3;
		Cursor c = db.rawQuery(q, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			if (c.getInt(3) > 0) {
				int tot_del = c.getInt(3);
				t += tot_del;
			}

			q = "UPDATE " + TABLE_3 + " SET " + KEY_TOTAL_DELETIONS + "=" + t;
		} else {
			q = "INSERT INTO " + TABLE_3 + " (" + KEY_TOTAL_DELETIONS
					+ ") VALUES (" + t + ")";
		}
		db = this.getWritableDatabase();

		db.execSQL(q);
		c.close();
		db.close();

	}

	public void updateStat_date(String t) {
		SQLiteDatabase db = this.getReadableDatabase();
		String q = "SELECT * FROM " + TABLE_3;
		Cursor c = db.rawQuery(q, null);

		if (c.getCount() > 0) {
			c.moveToFirst();
			q = "UPDATE " + TABLE_3 + " SET " + KEY_UPDATE_DATE + "=" + "\""
					+ t + "\"";

		} else {
			q = "INSERT INTO " + TABLE_3 + " (" + KEY_UPDATE_DATE
					+ ") VALUES (" + "\"" + t + "\"" + ")";
		}
		db = this.getWritableDatabase();

		db.execSQL(q);
		c.close();
		db.close();

	}

	public AVStat getAVStat() {
		SQLiteDatabase db = this.getWritableDatabase();
		String q = "SELECT * FROM " + TABLE_3;
		Cursor c = db.rawQuery(q, null);
		int tot_files = 0;
		int tot_inf = 0;
		int tot_del = 0;
		String date_time = "Unavailable";

		if (c.getCount() > 0) {
			c.moveToFirst();
			if (c.getInt(1) > 0) {
				tot_files = c.getInt(1);
				tot_inf = c.getInt(2);
			}
			if (c.getInt(3) > 0) {
				tot_del = c.getInt(3);
			}
			if (c.getString(4) != null) {
				date_time = c.getString(4);
			}

		}

		c.close();
		db.close();

		return (new AVStat(tot_files, tot_inf, tot_del, date_time));

	}

}
