package nlu.hmuaf.android_bookapp.user.cart_user.FragmentFrontEnd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nlu.hmuaf.android_bookapp.R;

public class CreditCardFragment extends Fragment {
    private EditText creditCardHolder;
    private EditText creditCardNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_credit_card, container, false);
        creditCardHolder = view.findViewById(R.id.creditCardHolder);
        creditCardNumber = view.findViewById(R.id.creditCardNumber);
        return inflater.inflate(R.layout.fragment_credit_card, container, false);
    }
    public String getCreditCardNumber() {
        return creditCardNumber.getText().toString();
    }

    public String getCreditCardHolderName() {
        return creditCardHolder.getText().toString();
    }

}
