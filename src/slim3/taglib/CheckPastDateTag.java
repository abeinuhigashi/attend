package slim3.taglib;

import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import slim3.util.AttendDateUtil;

public class CheckPastDateTag implements Tag {
    
    @SuppressWarnings("all")
    private static final long serialVersionUID = 1L;

    private PageContext pageContext;
    private Tag parent;
    private Date date;
    private boolean isPast;

    public CheckPastDateTag() {
        super();
    }

    public int doStartTag() throws JspException {

        setPast(checkPast(getDate()));

        return EVAL_BODY_INCLUDE;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setPast(boolean isPast) {
        this.isPast = isPast;
    }

    public boolean isPast() {
        return isPast;
    }

    private boolean checkPast(Date targetDate) {
        return AttendDateUtil.checkPast(targetDate);
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public void setPageContext(PageContext pagecontext) {
        this.pageContext = pagecontext;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public Tag getParent() {
        return this.parent;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
    }

}
