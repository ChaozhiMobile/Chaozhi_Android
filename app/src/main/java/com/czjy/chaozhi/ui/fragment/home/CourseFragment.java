package com.czjy.chaozhi.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.czjy.chaozhi.R;
import com.czjy.chaozhi.model.bean.PurchProduct;
import com.czjy.chaozhi.util.glide.CommonGlideImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huyg on 2018/10/21.
 */
public class CourseFragment extends Fragment {


    @BindView(R.id.course_img)
    ImageView mImg;
    @BindView(R.id.course_title)
    TextView mTitle;
    @BindView(R.id.course_type)
    TextView mType;
    @BindView(R.id.course_time)
    TextView mTime;
    @BindView(R.id.course_question)
    TextView mQuestion;

    private Unbinder unbind;

    private PurchProduct mPurchProduct;


    public static CourseFragment newInstance(PurchProduct purchProduct) {
        CourseFragment courseFragment = new CourseFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", purchProduct);
        courseFragment.setArguments(bundle);
        return courseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        unbind = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mPurchProduct = bundle.getParcelable("data");
        initView();
    }

    private void initView() {
        if (mPurchProduct != null) {
            CommonGlideImageLoader.getInstance().displayNetImageWithCircle(getActivity(), mPurchProduct.getProduct_img(), mImg,getActivity().getResources().getDrawable(R.drawable.img_feature_product));
            mTitle.setText(mPurchProduct.getProduct_name());
            mType.setText(mPurchProduct.getProduct_sub_name());
            mTime.setText(String.format("%d节", mPurchProduct.getUser_time()));
            mQuestion.setText(String.format("%d道", mPurchProduct.getUser_question()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }
}
