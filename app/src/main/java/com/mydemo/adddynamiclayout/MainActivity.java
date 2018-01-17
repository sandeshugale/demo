package com.mydemo.adddynamiclayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    EditText editText;
    boolean isEdit = true;
    boolean isTwise = false;
    private ArrayList<AddTags> mArrayListFinalSelect;
    private ArrayList<String> mArrayListTags;
   /* private FlexboxLayout mLayoutAddTags;*/
    private LinearLayout mLayoutAddTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edt_tags_qrious);
        mArrayListTags = new ArrayList<>();
        mArrayListFinalSelect = new ArrayList<>();
       /* mLayoutAddTags =(FlexboxLayout)findViewById(R.id.layout_add_tags_qrious);*/
        mLayoutAddTags =(LinearLayout) findViewById(R.id.layout_add_tags_qrious);
        double scaletype = getResources().getDisplayMetrics().density;
        if (scaletype >= 3.0) {
            isTwise = true;
        }
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.setClickable(true);
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (count >= 1 && !isEdit) {
                    if (!Character.isSpaceChar(s.charAt(0))) {
                        if (s.charAt(start) == ' ')
                            setTag(); // generate chips
                    } else {
                        editText.getText().clear();
                        editText.setSelection(0);
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEdit) {
                    setTag();
                }


            }
        });
    }

    private void setTag() {

        editText.setOnKeyListener(new View.OnKeyListener() {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            if (editText.getText().toString().trim().equalsIgnoreCase("")) {
                                Toast.makeText(MainActivity.this, "Please enter tags.", Toast.LENGTH_SHORT).show();
                                editText.setFocusable(false);
                                editText.clearFocus();
                            }else if (!editText.getText().toString().matches(emailPattern))
                            {
                                Toast.makeText(MainActivity.this, "Please enter valid Email.", Toast.LENGTH_SHORT).show();
                                editText.setFocusable(false);
                                editText.clearFocus();
                            } else {
                                SpannableStringBuilder ssb = new SpannableStringBuilder(editText.getText());
                                String chips[] = editText.getText().toString().trim().split(" ");
                                int x = 0;
                                for (String c : chips) {
                                   /* LayoutInflater mLayoutInflater =getLayoutInflater();
                                 //   LayoutInflater mLayoutInflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                   // View view=lf.inflate(R.layout.tag_edittex, null);
                                    View additionView1 = mLayoutInflater.inflate(R.layout.editext, null, false);
                                    TextView textView=additionView1.findViewById(R.id.edtTxt1);
                                    textView.setText(c); // set text
                                    int spec = View.MeasureSpec.makeMeasureSpec(0,
                                            View.MeasureSpec.UNSPECIFIED);*/


                                    LayoutInflater lf = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                                    TextView textView = (TextView) lf.inflate(
                                            R.layout.tag_edittex, null);
                                    textView.setText(c); // set text
                                    int spec = View.MeasureSpec.makeMeasureSpec(0,
                                            View.MeasureSpec.UNSPECIFIED);
                                    textView.measure(spec, spec);
                                    textView.layout(0, 0, textView.getMeasuredWidth(),
                                            textView.getMeasuredHeight());
                                    Bitmap b = Bitmap.createBitmap(textView.getWidth(),
                                            textView.getHeight(), Bitmap.Config.ARGB_8888);
                                    Canvas canvas = new Canvas(b);
                                    canvas.translate(-textView.getScrollX(), -textView.getScrollY());
                                    textView.draw(canvas);
                                    textView.setDrawingCacheEnabled(true);
                                    Bitmap cacheBmp = textView.getDrawingCache();
                                    Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
                                    textView.destroyDrawingCache(); // destory drawable
                                    BitmapDrawable bmpDrawable = new BitmapDrawable(viewBmp);
                                    int width = bmpDrawable.getIntrinsicWidth();
                                    int height = bmpDrawable.getIntrinsicHeight();
                                    if (isTwise) {
                                        width = width * 2;
                                        height = height * 2;
                                    }
                                    bmpDrawable.setBounds(0, 0, width, height);
                                    ssb.setSpan(new ImageSpan(bmpDrawable), x, x + c.length(),
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    x = x + c.length() + 1;
                                }
                                isEdit = false;
                                editText.setText("");
                                mArrayListTags.clear();
                                mArrayListTags.add(String.valueOf(ssb));

                                setFinalTags(mArrayListTags);
                                editText.setFocusable(false);
                                editText.clearFocus();
                                mArrayListTags.clear();
                                editText.setSelection(0);
                                // Check if no view has focus:
                                View view = getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                return true;
                            }

                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }
    View additionView1;
    private void setFinalTags(ArrayList<String> mArrayListTags) {

        LayoutInflater lf = null;
        try {
            lf = (LayoutInflater)getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            additionView1 = lf.inflate(R.layout.single, null);
            AddTags tags = new AddTags();
            tags.setmTvName((TextView) additionView1.findViewById(R.id.txt_tags_entered));
            tags.setmIvRemove((ImageView) additionView1.findViewById(R.id.imv_remove_added_tags));
            mArrayListFinalSelect.add(tags);
            tags.getmTvName().setText(mArrayListTags.get(0).toString());
            int tag = mArrayListFinalSelect.size() - 1;
            tags.getmIvRemove().setTag("" + tag);
            tags.getmIvRemove().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = Integer.parseInt("" + v.getTag());
                    mArrayListFinalSelect.remove(pos);
                    mLayoutAddTags.removeViewAt(pos);
                    reindexing(mArrayListFinalSelect);

                }
            });

            mLayoutAddTags.addView(additionView1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void reindexing(ArrayList<AddTags> mArrayListFinalSelect) {
        for (int i = 0; i < mArrayListFinalSelect.size(); i++) {
            mArrayListFinalSelect.get(i).getmIvRemove().setTag("" + i);
        }
    }


}
