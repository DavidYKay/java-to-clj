String value = input.getText().toString();

Editor editor = shared.edit();
editor.putLong("PHONE", Long.valueOf(value));
editor.commit();
