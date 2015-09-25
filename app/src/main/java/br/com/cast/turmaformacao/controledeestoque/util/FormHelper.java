package br.com.cast.turmaformacao.controledeestoque.util;

import android.widget.EditText;

public final class FormHelper {

    private FormHelper() {
        super();
    }

    public static boolean checkRequireFields(String requiredMessage, EditText... editTexts) {

        boolean hasRequired = false;

        for (EditText editText : editTexts) {
            String textValue = editText.getText().toString();
            if (textValue.trim().isEmpty()) {
                editText.setError(requiredMessage);
                hasRequired = true;
            }
        }

        return hasRequired;
    }

    public static boolean checkMinimumQuantity(String alertMessage, EditText editTextMinimumQuantity, EditText editTextQuantity){
        boolean hasAlert = false;

        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        int minimumQuantity = Integer.parseInt(editTextMinimumQuantity.getText().toString());

        if(quantity < minimumQuantity){
            editTextQuantity.setError(alertMessage);
            editTextMinimumQuantity.setError(alertMessage);
            hasAlert = true;
        }

        return hasAlert;
    }

}
