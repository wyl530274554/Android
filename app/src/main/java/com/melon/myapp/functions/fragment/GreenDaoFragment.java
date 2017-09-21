package com.melon.myapp.functions.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melon.myapp.BaseFragment;
import com.melon.myapp.R;
import com.melon.myapp.bean.DaoMaster;
import com.melon.myapp.bean.DaoSession;
import com.melon.myapp.bean.Message;
import com.melon.myapp.bean.MessageDao;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ToastUtil;

import org.greenrobot.greendao.database.Database;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by melon on 2017/9/18.
 * Email 530274554@qq.com
 */

public class GreenDaoFragment extends BaseFragment {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_green_dao, container, false);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                insert();
                break;
            case R.id.button2:
                query();
                break;
            case R.id.button3:
                alter();
                query();
                break;
            case R.id.button4:
                delete();
                query();
                break;
        }
    }

    private void delete() {
        Database db = new DaoMaster.DevOpenHelper(getContext(), "message").getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        MessageDao messageDao = daoSession.getMessageDao();

        if (list.size() > 0)
            messageDao.delete(list.get(list.size() - 1));
    }

    private void alter() {
        Database db = new DaoMaster.DevOpenHelper(getContext(), "message").getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        MessageDao messageDao = daoSession.getMessageDao();

        if (list.size() > 0) {
            Message message = list.get(list.size() - 1);
            message.setText("改变了内容");
            messageDao.update(message);
        }
    }

    List<Message> list;

    private void query() {
        Database db = new DaoMaster.DevOpenHelper(getContext(), "message").getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        MessageDao messageDao = daoSession.getMessageDao();

        list = messageDao.queryBuilder().list();
        StringBuilder sb = new StringBuilder();
        for (Message message : list) {
            sb.append("id: " + message.getId() + "   content: " + message.text + "   date: " + CommonUtil.getMyDateFormat(message.getDate().getTime()+"") + "\n");
        }
        textView.setText(sb.toString());
    }

    private void insert() {
        Database db = new DaoMaster.DevOpenHelper(getContext(), "message").getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        MessageDao messageDao = daoSession.getMessageDao();

        long id = messageDao.insert(getMessage());
        if (id >= 0) ToastUtil.toast(getContext(), "插入成功: id为 " + id);
    }


    int i;

    private Message getMessage() {
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setText("消息内容" + i);
        i++;
        return msg;
    }
}
