package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //launch from ListActivity
        Intent receivingEnd = getIntent();
        //Integer genInt = receivingEnd.getIntExtra("genInt",0);

        Integer userId = receivingEnd.getIntExtra("userId",-100);
        DBHandler db = new DBHandler(this);
        User usr = db.getSpecificUser(userId);

        //User usr = ListActivity.userList.get(position);

        //TextView txt = findViewById(R.id.idText);
        //txt.setText(String.format("MAD %s",genInt));//java string formatting, like python printf

        TextView nameTxt = findViewById(R.id.nameText);
        TextView descTxt = findViewById(R.id.descText);

        nameTxt.setText(String.format("%s", usr.name));
        descTxt.setText(String.format("%s", usr.description));

        Button btn = findViewById(R.id.fButton);
        //User usr = initUser();
        setF(usr,btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastN;
                if(usr.followed == false){ //if user is not following
                    usr.followed = true;//set to follow
                    toastN = "Followed";
                }
                else{//if user is following
                    usr.followed = false; //set to unfollow
                    toastN = "Unfollowed";
                }
                setF(usr,btn);
                db.updateUser(usr);

                //Toast message
                Toast tNotif = Toast.makeText(MainActivity.this,toastN,Toast.LENGTH_SHORT);
                tNotif.show();
            }
        });

        //event/onclick listener for message button
        Button messageButton = findViewById(R.id.mButton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAct = new Intent(MainActivity.this,MessageGroup.class);
                startActivity(newAct);
            }
        });

    }

    //method to initialise user
    public User initUser(){
        User usr = new User("username","desc",1,false);
        return usr;
    }

    //method to set the text based on the condition
    public void setF(User usr,Button btn){
        TextView txt = btn;
        if(usr.followed == false){
            txt.setText("Follow");
        }
        else{
            txt.setText("Unfollow");
        }
    }
}