package com.example.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Scroller;

import java.util.List;

/**
 * 自定义ListView，支持左滑显示详细和删除操作
 */
public class UserListView extends ListView {

    private static final String TAG = "UserListView";

    private Context mContext;
    private int touchSlop;
    private boolean isPerformClick;

    private int lastX;
    private int lastY;

    private View mCurrentView;
    private int currentPosition = INVALID_POSITION;

    private boolean isStartScroll;
    private Scroller mScroller;
    private boolean isCurrentItemMoving;
    private boolean isDragging;

    private int mMaxLength;
    private int menuStatus = 0;
    private static final int MENU_CLOSE = 0;
    private static final int MENU_WILL_CLOSE = 1;
    private static final int MENU_OPEN = 2;
    private static final int MENU_WILL_OPEN = 3;

    private OnItemActionListener mListener;
    private List<User> mDatas;

    public UserListView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public UserListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public UserListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {
        mScroller = new Scroller(mContext, new LinearInterpolator());
        touchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset() && mCurrentView != null) {
            mCurrentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (menuStatus == MENU_WILL_CLOSE) {
                menuStatus = MENU_CLOSE;
            }
            if (menuStatus == MENU_WILL_OPEN) {
                menuStatus = MENU_OPEN;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (menuStatus == MENU_CLOSE) {
                    currentPosition = pointToPosition(x, y);
                    if (currentPosition == INVALID_POSITION) {
                        return super.onTouchEvent(ev);
                    }
                    mCurrentView = getChildAt(currentPosition - getFirstVisiblePosition());
                    if (mCurrentView == null) {
                        return super.onTouchEvent(ev);
                    }
                    Button btnDetail = mCurrentView.findViewById(R.id.btn_detail);
                    Button btnDelete = mCurrentView.findViewById(R.id.btn_delete);
                    if (btnDetail == null || btnDelete == null) {
                        return super.onTouchEvent(ev);
                    }

                    mMaxLength = btnDetail.getWidth() + btnDelete.getWidth();
                    btnDetail.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCurrentView.scrollTo(0, 0);
                            menuStatus = MENU_CLOSE;
                            if (mListener != null) {
                                mListener.OnItemDetail(currentPosition);
                            }
                        }
                    });
                    btnDelete.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCurrentView.scrollTo(0, 0);
                            menuStatus = MENU_CLOSE;
                            if (mListener != null) {
                                mListener.OnItemDelete(currentPosition);
                            }
                        }
                    });
                } else if (menuStatus == MENU_OPEN) {
                    if (mCurrentView != null) {
                        mScroller.startScroll(mCurrentView.getScrollX(), 0, -mMaxLength, 0, 200);
                        isStartScroll = true;
                        menuStatus = MENU_WILL_CLOSE;
                        invalidate();
                    }
                    return false;
                } else {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = lastX - x;
                int dy = lastY - y;
                if (isDragging) {
                    lastX = x;
                    lastY = y;
                    return super.onTouchEvent(ev);
                }
                if (mCurrentView != null) {
                    int scrollX = mCurrentView.getScrollX();
                    if (Math.abs(dx) > Math.abs(dy)) {
                        isCurrentItemMoving = true;
                        if (scrollX + dx <= 0) {
                            mCurrentView.scrollTo(0, 0);
                            return false;
                        }
                        if (scrollX + dx >= mMaxLength) {
                            mCurrentView.scrollTo(mMaxLength, 0);
                            return false;
                        }
                        mCurrentView.scrollBy(dx, 0);
                    } else {
                        isDragging = true;
                    }
                    if (isCurrentItemMoving) {
                        lastX = x;
                        lastY = y;
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isCurrentItemMoving && !isDragging && mListener != null && Math.abs(lastX - x) < touchSlop) {
                    if (currentPosition != INVALID_POSITION && mCurrentView != null) {
                        mListener.OnItemClick(currentPosition);
                    }
                }
                if (mCurrentView != null) {
                    int upScrollX = mCurrentView.getScrollX();
                    int deltaX = 0;
                    if (upScrollX < mMaxLength / 2) {
                        deltaX = -upScrollX;
                        menuStatus = MENU_WILL_CLOSE;
                    } else {
                        deltaX = mMaxLength - upScrollX;
                        menuStatus = MENU_WILL_OPEN;
                    }
                    mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                }
                isDragging = false;
                isCurrentItemMoving = false;
                isStartScroll = true;
                invalidate();
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(ev);
    }

    /**
     * 设置监听器
     */
    public void setActionListener(OnItemActionListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置数据
     */
    public void setData(List<User> data) {
        mDatas = data;
    }
}
