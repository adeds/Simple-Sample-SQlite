package adeds.id.ac.stta.sqliteop;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import adeds.id.ac.stta.sqliteop.database.DBAdapter;
import adeds.id.ac.stta.sqliteop.database.Message;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName, edtPass, edtNew, edtOld, edtDel;
    private Button btnAdd, btnView, btnUpdate, btnDel;
    DBAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edt_uname);
        edtPass = (EditText) findViewById(R.id.edt_pass);
        edtOld = (EditText) findViewById(R.id.edt_old);
        edtNew = (EditText) findViewById(R.id.edt_new);
        edtDel = (EditText) findViewById(R.id.edt_del);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnView = (Button) findViewById(R.id.btn_view);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDel = (Button) findViewById(R.id.btn_delete);

        btnAdd.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        adapter = new DBAdapter(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add: {
                String name = edtName.getText().toString();
                String pass = edtPass.getText().toString();
                Log.e("get", name + " " + pass);
                if (name.isEmpty() || pass.isEmpty()) {
                    Message.message(this, "Masukkan Username dan password");
                } else {
                    try {
                        long id_ins = adapter.insertData(name, pass);
                        Log.e("id_ins", "" + id_ins);
                        if (id_ins >= 0) {
                            Message.message(this, "Berhasil Insert ");
                            edtName.setText("");
                            edtPass.setText("");
                        } else {
                            Message.message(this, "Gagal Insert");
                            edtName.setText("");
                            edtPass.setText("");
                        }
                    } catch (SQLException e) {
                        Log.e("errIns", e.getMessage().toString());
                    }
                }
            }

                break;
                case R.id.btn_view: {
                    String data = adapter.getData();
                    Message.message(this, data);
                }
                break;
                case R.id.btn_update: {
                    String oldData = edtOld.getText().toString();
                    String newData = edtNew.getText().toString();

                    if (oldData.isEmpty() || newData.isEmpty()) {
                        Message.message(this, "Masukkan Data Update");
                    } else {
                        int a = adapter.update(oldData, newData);
                        if (a <= 0) {
                            Message.message(this, "Gagal Update");
                            edtNew.setText("");
                            edtOld.setText("");

                        } else {
                            Message.message(this, "Terupdate");
                            edtNew.setText("");
                            edtOld.setText("");
                        }
                    }

                }
                break;
                case R.id.btn_delete: {
                    String nameDel = edtDel.getText().toString();
                    if (nameDel.isEmpty()) {
                        Message.message(this, "Masukkan Data");
                    } else {
                        int a = adapter.delete(nameDel);
                        if (a <= 0) {
                            Message.message(this, "Gagal Hapus");
                            edtDel.setText("");
                        } else {
                            Message.message(this, "Terhapus");
                        }
                    }
                }
                break;
            }
        }
    }

