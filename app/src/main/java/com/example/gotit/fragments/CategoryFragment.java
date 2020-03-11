package com.example.gotit.fragments;

import android.annotation.TargetApi;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gotit.MainActivity;
import com.example.gotit.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

//----------------------------------------------------------------------------------
//  Fragment that allows the Customer to choose what type of products they
//  want to see or see the stores
//----------------------------------------------------------------------------------
@TargetApi(11)
public class CategoryFragment extends Fragment {

    private final String CREATE_POST = "CREATE_POST";
    private final String POST = "POST";
    private final String ERROR = "ERROR";
    private TextView title;
    private ImageView imagePost;
    private Button btnFood;
    private Button btnElectronics;
    private Button btnHousehold;
    private Button btnPersonalCare;
    private Button browse;
    private Button back;
    protected final String FEED = "FEED";

    //changed these below from public to private
    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private String photoFileName = "photo.jpg";
    private File photoFile;


    //----------------------------------------------------------------------------------
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML menu_top_nav inflation.
    //----------------------------------------------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        title = view.findViewById(R.id.pageTitle);
        title.setText("Welcome to Got It!");

        btnFood = view.findViewById(R.id.food_btn);
        btnElectronics = view.findViewById(R.id.electronics_btn);
        btnHousehold = view.findViewById(R.id.household_btn);
        btnPersonalCare = view.findViewById(R.id.personalCare_btn);
        browse = view.findViewById(R.id.browse_btn);

        //----------------------------------------------------------------------------------
        // Listen for click on the Browse Button
        //----------------------------------------------------------------------------------
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, new ListStoresFragment());
                fragmentTransaction.addToBackStack(FEED);
                fragmentTransaction.commit();
            }
        });

        //----------------------------------------------------------------------------------
        // Listen for click on the Electrics Button
        //----------------------------------------------------------------------------------
        btnElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, new CategoryFragment());
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    //----------------------------------------------------------------------------------
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    //----------------------------------------------------------------------------------
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText("Welcome to Got It!");


    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = null;
        fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = null;
        mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), CREATE_POST);


        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(ERROR, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below

                // Load the taken image into a preview
                imagePost.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
