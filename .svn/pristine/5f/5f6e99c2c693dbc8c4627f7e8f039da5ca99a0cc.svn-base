package slim3.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * 出席入力or表示の際のプルダウンを実現タグです
 *
 */
public class AttendanceOptionTag implements Tag {
    private PageContext pageContext;
    private Tag parentTag;
    
    private int attendance;
    private String memberKey;
    private String practiceKey;
    private boolean disable;
    private String classAttr;

    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public void setParent(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public Tag getParent() {
        return this.parentTag;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }

    public void setPracticeKey(String practiceKey) {
        this.practiceKey = practiceKey;
    }
    
    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
    public void setClassAttr(String classAttr) {
        this.classAttr = classAttr;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            String selectedStr[] = new String[6];
            for(int i = 0; i < selectedStr.length; i++) {
                selectedStr[i] = "";
            }
            selectedStr[attendance] = "selected";
            
            String nameStr = memberKey + "," + practiceKey;
            
            String disabled = "";
            if(disable) {
                disabled = "disabled";
            }

            String str = 
                "<select class=\"" + classAttr  + "\" name=\"" + nameStr + "\" " + disabled + ">"
                + "<option value=\"0\"" + selectedStr[0] + ">未入力</option>"
                + "<option value=\"1\"" + selectedStr[1] + ">未定</option>"
                + "<option value=\"2\"" + selectedStr[2] + ">出席</option>"
                + "<option value=\"3\"" + selectedStr[3] + ">欠席</option>"
                + "<option value=\"4\"" + selectedStr[4] + ">遅刻</option>"                
                + "<option value=\"5\"" + selectedStr[5] + ">早退</option>"                
                + "</select>";
            out.print(str);
            
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void release() {
    }
}
