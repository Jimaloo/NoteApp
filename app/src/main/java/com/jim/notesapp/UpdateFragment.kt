package com.jim.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class UpdateFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var titleText: EditText
    lateinit var descriptionText: EditText
    var title: String? = null
    var description: String? = null
    var noteId: Int? = null
    lateinit var updateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        titleText = view.findViewById(R.id.titleInput)
        descriptionText = view.findViewById(R.id.descriptionInput)
        updateButton = view.findViewById(R.id.updateButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        title = arguments?.getString("title")
        description = arguments?.getString("description")
        noteId = arguments?.getInt("id")

        if (title != null && description != null) {
            titleText.setText(title)
            descriptionText.setText(description)
        }

        updateButton.setOnClickListener(View.OnClickListener {
            title = titleText.text.toString()
            description = descriptionText.text.toString()
            var updateBundle = bundleOf(
                "titleUpdate" to title,
                "descriptionUpdate" to description,
                "id" to noteId
            )
            navController.navigate(
                R.id.action_updateFragment_to_homePageNotes,
                updateBundle
            )
        })
    }

}