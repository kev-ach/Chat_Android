package com.example.kevin.android_chat.models;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevin.android_chat.MessageActivity;
import com.example.kevin.android_chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String mChatUser;
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;


    public MessageAdapter(List<Messages> mMessageList){
        this.mMessageList = mMessageList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_OTHER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == VIEW_TYPE_ME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_me_layout, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;
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

    private void configureMyChatViewHolder(final ItemMessageUserHolder myChatViewHolder, int position) {
        Messages chat = mMessageList.get(position);
        mAuth = FirebaseAuth.getInstance();
        String mCurrentUser = mAuth.getCurrentUser().getUid();
        String message_type = chat.getType();
        String from_user = chat.getFrom();

        if(from_user == null){
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(myChatViewHolder.avatar);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(myChatViewHolder.avatar);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




        if(message_type.equals("text")){
            myChatViewHolder.txtContent.setText(chat.getMessage());
            myChatViewHolder.messageImage.setVisibility(View.INVISIBLE);
        }else{
            myChatViewHolder.txtContent.setVisibility(View.INVISIBLE);
            Picasso.get().load(chat.getMessage()).placeholder(R.drawable.default_avatar).into(myChatViewHolder.messageImage);
        }

    }

    private void configureOtherChatViewHolder(final ItemMessageFriendHolder otherChatViewHolder, int position) {
        Messages chat = mMessageList.get(position);
        mAuth = FirebaseAuth.getInstance();
        String mCurrentUser = mAuth.getCurrentUser().getUid();
        String message_type = chat.getType();
        String from_user = chat.getFrom();

        if(from_user == null){
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(otherChatViewHolder.avatar);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else {
            mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

            mUserDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("thumb_image").getValue().toString();

                    Picasso.get().load(image).placeholder(R.drawable.default_avatar).into(otherChatViewHolder.avatar);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        if(message_type.equals("text")){
            otherChatViewHolder.txtContent.setText(chat.getMessage());
            otherChatViewHolder.messageImage.setVisibility(View.INVISIBLE);
        }else{
            otherChatViewHolder.txtContent.setVisibility(View.INVISIBLE);
            Picasso.get().load(chat.getMessage()).placeholder(R.drawable.default_avatar).into(otherChatViewHolder.messageImage);
        }


    }

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
        //return mMessageList.size();
        if (mMessageList != null) {
            return mMessageList.size();
        }
        return 0;
    }

}

class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public ImageView messageImage;
    public CircleImageView avatar;


    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.mine_message_text_layout);
        messageImage = (ImageView) itemView.findViewById(R.id.mine_message_image_layout);
        avatar = (CircleImageView) itemView.findViewById(R.id.me_single_message_image);
    }
}

class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
    public TextView txtContent;
    public CircleImageView avatar;
    public ImageView messageImage;

    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.message_text_layout);
        messageImage = (ImageView) itemView.findViewById(R.id.message_image_layout);
        avatar = (CircleImageView) itemView.findViewById(R.id.single_message_image);
    }
}