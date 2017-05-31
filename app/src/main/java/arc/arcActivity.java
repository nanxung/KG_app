package arc;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.kg.cb.kg_app.R;

public class arcActivity extends ViewGroup implements OnClickListener {

    private int sign=-1;
    public static final int LEFT_TOP = 0;
    public static final int LEFT_BOTTOM = 1;
    public static final int RIGHT_TOP = 2;
    public static final int RIGHT_BOTTOM = 3;

    /*
    菜单的枚举类
    */
    public enum position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    public enum Staus {
        OPEN, CLOSE
    }

    //菜单的状态
    public Staus mstaus = Staus.CLOSE;
    public position mposition = position.RIGHT_BOTTOM;
    public int mradius;
    public View mCButton;//菜单主按纽

    public OnMenuItemClickListen menuItemClickListen;
    public void setOnMenuItemClickListener(
            OnMenuItemClickListen mMenuItemClickListener)
    {
        this.menuItemClickListen = mMenuItemClickListener;
    }
    public arcActivity(Context context) {
        this(context, null);
    }

    public arcActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mradius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                100, getResources().getDisplayMetrics());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.arcActivity, defStyleAttr, 0);
        int pos = a.getInt(R.styleable.arcActivity_position, RIGHT_BOTTOM);
        switch (pos) {
            case LEFT_TOP:
                mposition = position.LEFT_TOP;
                break;
            case LEFT_BOTTOM:
                mposition = position.LEFT_BOTTOM;
                break;
            case RIGHT_TOP:
                mposition = position.RIGHT_TOP;
                break;
            case RIGHT_BOTTOM:
                mposition = position.RIGHT_BOTTOM;
                break;
        }
        mradius = (int) a.getDimension(R.styleable.arcActivity_radius, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160,
                        getResources().getDisplayMetrics()));
        Log.e("key", mposition + ";" + mradius);
        a.recycle();

    }


    public arcActivity(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //点击子菜单下的回调接口
    public interface OnMenuItemClickListen {
        void onClick(View v, int positon);

    }

    public boolean isOpen(){
        return mstaus== Staus.OPEN;
    }
    @Override
    public void onClick(View view) {
//        mCButton=findViewById(R.id.id_Button);
//        if(mCButton==null){
//            mCButton=getChildAt(0);
//        }
        rotateCButton(view, 0f, 360f, 300);
        toggleMenu(300);
    }

    public void toggleMenu(int duration) {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View child = getChildAt(i + 1);
            child.setVisibility(View.VISIBLE);
            int cl = (int) (mradius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mradius * Math.cos(Math.PI / 2 / (count - 2) * i));
            int xflag = 1;
            int yflag = 1;
            if (mposition == position.LEFT_BOTTOM || mposition == position.LEFT_TOP) {
                xflag = -1;
            }
            if (mposition == position.LEFT_TOP || mposition == position.RIGHT_TOP) {
                yflag = -1;
            }
            //平移动画
            AnimationSet an = new AnimationSet(true);
            Animation at = null;
            if (mstaus == Staus.CLOSE) {
                at = new TranslateAnimation(xflag * cl, 0, yflag * ct, 0);
                child.setFocusable(true);
                child.setClickable(true);
            } else {
                at = new TranslateAnimation(0, xflag * cl, 0, yflag * ct);
                child.setFocusable(false);
                child.setClickable(false);
            }
            at.setFillAfter(true);
            at.setDuration(duration);
            at.setStartOffset((i * 100) / count);
            at.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mstaus == Staus.CLOSE) {
                        child.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            //旋转动画
            RotateAnimation ro = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ro.setDuration(duration);
            ro.setFillAfter(true);
            an.addAnimation(ro);
            an.addAnimation(at);
            child.startAnimation(an);
            final int pos=i+1;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(menuItemClickListen!=null)
                        menuItemClickListen.onClick(view,pos-1);
                        menuItemAnim(pos-1);
                        changeStaus();
                }
            });

        }

        //切换菜单状态
       changeStaus();
    }
//添加menuItem的点击动画
    public void menuItemAnim(int pos) {
        for(int i=0;i<getChildCount()-1;i++){
            View childView=getChildAt(i+1);
            if(i==pos){
//                switch (pos){
//                    case 0:Toast.makeText(getContext(),"Music",Toast.LENGTH_LONG).show();break;
//                    case 1:Toast.makeText(getContext(),"Place",Toast.LENGTH_LONG).show();break;
//                    case 2:Toast.makeText(getContext(),"Sleep",Toast.LENGTH_LONG).show();break;
//                    case 3:Toast.makeText(getContext(),"Sun",Toast.LENGTH_LONG).show();break;
//                    case 4:Toast.makeText(getContext(),"People",Toast.LENGTH_LONG).show();break;
//                }

                childView.startAnimation(scaleBigAnim(300));
                sign=pos;
            }else{
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }
public int getsign(){
    return sign;
}

    public Animation scaleSmallAnim(int duration)
    {

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);
        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;

    }

    /**
     * 为当前点击的Item设置变大和透明度降低的动画
     *
     * @param duration
     * @return
     */
    private Animation scaleBigAnim(int duration)
    {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0.0f);

        animationSet.addAnimation(scaleAnim);
        animationSet.addAnimation(alphaAnim);

        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;

    }

    //切换菜单状态
    private void changeStaus() {
        mstaus = (mstaus == Staus.CLOSE ? Staus.OPEN : Staus.CLOSE);
    }

    private void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation ra = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(duration);
        ra.setFillAfter(true);
        view.startAnimation(ra);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        //测量child
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            layoutCButton();
            int count = getChildCount();
            //mradius=300;
            for (int i = 0; i < count - 1; i++) {
                View v = getChildAt(i + 1);
                v.setVisibility(View.GONE);
                int cl = (int) (mradius * Math.sin(Math.PI / 2 / (count - 2) * i));
                int ct = (int) (mradius * Math.cos(Math.PI / 2 / (count - 2) * i));
                int height = v.getMeasuredHeight();
                int width = v.getMeasuredWidth();

                //在底部
                if (mposition == position.LEFT_BOTTOM || mposition == position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - height - ct;
                }
                //在右上或右下
                if (mposition == position.RIGHT_BOTTOM || mposition == position.RIGHT_TOP) {
                    cl = getMeasuredWidth() - width - cl;
                }
                v.layout(cl, ct, cl + width, ct + height);
            }
        }
    }

    private void layoutCButton() {
        mCButton = getChildAt(0);
        Log.e("key", mCButton + "");
        mCButton.setOnClickListener(this);
        int l = 0, t = 0, width = mCButton.getMeasuredWidth(), height = mCButton.getMeasuredHeight();
        switch (mposition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                ;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                ;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                ;
                break;
            case RIGHT_BOTTOM:
                t = getMeasuredHeight() - height;
                l = getMeasuredWidth() - width;
                ;
                break;
        }
        mCButton.layout(l, t, l + width, t + width);
    }
}
