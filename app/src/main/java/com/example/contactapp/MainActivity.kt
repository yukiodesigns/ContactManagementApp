package com.example.contactapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.myviewModel.UserViewModel
import com.example.contactapp.myviewModel.UserViewModelFactory
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.room.User
import com.example.contactapp.room.UserDatabase
import com.example.contactapp.room.UserRepo
import com.example.contactapp.viewUI.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var  userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ROOM
        val dao = UserDatabase.getInstance(application).userDAO
        val repository = UserRepo(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }
     private fun initRecyclerView(){
         binding.recyclerView.layoutManager = LinearLayoutManager(this)
         DisplayUsersList()
     }

    private fun DisplayUsersList() {
        userViewModel.users.observe(this, Observer {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(
                it, {selectedItem:User -> listItemClicked(selectedItem)}
            )
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this, "Selected name is ${selectedItem.name}", Toast.LENGTH_LONG).show()
        userViewModel.initUpdateAndDelete(selectedItem)
    }
}