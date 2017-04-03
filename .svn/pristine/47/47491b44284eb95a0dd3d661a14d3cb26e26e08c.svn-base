package slim3.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class PastTag implements Tag {

    @SuppressWarnings("all")
    private static final long serialVersionUID = 1L;

    private Tag parent;

    public void setPageContext(PageContext pagecontext) {

    }

    public void setParent(Tag parent) {
        this.parent = parent;

    }

    public Tag getParent() {
        return this.parent;
    }

    public int doStartTag() throws JspException {

        CheckPastDateTag checkParent =  (CheckPastDateTag)getParent();

        if(checkParent.isPast()){
            return EVAL_BODY_INCLUDE;
        }else{
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
    }

}
