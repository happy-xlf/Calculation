package com.example.calculation;

import android.os.Bundle;

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
import android.widget.Toast;

import com.example.calculation.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel= ViewModelProviders.of(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        //获取运算表达式
        myViewModel.generator();
        //设置初始得分为0
        myViewModel.setCurrentScore();
        //获取页面的控制binding
        final FragmentQuestionBinding binding;
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_question,container,false);
        //设置数据与生命周期
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        //builder用来保存用户输入的数据
        final StringBuilder builder=new StringBuilder();
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.button0:
                            builder.append(0);
                            break;
                        case R.id.button1:
                            builder.append(1);
                            break;
                        case R.id.button2:
                            builder.append(2);
                            break;
                        case R.id.button3:
                            builder.append(3);
                            break;
                        case R.id.button4:
                            builder.append(4);
                            break;
                        case R.id.button5:
                            builder.append(5);
                            break;
                        case R.id.button6:
                            builder.append(6);
                            break;
                        case R.id.button7:
                            builder.append(7);
                            break;
                        case R.id.button8:
                            builder.append(8);
                            break;
                        case R.id.button9:
                            builder.append(9);
                            break;
                        case R.id.clear:
                            //清楚代表用户的输入长度未0
                            builder.setLength(0);
                            break;
                    }
                    if(builder.length()==0){
                        binding.textView9.setText("请输入你的答案：");
                    }else{
                        //显示到UI页面上
                        binding.textView9.setText(builder.toString());
                    }
            }
        };
        //设置每一个按钮的点击事件
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.clear.setOnClickListener(listener);
        //提交按钮的设计
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(builder.length()!=0) {
                    //当答案相同时
                    if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getAnswer().getValue()) {
                        myViewModel.answerCurrent();
                        builder.setLength(0);
                        binding.textView9.setText("回答正确! 请继续!");
                    } else {
                        NavController controller = Navigation.findNavController(v);
                        if (myViewModel.win_flag) {
                            //当创造新纪录时，进入成功页面
                            controller.navigate(R.id.action_questionFragment_to_winFragment);
                            myViewModel.win_flag = false;
                            myViewModel.save();
                        } else {
                            //否则进入失败页面
                            controller.navigate(R.id.action_questionFragment_to_loseFragment);
                        }
                    }
                }else{
                    Toast.makeText(v.getContext(), "请输入答案", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_question, container, false);
    }
}
