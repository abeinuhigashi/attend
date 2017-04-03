package slim3.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.taglibs.standard.tag.rt.core.ForEachTag;

public class AttendanceForEach extends ForEachTag {

    private static final long serialVersionUID = 1L;

    static final String FUTURE_IDX = "future";

    @Override
    public int doStartTag() throws JspException {

        FutureIdx futureIxd = new FutureIdx();

        futureIxd.setIdx(0);// インデックスの初期化

        pageContext.getRequest().setAttribute(FUTURE_IDX, futureIxd);

        return super.doStartTag();
    }

    public PageContext getPageContext() {
        return super.pageContext;
    }

    // 未来日インデックス用内部クラス
    public static class FutureIdx {

        private int idx;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

    }

}
