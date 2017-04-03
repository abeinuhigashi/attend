package slim3.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;
import org.slim3.util.DateUtil;

import slim3.meta.PracticeMeta;
import slim3.model.Practice;

import com.google.appengine.api.datastore.Key;

public class PracticeService {

    public final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm";
    public final String DATE_PATTERN = "yyyy-MM-dd";
    public final String TIME_PATTERN = "HH:mm";

    private PracticeMeta t = new PracticeMeta();

    /**
     * 練習情報を1つ登録します
     * 
     * @param practice
     *            登録対象の練習情報
     * @return
     * @throws IllegalArgumentException
     *             入力不備例外
     */
    public Practice regist(Practice practice) throws IllegalArgumentException {
        return regist(practice, null);
    }

    /**
     * 練習情報を1つ登録します
     * 
     * @param practice
     *            登録対象の練習情報
     * @return
     * @throws IllegalArgumentException
     *             入力不備例外
     */
    public Practice regist(Practice practice, GlobalTransaction gtx)
            throws IllegalArgumentException {

        if (searchFromStartDateTime(practice.getStartDate()) != null) {
            throw new IllegalArgumentException("既に存在する練習情報です。");
        }

        // Transactionが指定されていないとき
        if (gtx == null) {
            gtx = Datastore.beginGlobalTransaction();
            gtx.put(practice);
            gtx.commit();
        } else {
            gtx.put(practice);
        }

        return practice;
    }

    /**
     * Keyで1件取得を行います
     * 
     * @param key
     *            key
     * @return 練習情報
     */
    public Practice searchFromKey(Key key) {
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    /**
     * 練習情報を取得します。 開始日時が遅い順に指定数。
     * 
     * @param num
     *            取得する個数
     */
    public List<Practice> search(int num) {
        return Datastore.query(t).sort(t.startDate.asc).limit(num).asList();
    }

    /**
     * 練習情報を取得します 日付指定をした場合は、指定日以降のみ。
     * 
     * @param date
     *            指定日
     * @param num
     *            個数
     * @return
     */
    public List<Practice> search(Date date, int num) {
        if (date == null) {
            return search(num);
        } else {
            return Datastore
                .query(t)
                .filter(t.startDate.greaterThanOrEqual(date))
                .sort(t.startDate.asc)
                .asList();
        }
    }

    /**
     * 日付幅を指定して練習日のリストを取得します。
     * 
     * @param fromDate
     * @param toDate
     * @return
     * @throws IllegalArgumentException
     */
    public List<Practice> searchList(Date fromDate, Date toDate)
            throws IllegalArgumentException {

        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException(
                "Error! Input fromDate or toDate is null.");
        }

        return Datastore
            .query(t)
            .filter(t.startDate.greaterThanOrEqual(fromDate))
            .filterInMemory(t.startDate.lessThanOrEqual(toDate))
            .sort(t.startDate.asc)
            .asList();
    }

    /**
     * 日付幅を指定してメール送信対象の練習日のリストを取得します。
     * 
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<Practice> searchListForMail(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException(
                "Error! Input fromDate or toDate is null.");
        }

        return Datastore
            .query(t)
            .filter(t.needSendMail.equal(true))
            .filter(t.startDate.greaterThanOrEqual(fromDate))
            .filterInMemory(t.startDate.lessThanOrEqual(toDate))
            .sort(t.startDate.asc)
            .asList();
    }

    /**
     * 開始日時で練習情報を１件取得します
     * 
     * @param startDate
     *            開始日時
     * @return 練習情報
     */
    public Practice searchFromStartDateTime(Date startDate) {
        return Datastore
            .query(t)
            .filter(t.startDate.equal(startDate))
            .asSingle();
    }

    /**
     * 開始月を指定して練習情報を複数取得します。
     * 
     * @param startDate
     *            開始日
     */
    public List<Practice> searchFromYearAndMonth(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1); // 1月は0になるらしい・・・
        Date fromDate = DateUtil.clearTimePart(cal.getTime());
        cal.add(Calendar.MONTH, 1);
        Date toDate = DateUtil.clearTimePart(cal.getTime());

