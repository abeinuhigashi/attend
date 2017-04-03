package slim3.service;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import slim3.meta.MailQueueMeta;
import slim3.model.MailQueue;

import com.google.appengine.api.datastore.Key;


public class MailQueueService {
    
    MailQueueMeta t = new MailQueueMeta();

    /**
     * MailQueue���P���쐬���܂��B
     * 
     * @param que
     * @return
     */
    public MailQueue regist(MailQueue que) {
        if(que == null){
            throw new IllegalArgumentException("Error! Input que is null.");
        }
        
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(que);
        gtx.commit();            
        return que;
    }

    /**
     * MailQueue��S���擾���܂�
     * 
     * @return
     */
    public List<MailQueue> searchAll() {
        return Datastore.query(t).asList();
    }
    
    /**
     * MailQueue���P���擾���܂�
     * 
     * @param key
     * @return
     */
    public MailQueue searchFromKey(Key key) {
        if(key == null) {
            throw new IllegalArgumentException("Error! Input key is null.");
        }
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    /**
     * MailQueue����t�ŕ������擾���܂�
     * 
     * @param date
     * @return
     */
    public List<MailQueue> searchFromDate(Date date) {
        if(date == null) {
            throw new IllegalArgumentException("Error! Input date is null.");
        }
        return Datastore.query(t).filter(t.sendDate.equal(date)).asList();
    }

    /**
     * MailQueue����t�ŕ������擾���A�擾�������̂��폜���܂�
     * 
     * @param date
     * @return
     */
    public List<MailQueue> searchAndDeleteFromDate(Date date) {
        List<MailQueue> list = searchAndDeleteFromDate(date);
        
        if(list == null || list.size() == 0) {
            return null;
        }
        
        if(delete(list)) {
            return list;
        } else {
            throw new IllegalArgumentException("Error! It was not presumed.");
        }
        
    }
    
    /**
     * MailQueue���P���X�V���܂�
     * 
     * @param que
     * @return
     */
    public MailQueue update(MailQueue que) {
        if (que == null) {
            throw new IllegalArgumentException("Error! Input que is null.");
        } else if (searchFromKey(que.getKey()) == null) {
            throw new IllegalArgumentException("Error! Target que does not exist. Key is " + que.getKey());            
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(que);
        gtx.commit();            
        return que;
    }
    
    /**
     * MailQueue���P���폜���܂�
     * 
     * @param key
     * @return
     */
    public boolean delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Error! Input mail key is null.");
        } else if (searchFromKey(key) == null) {
            throw new IllegalArgumentException("Error! Target que does not exist. Key is " + key);            
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.delete(key);
        gtx.commit();
        return true;
    }
    
    /**
     * MailQueue���P���폜���܂�
     * 
     * @param que
     * @return
     */
    public boolean delete(MailQueue que) {
        if (que == null) {
            throw new IllegalArgumentException("Error! Input que is null.");
        } else if (searchFromKey(que.getKey()) == null) {
            throw new IllegalArgumentException("Error! Target que does not exist. Key is " + que.getKey());            
        }

        return delete(que.getKey());
    }

    /**
     * MailQueue�𕡐����폜���܂�
     * 
     * @param list
     * @return
     */
    public boolean delete(List<MailQueue> list) {
        if (list == null || list.size() == 0) {
            throw new IllegalArgumentException("Error! Input list is null.");            
        }
        
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        for(MailQueue que : list) {
            gtx.delete(que.getKey());
        }
        gtx.commit();

        return true;
    }
}
