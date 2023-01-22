package com.lotterysystem.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lotterysystem.app.R
import com.lotterysystem.app.ui.fragment.home.response.Ticket
import java.text.SimpleDateFormat
import java.util.*

class TicketsAdapter(
    private val dataSet: List<Ticket>,
    val context: Context,
    private val onClickItem: (item: Ticket) -> Unit
) :
    ListAdapter<Ticket, TicketsAdapter.ViewHolder>(TrackItemListDiffCallBack()) {

    class ViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
        val txtTicketID: TextView
        val txtTicketDate: TextView

        init {
            // Define click listener for the ViewHolder's View.
            txtTicketDate = view.findViewById(R.id.text_ticket_date)
            txtTicketID = view.findViewById(R.id.text_ticket_id)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_ticket_cell_item, viewGroup, false)

        return ViewHolder(context, view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.txtTicketID.text = "Ticket ID: ${dataSet[position].id}"

        viewHolder.txtTicketDate.text= getDateFromTimestamp(position)

        viewHolder.itemView.setOnClickListener {
            onClickItem(dataSet[position])
        }
    }

    private fun getDateFromTimestamp(position: Int): String {
        val sdf = SimpleDateFormat(context.getString(R.string.date_format_d_m_y_h_m_a))
        val netDate = Date(dataSet[position].created)
        val date = sdf.format(netDate)
        return date
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class TrackItemListDiffCallBack : DiffUtil.ItemCallback<Ticket>() {
        override fun areItemsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ticket, newItem: Ticket): Boolean {
            return oldItem == newItem
        }

    }

}
