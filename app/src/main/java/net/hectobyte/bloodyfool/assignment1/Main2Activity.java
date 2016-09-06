package net.hectobyte.bloodyfool.assignment1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;
import java.util.Set;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent activityThatCalled = getIntent();

        String pNumber = activityThatCalled.getExtras().getString("phonenumber");

        TextView number = (TextView) findViewById(R.id.number2);

        TextView country = (TextView) findViewById(R.id.country);

        number.setText(pNumber);

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        int countryCode;
        boolean isValid = false;
        PhoneNumberUtil.PhoneNumberType isMobile = null;

        Set<String> set = PhoneNumberUtil.getInstance().getSupportedRegions();

        String[] arr = set.toArray(new String[set.size()]);
        if(pNumber.length()>2)
            if(pNumber.charAt(0)=='0'&&pNumber.charAt(1)=='0'&&pNumber.charAt(2)!='0') {
                pNumber = "+" + pNumber.substring(2);

                try {
                    Phonenumber.PhoneNumber numberP = phoneUtil.parse(pNumber, "");
                    countryCode = numberP.getCountryCode();
                    country.setText("is a ");
                    isValid = phoneUtil.isValidNumber(numberP);
                    isMobile = phoneUtil.getNumberType(numberP);
                    if(!isValid)
                        country.append("non-");
                    country.append("valid ");
                    if(isMobile == PhoneNumberUtil.PhoneNumberType.MOBILE)
                        country.append("mobile ");
                    country.append("number from ");
                    Locale locale = new Locale(phoneUtil.getRegionCodeForNumber(numberP), phoneUtil.getRegionCodeForNumber(numberP));
                    country.append(locale.getDisplayCountry());

                } catch (NumberParseException e) {
                    country.setText("Number is invalid");
                }
            }
        else {
            country.setText("Number is invalid");
        }

        Button back = (Button) findViewById(R.id.back);
        Button call = (Button) findViewById(R.id.call);

        back.setOnClickListener(OnClickListener);
        call.setOnClickListener(OnClickListener);
    }

    private View.OnClickListener OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView number = (TextView) findViewById(R.id.number2);
            switch (v.getId()) {
                case R.id.back:
                    finish();
                    break;
                case R.id.call:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + number.getText().toString()));
                    startActivity(callIntent);
                    //intent to call
                    break;
            }
        }
    };

}
