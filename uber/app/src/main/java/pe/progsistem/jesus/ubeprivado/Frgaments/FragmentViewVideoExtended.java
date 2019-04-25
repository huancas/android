package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pe.progsistem.jesus.ubeprivado.R;


public class FragmentViewVideoExtended extends AppCompatDialogFragment {




    public FragmentViewVideoExtended() {
        // Required empty public constructor
    }


    public static FragmentViewVideoExtended newInstance(Bundle arguments) {
        FragmentViewVideoExtended fragment = new FragmentViewVideoExtended();
        Bundle args = arguments;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Esta linea de código hace que tu DialogFragment sea Full screen
       setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar);
        Bundle arguments = getArguments();
        // PICTURE_SELECTED = arguments.getParcelable("PICTURE_SELECTED");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_view_video_extended, container, false);

      //  ImageView ivImage = (ImageView)view.findViewById(R.id.ivImage);
        final String url = getArguments().getString("furlVideo");


        //webview
     WebView   myWebView = (WebView) view.findViewById(R.id.wvVideoBig);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        if(url != null ) {
            //  ivImage.setImageBitmap(PICTURE_SELECTED);
            myWebView.loadUrl(url);
        }


        return  view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                // Aqui puedes capturar el OnBackPressed
                dismiss();
            }
        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }




}
