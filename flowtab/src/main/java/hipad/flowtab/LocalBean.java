package hipad.flowtab;

/**
 * Created by wangyawen on 2017/8/17 0017.
 */

public class LocalBean {

    /**
     * 测量出来的上下左右
     */
    private Integer left;
    private Integer top;
    private Integer right;
    private Integer bottom;

    /*当前行数*/
    private Integer rows;


    public LocalBean(Integer rows, Integer left, Integer top, Integer right, Integer bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.rows = rows;
    }

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getBottom() {
        return bottom;
    }

    public void setBottom(Integer bottom) {
        this.bottom = bottom;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "LocalBean{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                ", rows=" + rows +
                '}';
    }
}
