package midtrans.mysamplesdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.models.BankType;
import com.midtrans.sdk.corekit.models.BillInfoModel;
import com.midtrans.sdk.corekit.models.CardTokenRequest;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ExpiryModel;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.corekit.utilities.Utils;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TransactionFinishedCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SDK initiation for UIflow
        SdkUIFlowBuilder.init(this, "VT-client-E4f1bsi1LpL1p5cF", "https://rakawm-snap.herokuapp.com/", this)
                .enableLog(true)
                .useBuiltInTokenStorage(false)
                .buildSDK();

        // Create new Transaction Request
        TransactionRequest transactionRequestNew = new
                TransactionRequest(System.currentTimeMillis()+"", 6000);

        //define customer detail (mandatory for coreflow)
        CustomerDetails mCustomerDetails = new CustomerDetails();
        mCustomerDetails.setPhone("624234234234");
        mCustomerDetails.setFirstName("sample full name");
        mCustomerDetails.setEmail("mail@mail.com");
        transactionRequestNew.setCustomerDetails(mCustomerDetails);


        // Define item details
        ItemDetails itemDetails = new ItemDetails("1", 1000, 1, "Trekking Shoes");
        ItemDetails itemDetails1 = new ItemDetails("2", 1000, 2, "Casual Shoes");
        ItemDetails itemDetails2 = new ItemDetails("3", 1000, 3, "Formal Shoes");

        // Add item details into item detail list.
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        itemDetailsArrayList.add(itemDetails1);
        itemDetailsArrayList.add(itemDetails2);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);
        // Set Bill info
        BillInfoModel billInfoModel = new BillInfoModel("demo_label", "demo_value");
        transactionRequestNew.setBillInfoModel(billInfoModel);

        // Create creditcard options for payment
        // noted : channel migs is needed if bank type is BCA, BRI or MyBank
        CreditCard creditCard = new CreditCard();

        String  cardClickType = getString(R.string.card_click_type_two_click);
        creditCard.setSaveCard(true);
        transactionRequestNew.setCreditCard(creditCard);



        transactionRequestNew.setCardPaymentInfo(cardClickType, true);

        MidtransSDK.getInstance().setTransactionRequest(transactionRequestNew);
        MidtransSDK.getInstance().startPaymentUiFlow(this);
    }

    @Override
    public void onTransactionFinished(TransactionResult transactionResult) {

    }
}
