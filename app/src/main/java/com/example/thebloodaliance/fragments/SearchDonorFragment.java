package com.example.thebloodaliance.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thebloodaliance.R;
import com.example.thebloodaliance.adapters.SearchDonorAdapter;

import com.example.thebloodaliance.viewmodels.DonorData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class SearchDonorFragment extends Fragment {
    private View view;

    FirebaseAuth mAuth;
    FirebaseUser fuser;
    FirebaseDatabase fdb;
    DatabaseReference db_ref, user_ref;

    Spinner bloodgroup, division;
    Button btnsearch;
    List<DonorData> donorItem;
    private RecyclerView recyclerView;

    private SearchDonorAdapter sdadapter;


    public SearchDonorFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.search_donor_fragment, container, false);



        mAuth = FirebaseAuth.getInstance();
        fuser = mAuth.getCurrentUser();
        fdb = FirebaseDatabase.getInstance();
        db_ref = fdb.getReference("donors");

        bloodgroup = view.findViewById(R.id.btngetBloodGroup);
        division = view.findViewById(R.id.btngetDivison);
        btnsearch = view.findViewById(R.id.btnSearch);

        getActivity().setTitle("Find Blood Donor");

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donorItem = new ArrayList<>();
                donorItem.clear();
                sdadapter = new SearchDonorAdapter(donorItem);
                recyclerView = (RecyclerView) view.findViewById(R.id.showDonorList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                RecyclerView.LayoutManager searchdonor = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(searchdonor);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                recyclerView.setAdapter(sdadapter);
                Query qpath  = db_ref.child(division.getSelectedItem().toString())
                        .child(bloodgroup.getSelectedItem().toString());
                qpath.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            for(DataSnapshot singleitem : dataSnapshot.getChildren())
                            {
                                DonorData donorData = singleitem.getValue(DonorData.class);
                                donorItem.add(donorData);
                                sdadapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {

                            Toast.makeText(getActivity(), "No Records Available Now!",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());

                    }
                });

            }
        });
        return view;
    }

}
