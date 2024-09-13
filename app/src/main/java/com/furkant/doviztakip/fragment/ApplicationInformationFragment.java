package com.furkant.doviztakip.fragment;
import com.furkant.doviztakip.R;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class ApplicationInformationFragment extends Fragment {


    public ApplicationInformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_application_information, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.header_Application_Information);
        setTextviewTypeface(view);

    }

    private void setTextviewTypeface(View view)
    {
        ViewGroup group = (ViewGroup)view.findViewById(R.id.help_content_relative);
        Typeface typefaceOpenSans_Semibold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/open_sans_semi_bold.otf");
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View v = group.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView)v).setTypeface(typefaceOpenSans_Semibold);
            }
        }
    }
}
