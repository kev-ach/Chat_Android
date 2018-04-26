package com.example.kevin.android_chat.models;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.android_chat.MessageActivity;
import com.example.kevin.android_chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;

    public MessageAdapter(List<Messages> mMessageList){
        this.mMessageList = mMessageList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView messageText,nameText,timeText;
        public CircleImageView profileImage;
        public ImageView messageImage;

        View mView;
        public MessageViewHolder(View itemView) {
            super(itemView);

            mView =itemView;
            messageText = (TextView) itemView.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) itemView.findViewById(R.id.single_message_image);
            messageImage = (ImageView) itemView.findViewById(R.id.message_image_layout);
            /*nameText = (TextView) itemView.findViewById(R.id.name_text_layout);
            timeText = (TextView) itemView.findViewById(R.id.time_text_layout);*/


        }

        public void setUserImage(String thumb_image, Context context){
            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.single_message_image);
            Picasso.get().load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout,parent,false);
        return new MessageViewHolder(v);*/

        if (viewType == VIEW_TYPE_OTHER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == VIEW_TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_me_layout, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;

        /*LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MessageAdapter.MessageViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new ItemMessageUserHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new ItemMessageFriendHolder(viewChatOther);
                break;
        }
        return viewHolder;*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (TextUtils.equals(mMessageList.get(position).getFrom(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((ItemMessageUserHolder) holder, position);
        } else {
            configureOtherChatViewHolder((ItemMessageFriendHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(ItemMessageUserHolder myChatViewHolder, int position) {
        Messages chat = mMessageList.get(position);
        String message_type = chat.getType();

        if(message_type.equals("text")){
            myChatViewHolder.txtContent.setText(chat.getMessage());
            myChatViewHolder.messageImage.setVisibility(View.INVISIBLE);
        }else{
            myChatViewHolder.txtContent.setVisibility(View.INVISIBLE);
            Picasso.get().load(chat.getMessage()).placeholder(R.drawable.default_avatar).into(myChatViewHolder.messageImage);
        }

    }

    private void configureOtherChatViewHolder(ItemMessageFriendHolder otherChatViewHolder, int position) {
        Messages chat = mMessageList.get(position);
        String message_type = chat.getType();

        if(message_type.equals("text")){
            otherChatViewHolder.txtContent.setText(chat.getMessage());
            otherChatViewHolder.messageImage.setVisibility(View.INVISIBLE);
        }else{
            otherChatViewHolder.txtContent.setVisibility(View.INVISIBLE);
            Picasso.get().load(chat.getMessage()).placeholder(R.drawable.default_avatar).into(otherChatViewHolder.messageImage);
        }
    }
   /* @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        String current_user_id = mAuth.getCurrentUser().getUid();
        Messages c = mMessageList.get(position);
        String from_user = c.getFrom();

        if(from_user.equals(current_user_id)){


            holder.messageText.setBackgroundColor(Color.WHITE);
            holder.messageText.setTextColor(Color.BLACK);
        }else{
            holder.setUserImage();
            holder.messageText.setBackgroundResource(R.drawable.message_shape_background);
            holder.messageText.setTextColor(Color.WHITE);
        }

        *//*holder.nameText.setText(from_user);
        holder.profileImage.setImageDrawable();*//*
        holder.messageText.setText(c.getMessage());
    }*/

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mMessageList.get(position).getFrom(),
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}

class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public CircleImageView avata;
    public ImageView messageImage;


    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.mine_message_text_layout);
        messageImage = (ImageView) itemView.findViewById(R.id.mine_message_image_layout);
        //avata = (CircleImageView) itemView.findViewById(R.id.imageView2);
    }
}

class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public CircleImageView avata;
    public ImageView messageImage;

    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.message_text_layout);
        messageImage = (ImageView) itemView.findViewById(R.id.message_image_layout);
        //avata = (CircleImageView) itemView.findViewById(R.id.imageView3);
    }
}