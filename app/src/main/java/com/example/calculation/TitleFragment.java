package com.example.calculation;

import android.app.AppComponentFactory;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculation.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment{


    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTitleBinding binding;
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_title,container,false);
        MyViewModel myViewModel = ViewModelProviders.of(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller=Navigation.findNavController(v);
                controller.navigate(R.id.action_titleFragment_to_questionFragment);
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_title, container, false);
    }


}
