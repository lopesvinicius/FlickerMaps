package com.example.aluno.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SearchActivity extends Activity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    

    public void onSendMessage(View view) {
        EditText search = (EditText) findViewById(R.id.searcher);

        if (search.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Atenção");
            builder.setMessage("Preencha o campo de busca!");

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            RadioGroup radioLimit = (RadioGroup) findViewById(R.id.radioLimit);

            Integer selectedId = radioLimit.getCheckedRadioButtonId();
            RadioButton radioLimitValue = (RadioButton) findViewById(selectedId);

            String messageText = search.getText().toString() + "|" + radioLimitValue.getText().toString();

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, messageText);
            startActivity(intent);
        }
    }
}
