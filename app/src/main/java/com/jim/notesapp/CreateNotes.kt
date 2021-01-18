package com.jim.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class CreateNotes : Fragment(), View.OnClickListener {

    lateinit var titleText: EditText
    lateinit var descriptionText: EditText
    lateinit var saveButton: Button

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_notes, container, false)

        saveButton = view.findViewById(R.id.saveButton)
        titleText = view.findViewById(R.id.titleInput)
        descriptionText = view.findViewById(R.id.descriptionInput)

        saveButton.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.saveButton -> {
                if (titleText.text.trim().isEmpty() || descriptionText.text.trim().isEmpty()) {
                    Toast.makeText(activity, "Enter Title or Description", Toast.LENGTH_SHORT)
                        .show()
                    return
                }
                val bundle = bundleOf(
                    "title" to titleText.text.toString(),
                    "description" to descriptionText.text.toString()
                )
                navController.navigate(
                    R.id.action_createNotes_to_homePageNotes,
                    bundle
                )
            }


        }
    }
}


