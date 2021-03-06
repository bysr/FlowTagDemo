package hipad.flowtab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyawen on 2017/8/17 0017.
 */

public class SimpleFlowLayout extends ViewGroup {

    /*存储标签文字*/
    private List<String> mTexts;

    private List<String> ChooseTexts;


    /*存储测量的位置*/
    private List<LocalBean> lists;

    /*设备map对象存储居中导致的位置差*/
    private Map<Integer, Integer> map;

    /*默认图片和选中图片*/
    private int backgroundDefaultId, backgroundSelectId;
    /*设置外间距*/
    private float toLeftMargin, toRightMargin, toTopMargin, toBottomMargin, toMargin;
    /*设置内间距*/
    private float toLeftPadding, toRightPadding, toTopPadding, toBottomPadding, toPadding;
    //文字大小
    private float markTextSize;

    /**
     * 获取选中设备
     *
     * @return
     */
    public List<String> getChooseTexts() {
        return ChooseTexts;
    }

    public SimpleFlowLayout(Context context) {
        this(context, null);
    }

    public SimpleFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SimpleFlowLayout);
        backgroundDefaultId = t.getResourceId(R.styleable.SimpleFlowLayout_backgroundDefault, R.drawable.shape_corner);
        backgroundSelectId = t.getResourceId(R.styleable.SimpleFlowLayout_backgroundSelect, R.drawable.shape_corner_choose);

        toMargin = t.getDimension(R.styleable.SimpleFlowLayout_toMargin, 5);
        toLeftMargin = t.getDimension(R.styleable.SimpleFlowLayout_toLeftMargin, toMargin);
        toTopMargin = t.getDimension(R.styleable.SimpleFlowLayout_toTopMargin, toMargin);
        toRightMargin = t.getDimension(R.styleable.SimpleFlowLayout_toRightMargin, toMargin);
        toBottomMargin = t.getDimension(R.styleable.SimpleFlowLayout_toBottomMargin, toMargin);

        toPadding = t.getDimension(R.styleable.SimpleFlowLayout_toPadding, 10);
        toLeftPadding = t.getDimension(R.styleable.SimpleFlowLayout_toLeftPadding, toPadding);
        toTopPadding = t.getDimension(R.styleable.SimpleFlowLayout_toTopPadding, toPadding);
        toRightPadding = t.getDimension(R.styleable.SimpleFlowLayout_toRightPadding, toPadding);
        toBottomPadding = t.getDimension(R.styleable.SimpleFlowLayout_toBottomPadding, toPadding);


        markTextSize = t.getDimension(R.styleable.SimpleFlowLayout_markTextSize, 16);





        //初始化条件
        map = new HashMap<>();
        ChooseTexts = new ArrayList<>();
        mTexts = new ArrayList<>();
    }

    public interface OnClickChildListener {
        void OnClickChild(int position, boolean isChecked);
    }


    public OnClickChildListener listener;

    public void setOnClickChildListener(OnClickChildListener l) {

        this.listener = l;
    }


    /**
     * 设置文字集合
     *
     * @param mTexts
     */
    public void setmTexts(List<String> mTexts) {
        this.mTexts = mTexts;
        setTextData(mTexts);


    }


    //处理文字数据
    private void setTextData(List<String> mTexts) {
        int showCount = mTexts.size();

        if (lists == null || !lists.isEmpty())
            lists = new ArrayList<>();


        //直接替换掉之前的数据
        int i = 0;

        while (i < showCount) {
            //循环添加textview
            TextView view = getTextView(i);
            if (view == null)
                return;
            addView(view, generateDefaultLayoutParams());
            i++;

        }
        requestLayout();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMax = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMax = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        int widthNeed = 0;
        int heightNeed = 0;
        int x = 0;
        int y = 0;
        int currentLineHeight = 0;
        View child;
        int left = 0;
        int top = 0;

        //---------------
        lists.clear();//清空集合数据
        int length = 1;
        int widthAll = 0;

        //----------------

        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }

            child.measure(widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();//
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            if (x + childWidth > widthMax) {//换行处理，本行高度和x轴都清零，y轴下移（加上上次的行高）
                y += currentLineHeight;
                currentLineHeight = 0;
                x = 0;

                map.put(length, widthAll);

                length++;
                widthAll = 0;
            }

            //----新添加-----------
            widthAll += /*child.getMeasuredWidth()*/+childWidth;

            left = x + lp.leftMargin;
            top = y + lp.topMargin;
            //定位子view的位置
            lists.add(new LocalBean(length, left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight()));
            /*防止最后一项数据添加不进去*/
            map.put(length, widthAll);
            //-----------------------------------

            x += childWidth;
            currentLineHeight = Math.max(currentLineHeight, childHeight);

            widthNeed = Math.max(widthNeed, x);//加入了这个 子view后，留下最大宽度
            heightNeed = Math.max(heightNeed, y + currentLineHeight);//对比上次的，留下最大的高度
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthMax : widthNeed,
                heightMode == MeasureSpec.EXACTLY ? heightMax : heightNeed);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int widthMax = getWidth();
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            LocalBean bean = lists.get(i);

            child.layout(bean.getLeft() + error(widthMax, bean.getRows()), bean.getTop(), bean.getRight() + error(widthMax, bean.getRows()), bean.getBottom());

        }

    }

    private Integer error(int widthMax, Integer rows) {
        int error = map.get(rows);//得到当前行的宽度
        return (widthMax - error) / 2;


    }


    /**
     * 获取TextView
     *
     * @param position 位置
     */
    private TextView getTextView(final int position) {

        final TextView view = new TextView(getContext());
        view.setText(mTexts.get(position));
        view.setGravity(Gravity.CENTER);
        view.setBackgroundResource(R.drawable.shape_corner);
        view.setPadding((int) toLeftPadding, (int) toTopPadding, (int) toRightPadding, (int) toBottomPadding);
        view.setTextSize(markTextSize);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (ChooseTexts.contains(mTexts.get(position))) {
                        ChooseTexts.remove(mTexts.get(position));
                        view.setBackgroundResource(backgroundDefaultId);
                        listener.OnClickChild(position, false);
                    } else {
                        ChooseTexts.add(mTexts.get(position));
                        view.setBackgroundResource(backgroundSelectId);
                        listener.OnClickChild(position, true);
                    }


                }

            }
        });

        return view;

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {

        MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        lp.leftMargin = (int) toLeftMargin;
        lp.rightMargin = (int) toRightMargin;
        lp.topMargin = (int) toTopMargin;
        lp.bottomMargin = (int) toBottomMargin;


        return lp;
    }


}
