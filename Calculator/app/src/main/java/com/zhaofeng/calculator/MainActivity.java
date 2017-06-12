package com.zhaofeng.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnAdd,btnSub,btnMul,btnDiv,btnAc,btnResult;
private TextView screen;

    private List<BtnItem> item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen = (TextView) findViewById(R.id.screenTV);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnAc = (Button) findViewById(R.id.btnAc);
        btnResult = (Button) findViewById(R.id.btnResult);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnAc.setOnClickListener(this);
        btnResult.setOnClickListener(this);
    }

    private long lastClick=0;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(lastClick <= 0){
            Toast.makeText(this,"请再按一次退出应用",Toast.LENGTH_SHORT).show();
            lastClick = System.currentTimeMillis();
        }else {
            long currentClick = System.currentTimeMillis();
            if(currentClick - lastClick < 1000){
                finish();
            }else {
                lastClick = currentClick;
                Toast.makeText(this,"请再按一次退出应用",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        checkAndCompute();
        switch (v.getId()){
            case R.id.btn0:
                checkAndCompute();
                item.add(new BtnItem(BtnTypes.NUMBER,0));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn1:
                item.add(new BtnItem(BtnTypes.NUMBER,1));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn2:
                item.add(new BtnItem(BtnTypes.NUMBER,2));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn3:
                item.add(new BtnItem(BtnTypes.NUMBER,3));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn4:
                item.add(new BtnItem(BtnTypes.NUMBER,4));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn5:
                item.add(new BtnItem(BtnTypes.NUMBER,5));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn6:
                item.add(new BtnItem(BtnTypes.NUMBER,6));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn7:
                item.add(new BtnItem(BtnTypes.NUMBER,7));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn8:
                item.add(new BtnItem(BtnTypes.NUMBER,8));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btn9:
                item.add(new BtnItem(BtnTypes.NUMBER,9));
                screen.setText(item.get(item.size()-1).getValue()+"");
                break;
            case R.id.btnAdd:
                item.add(new BtnItem(BtnTypes.OPERATOR_ADD,0));
                screen.setText("+");
                break;
            case R.id.btnSub:
                item.add(new BtnItem(BtnTypes.OPERATOR_SUB,0));
                screen.setText("-");
                break;
            case R.id.btnMul:
                item.add(new BtnItem(BtnTypes.OPERATOR_MUL,0));
                screen.setText("*");
                break;
            case R.id.btnDiv:
                item.add(new BtnItem(BtnTypes.OPERATOR_DIV,0));
                screen.setText("/");
                break;
            case R.id.btnAc:
                //item.add(new BtnItem(BtnTypes.AC,0));
                screen.setText("0.0");
                break;
            case R.id.btnResult:
                //item.add(new BtnItem(BtnTypes.RESULT,0));
                screen.setText(item.get(0).getValue()+"");
                break;
        }

    }
    public void checkAndCompute(){

        if(item.size() >= 3){
            double first = item.get(0).getValue();
            double last = item.get(2).getValue();
            int type = item.get(1).getType();
            item.clear();
            switch (type){
                case BtnTypes.OPERATOR_ADD:
                    item.add(new BtnItem(BtnTypes.NUMBER,(first+last)));
                    break;
                case BtnTypes.OPERATOR_SUB:
                    item.add(new BtnItem(BtnTypes.NUMBER,(first-last)));
                    break;
                case BtnTypes.OPERATOR_MUL:
                    item.add(new BtnItem(BtnTypes.NUMBER,(first*last)));
                    break;
                case BtnTypes.OPERATOR_DIV:
                    item.add(new BtnItem(BtnTypes.NUMBER,(first/last)));
                    break;
//                case BtnTypes.AC:
//
//                    break;
//                case BtnTypes.RESULT:
//                    screen.setText(item.get(0).getValue()+"");
//                    break;
            }

        }


    }
}
