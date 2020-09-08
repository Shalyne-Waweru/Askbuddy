package com.kevine.askbuddy.post;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.hendraanggrian.appcompat.socialview.Hashtag;
import com.hendraanggrian.appcompat.widget.HashtagArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.kevine.askbuddy.BaseActivity;
import com.kevine.askbuddy.Constants;
import com.kevine.askbuddy.Log;
import com.kevine.askbuddy.MainActivity;
import com.kevine.askbuddy.R;
import com.kevine.askbuddy.network.ApiClientString;
import com.kevine.askbuddy.network.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends BaseActivity {

    private Uri imageUri;
    private String imageUrl;

    private ImageView close;
    private ImageView imageAdded;
    private TextView post;
    SocialAutoCompleteTextView topic;
    SocialAutoCompleteTextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        close = findViewById(R.id.close);
        imageAdded = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        topic = findViewById(R.id.topic);
        description = findViewById(R.id.description);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this , MainActivity.class));
                finish();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload();
                createPost(description.getText().toString(),"",session.getUserId(),topic.getText().toString());
            }
        });

        CropImage.activity().start(PostActivity.this);

    }

    private void createPost(String description,String imageUrl,String u_id,String topic) {
        showProgress();
        ApiInterface apiInterface = ApiClientString.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.createPost(description,imageUrl,u_id,topic);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dismissProgress();

                Log.debug("postResp:", response.body());

                try {
                    assert response.body() !=null;
                    JSONObject obj = new JSONObject(response.body());

                    String respcode = obj.optString(Constants.KEY_RESPCODE);
                    String respdesc = obj.optString(Constants.KEY_RESPDESC);

                    if (respcode.equals("01")){
                        //JSONObject Ob = obj.optJSONObject("details");
                        showSnackBar(respdesc);

                    }else {
                        dismissProgress();
                        showSnackBar(respdesc);
                    }


                }catch (JSONException e){
                    dismissProgress();
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            //if an error occurs in network transaction
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dismissProgress();
                Log.debug("postResp: ",t.getMessage());
            }
        });
    }

    private void upload() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("Posts").child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            StorageTask uploadtask = filePath.putFile(imageUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                    String postId = ref.push().getKey();

                    HashMap<String , Object> map = new HashMap<>();
                    map.put("postid" , postId);
                    map.put("imageurl" , imageUrl);
                    map.put("topic" , topic.getText().toString());
                    map.put("description" , description.getText().toString());
                    map.put("publisher" , FirebaseAuth.getInstance().getCurrentUser().getUid());

                    ref.child(postId).setValue(map);

                    DatabaseReference mHashTagRef = FirebaseDatabase.getInstance().getReference().child("HashTags");
                    List<String> hashTags1 = description.getHashtags();
                    List<String> hashTags2 = topic.getHashtags();
                    if (!hashTags1.isEmpty() && hashTags2.isEmpty()){
                        for (String tag : hashTags2){
                            map.clear();

                            map.put("tag" , tag.toLowerCase());
                            map.put("postid" , postId);

                            mHashTagRef.child(tag.toLowerCase()).child(postId).setValue(map);
                        }
                    }

                    pd.dismiss();
                    startActivity(new Intent(PostActivity.this , MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri) {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            imageAdded.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this , MainActivity.class));
            finish();
        }

    }



    @Override
    protected void onStart() {
        super.onStart();

        final ArrayAdapter<Hashtag> hashtagAdapter = new HashtagArrayAdapter<>(getApplicationContext());

        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hashtagAdapter.add(new Hashtag(snapshot.getKey() , (int) snapshot.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        description.setHashtagAdapter(hashtagAdapter);
        topic.setHashtagAdapter(hashtagAdapter);
    }
}
