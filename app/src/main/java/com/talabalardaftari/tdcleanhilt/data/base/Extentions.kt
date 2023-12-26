package com.talabalardaftari.tdcleanhilt.data.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Matcher
import java.util.regex.Pattern

fun View.gone(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.INVISIBLE
}
fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}
fun Activity.toast(msg: String?){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}
fun Context.createDialog(layout: Int, cancelable: Boolean): Dialog {
    val dialog = Dialog(this, android.R.style.Theme_Dialog)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(layout)
    dialog.window?.setGravity(Gravity.CENTER)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(cancelable)
    dialog.create()
    return dialog
}
fun snackbar(message:String,view:View){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun String.checkPhone():Boolean{
    val REG ="^(\\+998|998)(90|91|93|94|95|97|98|99|88)\\d{7}$"
    val pattern = Pattern.compile(REG)
    return pattern.matcher(this).find()
}
fun String.checkGmail():Boolean{
    val REG = "^(\\d{5})@(gmail|email|yahoo).\\d{3}$"
    val pattern = Pattern.compile(REG)
    return pattern.matcher(this).find()
}

 fun String.parseCode(): String {
    val p = Pattern.compile("\\b\\d{6}\\b")
    val m: Matcher = p.matcher(this)
    var code = ""
    while (m.find()) {
        code = m.group(0)
    }
    return code
}
fun String.validatePhoneNumber():String{
    if (this[0]!='+'){
        return "Nomber '+' belgi bilan boshlanishi kerak"
    }else if (this.length!=13){
        return "Nomer uzunligi 13 ga teng bo'lishi kerak. \n Namuna +998901234567"
    }
    return "true"
}