package com.example.nviller.projetm2psav.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nviller.projetm2psav.R;

/**
 * Created by mouna on 16/01/2018.
 */

public class GestionEvent extends Fragment implements View.OnClickListener{

    public GestionEvent(){

    }

    @Override
    public void onClick(View view) {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View vf = inflater.inflate(R.layout.activity_gestion_event, container, false);
        return  vf;
    }

}
