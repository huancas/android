package pe.progsistem.jesus.ubeprivado.Frgaments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import pe.progsistem.jesus.ubeprivado.R;

import static pe.progsistem.jesus.ubeprivado.conexion.Conexion.RUTAIMG;


public class ViewImageExtended extends AppCompatDialogFragment {

    public Bitmap PICTURE_SELECTED;
    public static ViewImageExtended newInstance(Bundle arguments) {
        Bundle args = arguments;
        ViewImageExtended fragment = new ViewImageExtended();
        fragment.setArguments(args);
        return fragment;
    }

    public ViewImageExtended() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_view_image_extended, container, false);

        ImageView ivImage = (ImageView)view.findViewById(R.id.ivImage);
        final String url = getArguments().getString("furl");
        if(url != null) {
            //  ivImage.setImageBitmap(PICTURE_SELECTED);
            String ruta =RUTAIMG+url;
            Picasso.get().load(ruta).into(ivImage);
        }
        return view;
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
