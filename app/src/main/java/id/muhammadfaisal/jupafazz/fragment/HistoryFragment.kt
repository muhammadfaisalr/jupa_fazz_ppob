package id.muhammadfaisal.jupafazz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.muhammadfaisal.jupafazz.R
import id.muhammadfaisal.jupafazz.adapter.HistoryAdapter
import id.muhammadfaisal.jupafazz.databinding.FragmentHistoryBinding
import id.muhammadfaisal.jupafazz.dummy.Dummy
import id.muhammadfaisal.jupafazz.utils.Font

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        this.binding = FragmentHistoryBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initialize()
    }

    private fun initialize() {
        this.binding.let {
            it.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.recyclerView.adapter = HistoryAdapter(requireContext(), Dummy.getHistories())
            it.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
            Font.setInto((this.requireActivity() as AppCompatActivity), Font.Rubik.MEDIUM, it.textTitle)
        }
    }
}