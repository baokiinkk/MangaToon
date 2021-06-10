package com.baokiin.mangatoon.ui.chapterdetail

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.Observer
import com.baokiin.mangatoon.R
import com.baokiin.mangatoon.data.model.DetailManga
import com.baokiin.mangatoon.databinding.FragmentDetailChapterBinding
import com.baokiin.mangatoon.base.BaseFragment
import com.baokiin.mangatoon.adapter.ItemDetailChapterAdapter
import com.baokiin.mangatoon.data.model.Chapter
import com.baokiin.mangatoon.data.model.Manga
import com.baokiin.mangatoon.utils.Utils.DETAILMANGA
import com.baokiin.mangatoon.utils.Utils.ENDPOINT
import com.baokiin.mangatoon.utils.Utils.MANGA
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailChapterFragment :
    BaseFragment<FragmentDetailChapterBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_detail_chapter
    }

    private var indexManga = -1
    val viewModel: DetailChapterViewModel by viewModel()
    private lateinit var onBack: BacktoChap

    override fun onResume() {
        super.onResume()
        if(viewModel.auth.currentUser == null){
            dialogPayment(titleLog = "Login failed!",mess = "The chaper needs 20$ to unlock, please login!")
        }
    }
    override fun onCreateViews() {

        var endpointIndex = arguments?.getInt(ENDPOINT)

        val detailManga = arguments?.getSerializable(DETAILMANGA) as DetailManga
        val manga = arguments?.getSerializable(MANGA) as Manga?
        var clickItem = false

        viewModel.insertRecents(manga)
        val adapters = ItemDetailChapterAdapter { _, _ ->
            baseBinding.constraintLayout2.apply {
                clickItem = if (!clickItem) {
                    transitionToEnd()
                    true
                } else {
                    transitionToStart()
                    false
                }
            }
        }
        if (endpointIndex != null) {
            endpointIndex = detailManga.chapter.size - 1 - endpointIndex
            detailManga.chapter[endpointIndex].chapter_endpoint?.let { viewModel.getData(it) }
            indexManga = endpointIndex
            if (indexManga == 0)
                baseBinding.btnNextChap.visibility = View.GONE
            if (indexManga == detailManga.chapter.size - 1)
                baseBinding.btnBackChap.visibility = View.GONE
        }
        baseBinding.apply {
            viewmodel = viewModel
            adapter = adapters
            btnBack.setOnClickListener {
                onBack.onClickBack()
                requireActivity().onBackPressed()
            }
            btnBackChap.setOnClickListener {
                changeChap(detailManga, false, it)
                btnNextChap.visibility = View.VISIBLE
            }
            btnNextChap.setOnClickListener {
                changeChap(detailManga, true, it)
                btnBackChap.visibility = View.VISIBLE
            }
        }
        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.chapter_endpoint =
                    it.chapter_endpoint?.length?.let { size ->
                        it.chapter_endpoint?.substring(
                            0,
                            size - 1
                        )
                    }
                detailManga.title?.let { title ->
                    viewModel.isLockFromFireBase(it.chapter_endpoint, title)
                }
                adapters.submitList(it.chapter_image)
            }
        })
        viewModel.isUnlock.observe(viewLifecycleOwner, Observer {
            it?.let {
                val mess: String
                val titleLog: String
                if (it) {
                    mess = "The episode needs 20$ to unlock"
                    titleLog = "Unlock"
                } else {
                    mess = "Account balance is not enough, please add more money!"
                    titleLog = "Payment failed!"
                }
                detailManga.title?.let { it1 ->
                    dialogPayment(
                        viewModel.data.value,
                        it1,
                        titleLog,
                        mess
                    )
                }
                viewModel.isUnlock.postValue(null)
            }
        })
    }

    fun resiveOnBackFromDetailChap(click: BacktoChap) {
        onBack = click
    }

    private fun dialogPayment(chapter: Chapter?=null, title: String?=null, titleLog: String, mess: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(titleLog).setMessage(mess)
            setCancelable(true)
            setIcon(R.drawable.ic_lock)
            setCancelable(false)

            if (mess == "The episode needs 20\$ to unlock")
                setPositiveButton("OK") { dialog, _ ->
                    if (title != null) {
                        viewModel.unlock(chapter, title)
                    }
                    dialog.cancel()
                }
            setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
                requireActivity().onBackPressed()
            }
            create().show()
        }
    }

    private fun changeChap(detailManga: DetailManga, boolean: Boolean, view: View) {
        if (boolean) {
            indexManga--
            if (indexManga == 0)
                view.visibility = View.GONE
        } else {
            indexManga++
            if (indexManga == detailManga.chapter.size - 1)
                view.visibility = View.GONE
        }
        detailManga.chapter[indexManga].chapter_endpoint?.let { viewModel.getData(it) }
    }
}

interface BacktoChap {
    fun onClickBack()
}