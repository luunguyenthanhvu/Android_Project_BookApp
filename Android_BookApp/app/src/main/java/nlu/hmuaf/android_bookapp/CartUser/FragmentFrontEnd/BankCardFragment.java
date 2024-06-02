package nlu.hmuaf.android_bookapp.CartUser.FragmentFrontEnd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import nlu.hmuaf.android_bookapp.R;

public class BankCardFragment extends Fragment {
    private EditText bankCardNumber;
    private EditText bankCardHolderName;
    private EditText bankCardName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_card, container, false);
        bankCardNumber = view.findViewById(R.id.bankCardNumber);
        bankCardHolderName = view.findViewById(R.id.bankCardHolder);
        bankCardName = view.findViewById(R.id.bankCardName);
        return view;
    }

    public String getBankCardNumber() {
        return bankCardNumber.getText().toString();
    }

    public String getBankCardHolderName() {
        return bankCardHolderName.getText().toString();
    }

    public String getBankCardName() {
        return bankCardName.getText().toString();

    }
}

