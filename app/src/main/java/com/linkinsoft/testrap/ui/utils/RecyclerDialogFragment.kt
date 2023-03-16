package com.linkinsoft.testrap.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkinsoft.testrap.R


class RecyclerDialogFragment(
    private val list: List<String>,
    @LayoutRes val resourceLayout: Int? = null,
    @IdRes val resourceRV: Int? = null,
    val callback: CallbackItem
) : BaseDialog() {


    companion object {
        fun newInstance(
            list: List<String>,
            @LayoutRes resourceLayout: Int? = null,
            @IdRes resourceRV: Int? = null,
            callback: CallbackItem
        ): RecyclerDialogFragment {
            return RecyclerDialogFragment(list, resourceLayout, resourceRV, callback)
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mBuilder = AlertDialog.Builder(requireActivity())
        val inflater = activity?.layoutInflater
        val view = inflater!!.inflate(resourceLayout ?: R.layout.dialog_my_custom_recycler, null)
        val mRv: RecyclerView =
            view.findViewById(resourceRV ?: R.id.dialog_my_custom_recycler_view) as RecyclerView
        val mlayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val mAdapter = GenericListAdapter(object : CallbackItem {
            override fun onItemSelected(position: Int) {
                callback.onItemSelected(position)
                dismiss()
            }
        })
        mRv.apply {
            layoutManager = mlayoutManager
            adapter = mAdapter
        }.addItemDecoration(DividerItemDecoration(mRv.context, mlayoutManager.orientation))
        mAdapter.submitList(list)

        mBuilder.setView(view)
        return mBuilder.create()
    }
}