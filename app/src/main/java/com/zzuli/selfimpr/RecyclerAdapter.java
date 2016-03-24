package com.zzuli.selfimpr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiacheng.Wang on 2015/10/12 19:17;
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContentViewHolder>{

    private Context context;
    private List<ItemBean> list;
    private OnItemClickLitener itemClickLitener;

    private List<Integer> heightList;

    public RecyclerAdapter(Context context,List<ItemBean> list){
        this.context = context;
        this.list = list;

        //各组件的高度
        heightList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            int height = (int)(Math.random()*400 + 400);
            heightList.add(height);
        }
    }

    /**
     * 不指定添加的位置position，默认添加到列表的最后位置
     * @param itemBean
     */
    public void add(ItemBean itemBean){
        add(list.size() - 1, itemBean);
    }

    /**
     *
     * @param position  添加的位置
     * @param itemBean  添加的元素
     */
    public void add(int position,ItemBean itemBean){
        list.add(position, itemBean);

        int height = (int)(Math.random()*400 + 400);
        heightList.add(height);

        //普通Adapter使用notifyDataSetChanged()来刷新Adapter
//        notifyDataSetChanged();
        //在RecyclerView中使用notifyItemInserted()来刷新Adapter
        notifyItemInserted(position);
    }

    /**
     * 通过位置position删除元素
     * @param position
     */
    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 创建Item视图，并返回相应的ViewHolder
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

//        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview,null);
        //上面达不到我想要的效果
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycleview, viewGroup, false);

        ContentViewHolder contentViewHolder = new ContentViewHolder(view);
        return contentViewHolder;
    }

    /**
     * 绑定数据到正确的Item视图上。
     * @param contentViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ContentViewHolder contentViewHolder, int position) {
        ItemBean itemBean = list.get(position);
        contentViewHolder.contentView.setText(itemBean.getContent());

        ViewGroup.LayoutParams lp = contentViewHolder.itemView.getLayoutParams();
        lp.height =heightList.get(position);

        if(itemClickLitener != null){
            //设置监听 对象的值
            contentViewHolder.itemOnClick.init(contentViewHolder, position);
            contentViewHolder.itemOnLongClick.init(contentViewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ContentViewHolder extends RecyclerView.ViewHolder{

        TextView contentView;

        //15年10月13日因为这个变量的误写，使得监听事件无效
//        private OnItemClickLitener itemClickLitener;
        ItemOnClick itemOnClick;    //Item的点击事件
        ItemOnLongClick itemOnLongClick;    //Item长按事件

        public ContentViewHolder(View itemView) {
            super(itemView);

            contentView = (TextView) itemView.findViewById(R.id.id_content_tv);
            itemOnClick = new ItemOnClick();

            itemOnLongClick = new ItemOnLongClick();

            if(itemClickLitener != null){
                //设置监听 对象的值
                itemView.setOnClickListener(itemOnClick);
                itemView.setOnLongClickListener(itemOnLongClick);
            }
        }

        class ItemOnClick implements View.OnClickListener{
            private int position;
            private ContentViewHolder contentViewHolder;

            public void init(ContentViewHolder contentViewHolder,int position) {
                this.contentViewHolder = contentViewHolder;
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                Log.e("wjc","--ItemOnClick：position-->>" + position);
//                getPosition();
                itemClickLitener.onItemClick(contentViewHolder.itemView, position);
            }
        }

        /**
         * Item长按监听事件
         */
        class ItemOnLongClick implements View.OnLongClickListener{
            private int position;
            private ContentViewHolder contentViewHolder;

            public void init(ContentViewHolder contentViewHolder,int position) {
                this.contentViewHolder = contentViewHolder;
                this.position = position;
            }

            @Override
            public boolean onLongClick(View v) {
                Log.e("wjc","--ItemOnLongClick：position-->>" + position);
                itemClickLitener.onItemLongClick(contentViewHolder.itemView,position);
                return true;
            }
        }
    }


    /**
     * Item条目的点击事件
     * @param itemClickLitener
     */
    public void setOnItemClickLitener(OnItemClickLitener itemClickLitener){
        this.itemClickLitener = itemClickLitener;
    }

    public interface OnItemClickLitener {

        /**
         * Item点击事件
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);

        /**
         * Item长按事件
         * @param view
         * @param position
         */
        void onItemLongClick(View view, int position);
    }
}
