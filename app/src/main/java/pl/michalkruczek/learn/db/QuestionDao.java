package pl.michalkruczek.learn.db;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "QUESTION".
*/
public class QuestionDao extends AbstractDao<Question, Long> {

    public static final String TABLENAME = "QUESTION";

    /**
     * Properties of entity Question.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CategoryId = new Property(1, Long.class, "categoryId", false, "CATEGORY_ID");
        public final static Property Question = new Property(2, String.class, "question", false, "QUESTION");
        public final static Property Answer = new Property(3, String.class, "answer", false, "ANSWER");
        public final static Property Describe = new Property(4, String.class, "describe", false, "DESCRIBE");
        public final static Property AddDate = new Property(5, java.util.Date.class, "addDate", false, "ADD_DATE");
        public final static Property NextRepeat = new Property(6, java.util.Date.class, "nextRepeat", false, "NEXT_REPEAT");
        public final static Property Level = new Property(7, Integer.class, "level", false, "LEVEL");
        public final static Property History = new Property(8, String.class, "history", false, "HISTORY");
    }

    private DaoSession daoSession;


    public QuestionDao(DaoConfig config) {
        super(config);
    }
    
    public QuestionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"QUESTION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CATEGORY_ID\" INTEGER," + // 1: categoryId
                "\"QUESTION\" TEXT," + // 2: question
                "\"ANSWER\" TEXT," + // 3: answer
                "\"DESCRIBE\" TEXT," + // 4: describe
                "\"ADD_DATE\" INTEGER," + // 5: addDate
                "\"NEXT_REPEAT\" INTEGER," + // 6: nextRepeat
                "\"LEVEL\" INTEGER," + // 7: level
                "\"HISTORY\" TEXT);"); // 8: history
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"QUESTION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Question entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long categoryId = entity.getCategoryId();
        if (categoryId != null) {
            stmt.bindLong(2, categoryId);
        }
 
        String question = entity.getQuestion();
        if (question != null) {
            stmt.bindString(3, question);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(4, answer);
        }
 
        String describe = entity.getDescribe();
        if (describe != null) {
            stmt.bindString(5, describe);
        }
 
        java.util.Date addDate = entity.getAddDate();
        if (addDate != null) {
            stmt.bindLong(6, addDate.getTime());
        }
 
        java.util.Date nextRepeat = entity.getNextRepeat();
        if (nextRepeat != null) {
            stmt.bindLong(7, nextRepeat.getTime());
        }
 
        Integer level = entity.getLevel();
        if (level != null) {
            stmt.bindLong(8, level);
        }
 
        String history = entity.getHistory();
        if (history != null) {
            stmt.bindString(9, history);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Question entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long categoryId = entity.getCategoryId();
        if (categoryId != null) {
            stmt.bindLong(2, categoryId);
        }
 
        String question = entity.getQuestion();
        if (question != null) {
            stmt.bindString(3, question);
        }
 
        String answer = entity.getAnswer();
        if (answer != null) {
            stmt.bindString(4, answer);
        }
 
        String describe = entity.getDescribe();
        if (describe != null) {
            stmt.bindString(5, describe);
        }
 
        java.util.Date addDate = entity.getAddDate();
        if (addDate != null) {
            stmt.bindLong(6, addDate.getTime());
        }
 
        java.util.Date nextRepeat = entity.getNextRepeat();
        if (nextRepeat != null) {
            stmt.bindLong(7, nextRepeat.getTime());
        }
 
        Integer level = entity.getLevel();
        if (level != null) {
            stmt.bindLong(8, level);
        }
 
        String history = entity.getHistory();
        if (history != null) {
            stmt.bindString(9, history);
        }
    }

    @Override
    protected final void attachEntity(Question entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Question readEntity(Cursor cursor, int offset) {
        Question entity = new Question( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // categoryId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // question
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // answer
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // describe
            cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)), // addDate
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // nextRepeat
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // level
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // history
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Question entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCategoryId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setQuestion(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAnswer(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescribe(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAddDate(cursor.isNull(offset + 5) ? null : new java.util.Date(cursor.getLong(offset + 5)));
        entity.setNextRepeat(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setLevel(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setHistory(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Question entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Question entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Question entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getCategoryDao().getAllColumns());
            builder.append(" FROM QUESTION T");
            builder.append(" LEFT JOIN CATEGORY T0 ON T.\"CATEGORY_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Question loadCurrentDeep(Cursor cursor, boolean lock) {
        Question entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Category category = loadCurrentOther(daoSession.getCategoryDao(), cursor, offset);
        entity.setCategory(category);

        return entity;    
    }

    public Question loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Question> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Question> list = new ArrayList<Question>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Question> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Question> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
