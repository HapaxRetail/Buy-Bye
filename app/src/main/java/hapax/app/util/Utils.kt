package hapax.app.util

import android.widget.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> callback(function: (T) -> Unit) : Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            function.invoke(response.body() ?: return)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {}
    }
}

fun listener(function: (String) -> Unit) : SearchView.OnQueryTextListener {
    return object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(search: String): Boolean {
            function.invoke(search)
            return false
        }

        override fun onQueryTextChange(search: String): Boolean {
            function.invoke(search)
            return true
        }
    }
}

fun <T> List<T>.search(search : String, function: (T) -> String) : List<T> {
    if(search.isEmpty()) return emptyList()

    return filter { item -> function.invoke(item).let { name ->
        name.startsWith(search, true) or
        name.split(" ").any { arg -> arg.startsWith(search, true) }
    } }
}