package com.example.cs222receiptscanner

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE = 42
private lateinit var photoFile: File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            print(takePictureIntent)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(this, "com.example.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            print(takePictureIntent)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }

        addItem.setOnClickListener{
            val db = DBHelper(this, null)
            val mpuv2 = MultipartUtilityV2("https://ocr.asprise.com/api/v1/receipt")
            mpuv2.addFormField("api_key", "TEST")
            mpuv2.addFormField("recognizer", "US")
            mpuv2.addFilePart("file", photoFile)
            val res = mpuv2.finish()
            try {
                val itemArray = res.getJSONArray("receipts").getJSONObject(0).getJSONArray("items")
                println(itemArray)
                for ( i in 0 until itemArray.length()) {
                    val item = itemArray.getJSONObject(i)
                    val name = item.getString("description")
                    val price = item.getDouble("amount")

                    db.addName(name, price.toString())

                    Toast.makeText(this, name + " added to database", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        clearDB.setOnClickListener{
            val db = DBHelper(this, null)
            db.clearDatabase()
            Item.text = "Item\n\n"
            Price.text = "Price\n\n"

            Toast.makeText(this, "Cleared Database", Toast.LENGTH_LONG).show()
        }

        printItem.setOnClickListener{
            val db = DBHelper(this, null)
            val cursor = db.getName()
            Item.text = "Item\n\n"
            Price.text = "Price\n\n"

            cursor!!.moveToFirst()
            Item.append(cursor.getString(cursor.getColumnIndex(DBHelper.ITEM_COL)) + "\n")
            Price.append(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)) + "\n")

            while(cursor.moveToNext()){
                Item.append(cursor.getString(cursor.getColumnIndex(DBHelper.ITEM_COL)) + "\n")
                Price.append(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE_COL)) + "\n")
            }
            cursor.close()
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

//    private fun testV2() {
//            val testjson = JSONObject("{\n" +
////                    "  \"request_id\" : \"P_218.186.139.27_kl96a7ie_o9k\",\n" +
////                    "  \"ref_no\" : \"AspDemo_1613550458205_154\",\n" +
////                    "  \"file_name\" : \"SG-01s.jpg\",\n" +
////                    "  \"request_received_on\" : 1613550460551,\n" +
////                    "  \"success\" : true,\n" +
////                    "  \"image_width\" : 1200,\n" +
////                    "  \"image_height\" : 1600,\n" +
////                    "  \"image_rotation\" : -0.006,\n" +
////                    "  \"recognition_completed_on\" : 1613550461387,\n" +
////                    "  \"receipts\" : [ {\n" +
////                    "    \"merchant_name\" : \"McDonald's\",\n" +
////                    "    \"merchant_address\" : \"600 @ Toa Payoh #01-02, Singapore 319515\",\n" +
////                    "    \"merchant_phone\" : \"62596362\",\n" +
////                    "    \"merchant_website\" : null,\n" +
////                    "    \"merchant_tax_reg_no\" : \"M2-0023981-4\",\n" +
////                    "    \"merchant_company_reg_no\" : null,\n" +
////                    "    \"merchant_logo\" : \"/ocr/api/img/demo/logos/mcd.jpg\",\n" +
////                    "    \"region\" : null,\n" +
////                    "    \"mall\" : \"600 @ Toa Payoh\",\n" +
////                    "    \"country\" : \"SG\",\n" +
////                    "    \"receipt_no\" : \"002201330026\",\n" +
////                    "    \"date\" : \"2016-01-13\",\n" +
////                    "    \"time\" : \"15:49\",\n" +
////                    "    \"items\" : [ {\n" +
////                    "      \"amount\" : 2.95,\n" +
////                    "      \"description\" : \"Med Ice Lemon Tea\",\n" +
////                    "      \"flags\" : \"\",\n" +
////                    "      \"qty\" : 1,\n" +
////                    "      \"remarks\" : null,\n" +
////                    "      \"unitPrice\" : null\n" +
////                    "    }, {\n" +
////                    "      \"amount\" : 2.40,\n" +
////                    "      \"description\" : \"Coffee with Milk\",\n" +
////                    "      \"flags\" : \"\",\n" +
////                    "      \"qty\" : 1,\n" +
////                    "      \"remarks\" : null,\n" +
////                    "      \"unitPrice\" : null\n" +
////                    "    } ],\n" +
////                    "    \"currency\" : \"SGD\",\n" +
////                    "    \"total\" : 5.35,\n" +
////                    "    \"subtotal\" : null,\n" +
////                    "    \"tax\" : 0.35,\n" +
////                    "    \"service_charge\" : null,\n" +
////                    "    \"tip\" : null,\n" +
////                    "    \"payment_method\" : \"cash\",\n" +
////                    "    \"payment_details\" : null,\n" +
////                    "    \"credit_card_type\" : null,\n" +
////                    "    \"credit_card_number\" : null,\n" +
////                    "    \"ocr_text\" : \"         McDonald's Toa Payoh Central\\n            600 @ Toa Payoh #01-02,\\n              Singapore 319515\\n                Tel: 62596362\\n       McDonald's Restaurants Pte Ltd\\n          GST REGN NO : M2-0023981-4\\n                 TAX INVOICE\\n   INV# 002201330026\\n  ORD \$57 -REG # 1 - 13/01/2016 15:49:52\\n  QTY ITEM                            TOTAL\\n    1 Med Ice Lemon Tea                2.95\\n    1 Coffee with Milk                 2.40\\n Eat- In Total (incl GST)              5.35\\n Cash Tendered                         10.00\\n Change                                 4.65\\n TOTAL INCLUDES GST OF                  0.35\\n     Thank You and Have A Nice Day\",\n" +
////                    "    \"ocr_confidence\" : 96.60,\n" +
////                    "    \"width\" : 954,\n" +
////                    "    \"height\" : 1170,\n" +
////                    "    \"avg_char_width\" : null,\n" +
////                    "    \"avg_line_height\" : null,\n" +
////                    "    \"source_locations\" : {\n" +
////                    "      \"date\" : [ [ { \"y\" : 684, \"x\" : 500 }, { \"y\" : 681, \"x\" : 965 }, { \"y\" : 747, \"x\" : 966 }, { \"y\" : 750, \"x\" : 501 } ] ],\n" +
////                    "      \"total\" : [ [ { \"y\" : 957, \"x\" : 936 }, { \"y\" : 957, \"x\" : 1041 }, { \"y\" : 1012, \"x\" : 1041 }, { \"y\" : 1012, \"x\" : 936 } ] ],\n" +
////                    "      \"receipt_no\" : [ [ { \"y\" : 588, \"x\" : 246 }, { \"y\" : 587, \"x\" : 528 }, { \"y\" : 645, \"x\" : 528 }, { \"y\" : 646, \"x\" : 246 } ] ],\n" +
////                    "      \"doc\" : [ [ { \"y\" : 163, \"x\" : 44 }, { \"y\" : 157, \"x\" : 1094 }, { \"y\" : 1444, \"x\" : 1102 }, { \"y\" : 1450, \"x\" : 52 } ] ],\n" +
////                    "      \"merchant_name\" : [ [ { \"y\" : 223, \"x\" : 254 }, { \"y\" : 215, \"x\" : 876 }, { \"y\" : 260, \"x\" : 877 }, { \"y\" : 269, \"x\" : 255 } ] ],\n" +
////                    "      \"tax\" : [ [ { \"y\" : 1200, \"x\" : 949 }, { \"y\" : 1201, \"x\" : 1057 }, { \"y\" : 1258, \"x\" : 1056 }, { \"y\" : 1257, \"x\" : 948 } ] ],\n" +
////                    "      \"merchant_address\" : [ [ { \"y\" : 262, \"x\" : 318 }, { \"y\" : 261, \"x\" : 832 }, { \"y\" : 309, \"x\" : 832 }, { \"y\" : 309, \"x\" : 318 } ], [ { \"y\" : 308, \"x\" : 383 }, { \"y\" : 304, \"x\" : 768 }, { \"y\" : 356, \"x\" : 769 }, { \"y\" : 361, \"x\" : 384 } ] ],\n" +
////                    "      \"merchant_phone\" : [ [ { \"y\" : 354, \"x\" : 539 }, { \"y\" : 354, \"x\" : 719 }, { \"y\" : 396, \"x\" : 719 }, { \"y\" : 397, \"x\" : 539 } ] ],\n" +
////                    "      \"merchant_tax_reg_no\" : [ [ { \"y\" : 444, \"x\" : 574 }, { \"y\" : 438, \"x\" : 856 }, { \"y\" : 482, \"x\" : 857 }, { \"y\" : 488, \"x\" : 575 } ] ]\n" +
////                    "    }\n" +
////                    "  } ]\n" +
////                    "}")

//
//
//    }

}