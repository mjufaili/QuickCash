package com.example.g15_gp.activities;
/**
 * This class allows an employer to easily pay an employee his salary according to amount of work or
 * agreed salary between the employer and employee.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.g15_gp.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class PayPal extends AppCompatActivity {

    private static final String TAG = PayPal.class.getName();
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration payPalConfig;
    private Button btnPayNow;
    private EditText edtAmount;
    private TextView paymentTV;
    public static final String PAYPAL_CLIENT_ID= "AdzsTnCLHJ_xouV3yTI_LGEh5FKTqISx57uMowHuQbDOOSQyEE5wWuB4P48w5znQ6_HM_Z1xPxxX_XzD";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);
        init();
        configPayPal();
        initActivityLauncher();
        setListeners();
    }

    private void init(){
        edtAmount = findViewById(R.id.edtAmount);
        btnPayNow = findViewById(R.id.btnPayNow);
        paymentTV = findViewById(R.id.idTVStatus);
    }

    private void configPayPal(){
        payPalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PAYPAL_CLIENT_ID);
    }

    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode()==RESULT_OK){
                final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        // get the payment details
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        Log.i(TAG, paymentDetails);
                        // Extract json response and distort it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");
                        paymentTV.setText(String.format("Payment %s%n with payment id is %s", state, payID));
                    }
                    catch (JSONException e) {
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
                else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                    Log.d (TAG,"Launcher Result Invalid");
                }
                else if (result.getResultCode () == Activity .RESULT_CANCELED) {
                    Log.d(TAG, "Launcher Result Cancelled");
                }
            }
        });
    }

    private void setListeners(){
        btnPayNow.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        // Getting amount from editText
        final String amount = edtAmount.getText().toString();
        // Creating Paypal payment
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal((amount)),
                "CAD","Payment Done",PayPalPayment.PAYMENT_INTENT_SALE);
        // Creating Paypal Payment activity intent
        final Intent intent = new Intent(this, PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfig);
        // Adding paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }
}