package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // Initial values for quantity and default prices
    private int numberOfCoffees = 0;
    private final int PRICE_OF_COFFEE = 35;
    private final int WHIPPED_CREAM_PLUS = 2;
    private final int CHOCOLATE_PLUS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting of the background image
        getWindow().setBackgroundDrawableResource(R.drawable.coffee_bean_72dpi);
        displayQuantity(numberOfCoffees);
        displayPrice(calculatePrice());
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText customerName = (EditText) findViewById(R.id.name_input);
        String priceMessagge = createOrderSummary(customerName.getText().toString(), calculatePrice(), whippedCreamControl(), chocolateControl());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " " + getString(R.string.orderFor) + ": " + customerName.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, priceMessagge);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        numberOfCoffees++;
        displayQuantity(numberOfCoffees);
        displayPrice(calculatePrice());
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffees > 0) {
            numberOfCoffees--;
        }
        displayQuantity(numberOfCoffees);
        displayPrice(calculatePrice());
    }

    /**
     * This method is called when the checkboxes are clicked.
     */
    public void updatePrice(View view) {
        displayPrice(calculatePrice());
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method resumes the order
     */
    private String createOrderSummary(String customerName, int price, boolean extraCream, boolean chocolate) {
        String priceMessage = "";
        priceMessage += getString(R.string.name) + ": " + customerName;
        priceMessage += "\n" + getString(R.string.quantity) + ": " + numberOfCoffees;
        if (extraCream) {
            priceMessage += "\n" + getString(R.string.whipped_cream) + ": " + getString(R.string.yes);
        } else {
            priceMessage += "\n" + getString(R.string.whipped_cream) + ": " + getString(R.string.no);
        }
        if (chocolate) {
            priceMessage += "\n" + getString(R.string.chocolate) + ": " + getString(R.string.yes);
        } else {
            priceMessage += "\n" + getString(R.string.chocolate) + ": " + getString(R.string.no);
        }
        priceMessage += "\n" + getString(R.string.total) + ": " + NumberFormat.getCurrencyInstance().format(price);

        return priceMessage;
    }

    private int calculatePrice() {
        int extras = 0;
        if (whippedCreamControl()) {
            extras += WHIPPED_CREAM_PLUS;
        }
        if (chocolateControl()) {
            extras += CHOCOLATE_PLUS;
        }
        if (extras > 0) {
            return (PRICE_OF_COFFEE + extras) * numberOfCoffees;
        } else {
            return numberOfCoffees * PRICE_OF_COFFEE;
        }
    }

    private boolean whippedCreamControl() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        if (whippedCream.isChecked()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean chocolateControl() {
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        if (chocolate.isChecked()) {
            return true;
        } else {
            return false;
        }
    }

}
