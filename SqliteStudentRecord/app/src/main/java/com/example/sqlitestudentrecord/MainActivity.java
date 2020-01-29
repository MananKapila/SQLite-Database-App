package com.example.sqlitestudentrecord;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    HelperFile myDB;
    EditText editTextid,editName,editEmail,editCC;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonDelete,buttonViewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB= new HelperFile(this);
        editTextid=findViewById(R.id.editText_id);
        editName=findViewById(R.id.editText_name);
        editEmail=findViewById(R.id.editText_email);
        editCC=findViewById(R.id.editText_CC);

        buttonAdd=findViewById(R.id.button_add);
        buttonGetData=findViewById(R.id.button_view);
        buttonDelete=findViewById(R.id.button_delete);
        buttonUpdate=findViewById(R.id.button_update);
        buttonViewAll=findViewById(R.id.button_viewAll);
       // showMessage("test","testing done");
        AddData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }
    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted= myDB.insertData(editTextid.getText().toString(),editName.getText().toString(),editEmail.getText().toString(),editCC.getText().toString());
                if (isInserted==true){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this,"Something Went Wrong!",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void  getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextid.getText().toString();
                if(id.equals(String.valueOf(""))){
                    editTextid.setError("Enter ID !");
                    return;
                }
                Cursor cursor =myDB.getData(id);
                String data= null;

                if(cursor.moveToNext()){
                    data="ID: "+ cursor.getString(0) +"\n"+
                            "Name: "+ cursor.getString(1) +"\n"+
                            "Email: "+ cursor.getString(2)+ "\n"+
                            "Course Count: "+ cursor.getString(3)+"\n";
                }
                showMessage("Data: ",data);
//                if(cursor==null){
//                    showMessage("NO DATA AVAILABLE ",data);
//                }
            }
        });
    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=myDB.getAllData();
                if(cursor.getCount()==0){
                    showMessage("Error","Nothing found in DB");
                    return;
                }
                StringBuffer buffer =new StringBuffer();
                while(cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All data",buffer.toString());
            }
        });
    }
    public void updateData() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(editTextid.getText().toString(), editName.getText().toString(), editEmail.getText().toString(), editCC.getText().toString());
                if (isUpdate == true) {
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "OOPS!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
     public void deleteData() {
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer deleteRow = myDB.deleteData(editTextid.getText().toString());
                    if (deleteRow > 0) {
                        Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "OOPS !!", Toast.LENGTH_SHORT).show();
                    }
                    if(editTextid.equals(String.valueOf(""))){
                        editTextid.setError("Enter ID !");
                        return;
                    }
                }
            });
        }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
