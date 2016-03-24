package com.zzuli.selfimpr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 如果你想使用RecyclerView，需要做以下操作：
 * RecyclerView.Adapter - 处理数据集合并负责绑定视图
 * ViewHolder - 持有所有的用于绑定数据或者需要操作的View
 * LayoutManager - 负责摆放视图等相关操作
 * ItemDecoration - 负责绘制Item附近的分割线
 * ItemAnimator - 为Item的一般操作添加动画效果，如，增删条目等
 */
public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.id_recycle_view);
        //设置布局管理器（布局管理器总共有三个）
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //设置增加或删除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView添加分割线
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        recyclerAdapter = new RecyclerAdapter(this,initData());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickLitener(new RecyclerAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this,"点击了："+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(RecyclerActivity.this,"长按了："+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加菜单menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recycler, menu);
        return true;
    }

    /**
     * 菜单点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_add:    //添加元素
                ItemBean itemBean = new ItemBean();
                itemBean.setContent("正在听-->薛之谦：绅士");
                recyclerAdapter.add(1, itemBean);
                Toast.makeText(getApplicationContext(),"添加一条数据",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_del:    //移除元素
                recyclerAdapter.remove(1);
                Toast.makeText(getApplicationContext(),"移除一条数据",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化数据
     * @return
     */
    private List<ItemBean> initData(){
        List<ItemBean> list = new ArrayList<>();

        for (int i=0;i<1000;i++){
            ItemBean itemBean = new ItemBean();
            itemBean.setContent("QQ:1281315018_"+i);
            list.add(itemBean);
        }
        return list;
    }
}
