package ru.netology.kotlin.dz3_1standroidapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.kotlin.dz3_1standroidapp.dto.Post
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    // Цвета для текста
    val activeColor = Color.RED
    val inactiveColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val post = EventPost(
            1, "Netology", "First post in our network!", "20 august 2019",
            false, false, true,
            0, 8, 2,
            "Ижевск",
            56.852562 to 53.208176
        )
        //Прорисовка содержимого
        createdTv.text = post.created
        authorTv.text = post.author
        contentTv.text = post.content
        likeQtyTv.text = post.likesQty.toString()
        if (post.likesQty > 0) likeQtyTv.visibility = VISIBLE
        else likeQtyTv.visibility = INVISIBLE
        commentQtyTv.text = post.commentsQty.toString()
        if (post.commentsQty > 0) commentQtyTv.visibility = VISIBLE
        else commentQtyTv.visibility = INVISIBLE
        shareQtyTv.text = post.sharesQty.toString()
        if (post.sharesQty > 0) shareQtyTv.visibility = VISIBLE
        else shareQtyTv.visibility = INVISIBLE

        if (post.likedByMe) {
            likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
            likeQtyTv.setTextColor(activeColor)
        }
        if (post.commentedByMe) {
            commentBtn.setImageResource(R.drawable.ic_chat_bubble_active_24dp)
            commentQtyTv.setTextColor(activeColor)
        }
        if (post.sharedByMe) {
            shareBtn.setImageResource(R.drawable.ic_share_active_24dp)
            shareQtyTv.setTextColor(activeColor)
        }

        //----Создадим универсальный listener, работающий для любых наших кнопок------
        val listener = View.OnClickListener {
            var trigger =
                post::likedByMe //инициализация переменной для хранения ссылки на свойство-флаг нажатия
            var counter =
                post::likesQty //инициализация переменной для хранения ссылки на свойство-количество нажатий
            var checkedBtn = R.drawable.ic_favorite_active_24dp //инициализация переменной, хранящей вид нажатой кнопки
            var uncheckedBtn = R.drawable.ic_favorite_inactive_24dp //инициализация переменной, хранящей вид не нажатой кнопки
            var textField = likeQtyTv //инициализация переменной, хранящей указатель на поле текущего счетчика
            when (it.id) {
                R.id.likeBtn -> {
                    trigger = post::likedByMe
                    counter = post::likesQty
                    checkedBtn = R.drawable.ic_favorite_active_24dp
                    uncheckedBtn = R.drawable.ic_favorite_inactive_24dp
                    textField = likeQtyTv
                }
                R.id.commentBtn -> {
                    trigger = post::commentedByMe
                    counter = post::commentsQty
                    checkedBtn = R.drawable.ic_chat_bubble_active_24dp
                    uncheckedBtn = R.drawable.ic_chat_bubble_inactive_24dp
                    textField = commentQtyTv
                }
                R.id.shareBtn -> {
                    trigger = post::sharedByMe
                    counter = post::sharesQty
                    checkedBtn = R.drawable.ic_share_active_24dp
                    uncheckedBtn = R.drawable.ic_share_inactive_24dp
                    textField = shareQtyTv
                }
            }
            if (trigger.get()) {//если метка ранее уже стояла, значит счетчик нужно уменьшить
                counter.set(counter.get() - 1)
                //и поменять изображение на кнопке
                if (it is ImageButton) it.setImageResource(uncheckedBtn) //smart cast
                //цвет текста счетчика сделать обычным
                textField.setTextColor(inactiveColor)
            }
            else {//если ранее не стояла - счетчик увеличить
                counter.set(counter.get() + 1)
                //и поменять изображение на кнопке
                if (it is ImageButton) it.setImageResource(checkedBtn) //smart cast
                //цвет текста счетчика сделать активным
                textField.setTextColor(activeColor)
            }
            //далее саму метку инвертировать
            trigger.set(!trigger.get())
            //вывести количество нажатий
            if (counter.get()>0) textField.visibility = VISIBLE
            else textField.visibility = INVISIBLE
            textField.setText(counter.get().toString())
        } //--------Конец listener---------

        //Назначить универсальный listener кнопкам
        likeBtn.setOnClickListener(listener)
        commentBtn.setOnClickListener(listener)
        shareBtn.setOnClickListener(listener)

        //Задача 2. Карта по нажатию на кнопку
        locationBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("geo:${post.coordinates.first},${post.coordinates.second}")
            }
            startActivity(intent)
        }
    }
}
