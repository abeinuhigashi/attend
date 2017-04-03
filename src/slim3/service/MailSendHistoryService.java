package slim3.service;

import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.GlobalTransaction;

import slim3.meta.MailSendHistoryMeta;
import slim3.model.MailSendHistory;

import com.google.appengine.api.datastore.Key;


public class MailSendHistoryService {
    
    private MailSendHistoryMeta t = new MailSendHistoryMeta();
    
    /**
     * MailSendHistory‚ğ‚PŒì¬‚µ‚Ü‚·B
     * 
     * @param history
     * @return
     */
    public MailSendHistory regist(MailSendHistory history) {
        if(history == null){
            throw new IllegalArgumentException("Error! Input mail history is null.");
        }
        
        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(history);
        gtx.commit();            
        return history;
    }
    
    /**
     * MailSendHistory‚ğ‘SŒæ“¾‚µ‚Ü‚·
     * 
     * @return
     */
    public List<MailSendHistory> searchAll() {
        return Datastore.query(t).asList();
    }
    
    /**
     * MailSendHistory‚ğKey‚Å‚PŒŒŸõ‚µ‚Ü‚·B
     * 
     * @param key
     * @return
     */
    public MailSendHistory searchFromKey(Key key) {
        if(key == null) {
            throw new IllegalArgumentException("Error! Input key is null.");
        }
        return Datastore.query(t).filter(t.key.equal(key)).asSingle();
    }

    /**
     * MailSendHistory‚ğ‚PŒXV‚µ‚Ü‚·
     * 
     * @param history
     * @return
     */
    public MailSendHistory update(MailSendHistory history) {
        if (history == null) {
            throw new IllegalArgumentException("Error! Input mail history is null.");
        } else if (searchFromKey(history.getKey()) == null) {
            throw new IllegalArgumentException("Error! Target history does not exist. Key is " + history.getKey());            
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.put(history);
        gtx.commit();            
        return history;
    }
    
    /**
     * MailSendHistory‚ğ‚PŒíœ‚µ‚Ü‚·
     * 
     * @param history
     * @return
     */
    public boolean delete(MailSendHistory history) {
        if (history == null) {
            throw new IllegalArgumentException("Error! Input mail history is null.");
        } else if (searchFromKey(history.getKey()) == null) {
            throw new IllegalArgumentException("Error! Target history does not exist. Key is " + history.getKey());            
        }

        return delete(history.getKey());
    }
    
    /**
     * MailSendHistory‚ğ‚PŒíœ‚µ‚Ü‚·
     * 
     * @param history
     * @return
     */
    public boolean delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Error! Input mail key is null.");
        } else if (searchFromKey(key) == null) {
            throw new IllegalArgumentException("Error! Target history does not exist. Key is " + key);            
        }

        GlobalTransaction gtx = Datastore.beginGlobalTransaction();
        gtx.delete(key);
        gtx.commit();
        return true;
    }
}
