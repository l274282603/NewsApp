package com.leijx.newsapp.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.leijx.newsapp.bean.NewsChannelBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NEWS_CHANNEL_BEAN".
*/
public class NewsChannelBeanDao extends AbstractDao<NewsChannelBean, String> {

    public static final String TABLENAME = "NEWS_CHANNEL_BEAN";

    /**
     * Properties of entity NewsChannelBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property NewsChannelname = new Property(0, String.class, "newsChannelname", true, "NEWS_CHANNELNAME");
        public final static Property NewsChannelId = new Property(1, String.class, "newsChannelId", false, "NEWS_CHANNEL_ID");
        public final static Property NewsChannelType = new Property(2, String.class, "newsChannelType", false, "NEWS_CHANNEL_TYPE");
        public final static Property NewsChannelIndex = new Property(3, int.class, "newsChannelIndex", false, "NEWS_CHANNEL_INDEX");
        public final static Property NewChannelSelect = new Property(4, boolean.class, "newChannelSelect", false, "NEW_CHANNEL_SELECT");
        public final static Property NewsChannelFixed = new Property(5, boolean.class, "newsChannelFixed", false, "NEWS_CHANNEL_FIXED");
    }


    public NewsChannelBeanDao(DaoConfig config) {
        super(config);
    }
    
    public NewsChannelBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NEWS_CHANNEL_BEAN\" (" + //
                "\"NEWS_CHANNELNAME\" TEXT PRIMARY KEY NOT NULL ," + // 0: newsChannelname
                "\"NEWS_CHANNEL_ID\" TEXT NOT NULL ," + // 1: newsChannelId
                "\"NEWS_CHANNEL_TYPE\" TEXT NOT NULL ," + // 2: newsChannelType
                "\"NEWS_CHANNEL_INDEX\" INTEGER NOT NULL ," + // 3: newsChannelIndex
                "\"NEW_CHANNEL_SELECT\" INTEGER NOT NULL ," + // 4: newChannelSelect
                "\"NEWS_CHANNEL_FIXED\" INTEGER NOT NULL );"); // 5: newsChannelFixed
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_NEWS_CHANNEL_BEAN_NEWS_CHANNELNAME ON NEWS_CHANNEL_BEAN" +
                " (\"NEWS_CHANNELNAME\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NEWS_CHANNEL_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, NewsChannelBean entity) {
        stmt.clearBindings();
 
        String newsChannelname = entity.getNewsChannelname();
        if (newsChannelname != null) {
            stmt.bindString(1, newsChannelname);
        }
        stmt.bindString(2, entity.getNewsChannelId());
        stmt.bindString(3, entity.getNewsChannelType());
        stmt.bindLong(4, entity.getNewsChannelIndex());
        stmt.bindLong(5, entity.getNewChannelSelect() ? 1L: 0L);
        stmt.bindLong(6, entity.getNewsChannelFixed() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, NewsChannelBean entity) {
        stmt.clearBindings();
 
        String newsChannelname = entity.getNewsChannelname();
        if (newsChannelname != null) {
            stmt.bindString(1, newsChannelname);
        }
        stmt.bindString(2, entity.getNewsChannelId());
        stmt.bindString(3, entity.getNewsChannelType());
        stmt.bindLong(4, entity.getNewsChannelIndex());
        stmt.bindLong(5, entity.getNewChannelSelect() ? 1L: 0L);
        stmt.bindLong(6, entity.getNewsChannelFixed() ? 1L: 0L);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public NewsChannelBean readEntity(Cursor cursor, int offset) {
        NewsChannelBean entity = new NewsChannelBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // newsChannelname
            cursor.getString(offset + 1), // newsChannelId
            cursor.getString(offset + 2), // newsChannelType
            cursor.getInt(offset + 3), // newsChannelIndex
            cursor.getShort(offset + 4) != 0, // newChannelSelect
            cursor.getShort(offset + 5) != 0 // newsChannelFixed
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, NewsChannelBean entity, int offset) {
        entity.setNewsChannelname(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setNewsChannelId(cursor.getString(offset + 1));
        entity.setNewsChannelType(cursor.getString(offset + 2));
        entity.setNewsChannelIndex(cursor.getInt(offset + 3));
        entity.setNewChannelSelect(cursor.getShort(offset + 4) != 0);
        entity.setNewsChannelFixed(cursor.getShort(offset + 5) != 0);
     }
    
    @Override
    protected final String updateKeyAfterInsert(NewsChannelBean entity, long rowId) {
        return entity.getNewsChannelname();
    }
    
    @Override
    public String getKey(NewsChannelBean entity) {
        if(entity != null) {
            return entity.getNewsChannelname();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(NewsChannelBean entity) {
        return entity.getNewsChannelname() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