        return Datastore
            .query(t)
            .filter(t.startDate.greaterThanOrEqual(fromDate))
            .filterInMemory(t.startDate.lessThan(toDate))
            .asList();
    }

    /**
     * 開始日を指定して練習情報を複数取得します。
     * 
     * @param startDate
     *            開始日
     */
    public List<Practice> search(Date startDate) {

        Date fromDate = DateUtil.clearTimePart(startDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.DATE, 1);
        Date toDate = cal.getTime();

        return Datastore
            .query(t)
            .filter(t.startDate.greaterThan(fromDate))
            .filterInMemory(t.startDate.lessThan(toDate))
            .asList();
    }

    public List<Practice> searchForMail(Date startDate) {

        Date fromDate = DateUtil.clearTimePart(startDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.DATE, 1);
        Date toDate = cal.getTime();

        return Datastore
            .query(t)
            .filter(t.needSendMail.equal(true))
            .filter(t.startDate.greaterThan(fromDate))
            .filterInMemory(t.startDate.lessThan(toDate))
            .asList();

    }

    /**
     * 指定練習日時以降の練習日を取得します。 指定件数まで。
     * 
     * @param startDate
     * @param limit
     * @return
     */
    public List<Key> searchKeyListFromDate(Date fromDate, int limit) {

        return Datastore
            .query(t)
            .filter(t.startDate.greaterThanOrEqual(fromDate))
            .limit(limit)
            .asKeyList();
    }

    /**
     * 指定練習日時より過去の練習日を取得します。 指定件数まで。
     * 
     * @param toDate
     * @param limit
     * @return
     */
    public List<Key> searchKeyListToDate(Date toDate, int limit) {

        List<Key> rtnList =
            Datastore
                .query(t)
                .filter(t.startDate.lessThan(toDate))
                .sort(t.startDate.desc)
                .limit(limit)
                .asKeyList();

        Collections.reverse(rtnList);

        return rtnList;
    }

    /**
     * 日付幅を指定して練習日のリストのキーを取得します。
     * 
     * @param fromDate
     * @param toDate
     * @return
     * @throws IllegalArgumentException
     */
    public List<Key> searchKeyListFromDateSpan(Date fromDate, Date toDate) {

        if (fromDate == null || toDate == null) {
            return null;
        }

        return Datastore
            .query(t)
            .filter(t.startDate.greaterThanOrEqual(fromDate))
            .filterInMemory(t.startDate.lessThanOrEqual(toDate))
            .sort(t.startDate.asc)
            .asKeyList();
    }

    /**
     * 日付幅を指定して練習日のリストのキーを取得します。
     * 
     * @param fromDate
     * @param toDate
     * @return
     * @throws IllegalArgumentException
     */
    public List<Key> searchKeyListFromDateSpanForCalc(Date fromDate, Date toDate) {

        if (fromDate == null || toDate == null) {
            return null;
        }

        List<Practice> pracList =
            Datastore
                .query(t)
                .filter(t.needCalc.equal(true))
                .filter(t.startDate.greaterThanOrEqual(fromDate))
                .filterInMemory(t.startDate.lessThanOrEqual(toDate))
                .sort(t.startDate.asc)
                .asList();

        List<Key> keyList = new ArrayList<Key>();
        for (Practice practice : pracList) {
            keyList.add(practice.getKey());
        }

        return keyList;
    }

    /**
     * Keyをもとに指定した数だけ先の練習日のKeyを取得します。 指定数に満たない場合は最も未来の練習日のKeyを取得します。
     * １件も取得できない場合はnullを返却します。
     * 
     * @param key
     * @param count
     * @return
     */
    public Key getNextPracticeKey(Key key, int count) {
        List<Key> keyList =
            Datastore
                .query(t)
                .filter(
                    t.startDate.greaterThan(searchFromKey(key).getStartDate()))
                .sort(t.startDate.asc)
                .limit(count)
                .asKeyList();
        if (keyList == null || keyList.isEmpty()) {
            return null;
        } else {
            return keyList.get(keyList.size() - 1);
        }
    }

    /**
     * Keyをもとに指定した数だけ先の練習日のKeyを取得します。 指定数に満たない場合は最も過去の練習日のKeyを取得します。
     * １件も取得できない場合はnullを返却します。
     * 
     * @param key
     * @param count
     * @return
     */
    public Key getPreviousPracticeKey(Key key, int count) {
        List<Key> keyList =
            Datastore
                .query(t)
                .filter(t.startDate.lessThan(searchFromKey(key).getStartDate()))
                .sort(t.startDate.desc)
                .limit(count)
                .asKeyList();
        if (keyList == null || keyList.isEmpty()) {
            return null;
        } else {
            return keyList.get(0);
        }
    }

    /**
     * 練習情報を更新します。
     * 
     * @param practice
     *            更新する練習情報
     * @return 更新後の練習情報
     * @throws IllegalArgumentException
     *             入力不備例外
     */
    public Practice update(Practice practice) throws IllegalArgumentException {

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();

        Practice testingPractice =
            searchFromStartDateTime(practice.getStartDate());

        if (testingPractice != null) {
            if (testingPractice
                .getKey()
                .toString()
                .equals(practice.getKey().toString())) {
                // 練習開始日時が更新されていない場合は、通常の更新
                gtx.put(practice);
            } else {
                // 更新後の練習開始日時がすでに登録されている場合、エラーとする。
                throw new IllegalArgumentException("更新後の練習開始日時はすでに登録されています。");
            }
        } else {
            // 練習開始日時が更新された場合、既存の練習情報を削除して、新しく練習情報を作成する。
            delete(practice.getKey(), gtx);
            practice = regist(practice, gtx);
        }

        gtx.commit();

        return practice;
    }

    /**
     * Keyで練習情報を削除します。
     * 
     * @param key
     *            key
     */
    public boolean delete(Key key) {
        return delete(key, null);
    }

    /**
     * Keyで練習情報を削除します。
     * 
     * @param key
     * @param tx
     *            current Transaction
     */
    public boolean delete(Key key, GlobalTransaction gtx) {

        Practice practice = searchFromKey(key);

        if (practice == null) {
            return false;
        } else {
            AttendanceService svc = new AttendanceService();
            if (gtx == null) {
                // Transactionが指定されていないとき
                gtx = Datastore.beginGlobalTransaction();
                gtx.delete(key);
                svc.deleteList(searchFromKey(key).getStartDate(), gtx);
                gtx.commit();
            } else {
                gtx.delete(key);
                svc.deleteList(searchFromKey(key).getStartDate(), gtx);
            }
            return true;
        }
    }

    /**
     * 開始日時で練習情報を削除します。
     * 
     * @param startDate
     */
    public boolean delete(Date startDate) {

        Practice practice = searchFromStartDateTime(startDate);

        if (practice != null) {
            delete(practice.getKey());
            return true;
        } else {
            return false;
        }
    }

}
