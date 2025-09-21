
package com.example.gdgdemo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gdgdemo.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;
import android.graphics.Color;
import android.widget.ImageButton;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Listen for real-time updates (optional, still keeps updates live)
        listenForPromos();


    }

    private void listenForPromos() {
        db.collection("collectiongdg").document("app-banner")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null || snapshot == null || !snapshot.exists()) return;

                        String message = snapshot.getString("message");
                        String imageUrl = snapshot.getString("image-url");
                        String bannerColor = snapshot.getString("bannerColor");

                        if (message != null) {
                            binding.textHome.setText(message);
                        }

                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Picasso.get().load(imageUrl).into(binding.imageHome);
                        }

                        if (bannerColor != null && !bannerColor.isEmpty()) {
                            binding.textHome.setBackgroundColor(Color.parseColor(bannerColor));
                        }
                    }
                });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



