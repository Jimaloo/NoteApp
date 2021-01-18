package com.jim.notesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jim.notesapp.adapter.NoteAdapter
import com.jim.notesapp.model.Note
import com.jim.notesapp.repository.NoteRepository
import com.jim.notesapp.repository.NotesDatabase

class MainFragment : Fragment(), View.OnClickListener, NoteAdapter.ItemClickListener {
    var title: String? = null
    var description: String? = null
    var titleUpdated: String? = null
    var descriptionUpdated: String? = null
    var noteId: Int? = null
    private lateinit var viewModel: MainFragmentViewModel
    lateinit var navController: NavController
    var recyclerView: RecyclerView? = null


    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun init() {
        val adapter = NoteAdapter(this)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.setHasFixedSize(true)

        val application = requireNotNull(activity).application
        val dataSource = NotesDatabase.getInstance(application).noteDao()
        val repository = NoteRepository(dataSource)


        val viewModelFactory = dataSource.let { MainViewModelFactory(repository, it, application) }

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel::class.java)
        viewModel.getAll().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setNotes(it)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        init()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        title = arguments?.getString("title")
        description = arguments?.getString("description")

        titleUpdated = arguments?.getString("titleUpdate")
        descriptionUpdated = arguments?.getString("descriptionUpdate")
        noteId = arguments?.getInt("id")

        if (title != null && description != null) {
            val note = Note(null,title!!, description!!)
            viewModel.insert(note)
        }
        if (titleUpdated != null && descriptionUpdated != null) {
            val note = Note(noteId,titleUpdated!!, descriptionUpdated!!)
            viewModel.update(note)
        }
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener(this)
    }
//
//    override fun onItemClick(v: View, position: Int) {
//       viewModel.delete(NoteAdapter().allNotes[position])
//        Toast.makeText(activity, "Item Clicked", Toast.LENGTH_SHORT).show()
//    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab -> navController.navigate(R.id.action_homePageNotes_to_createNotes)
        }
    }

    override fun onDeleteClicked(allNotes: List<Note>, position: Int) {
        val note = allNotes[position]
        viewModel.delete(note)
       }

    override fun onItemClick( allNotes: List<Note>, position: Int) {
        val note = allNotes[position]
        val currentNoteTitle = note.title
        val currentNoteDescription = note.description
        val currentNoteId = note.id
        val updateBundle = bundleOf(
            "title" to currentNoteTitle,
            "description" to currentNoteDescription,
            "id" to currentNoteId
        )

        navController.navigate(
            R.id.action_homePageNotes_to_updateFragment,
            updateBundle)
    }
}

