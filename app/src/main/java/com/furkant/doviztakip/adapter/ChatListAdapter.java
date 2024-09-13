package com.furkant.doviztakip.adapter;

import com.furkant.doviztakip.model.ChatModel;
import com.furkant.doviztakip.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

public class ChatListAdapter extends FirebaseListAdapter<ChatModel> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public ChatListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, ChatModel.class, layout, activity);
        this.mUsername = mUsername;
    }


    @Override
    protected void populateView(View view, ChatModel chat) {
        // Map a Chat object to an entry in our listview
        String author = chat.getAuthor();
        TextView authorText = (TextView) view.findViewById(R.id.author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);

        } else {
            authorText.setTextColor(Color.BLUE);
        }
        ((TextView) view.findViewById(R.id.message)).setText(chat.getMessage());
    }
}
