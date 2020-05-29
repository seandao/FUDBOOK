package com.example.fudbook.ui.bookshelf;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fudbook.R;
import com.example.fudbook.objects.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;



// View bookshelf
public class fragment_bookshelf_1 extends Fragment {

    private static final String TAG = "fragment_bookshelf_1";
    //private String recipeList;
    private RecyclerView recyclerView;
    private bookshelf_adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // cell variables
    private ArrayList<String> titles;
    private ArrayList<String> images;
    private ArrayList<Book> books;
    private ArrayList<String> recipeList;

    private Bundle data;
    private Bundle book_bundle;

    // Connection
    private RequestQueue requestQueue;
    private String API_URL = "http://10.0.2.2:3000";

    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View set up
        View view = inflater.inflate(R.layout.fragment_bookshelf_1, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.books_recycler);
        recyclerView.setHasFixedSize(true);

        titles = new ArrayList<String>();
        books = new ArrayList<Book>();

        // memory set up
        data = new Bundle();
        book_bundle = getArguments();
        System.out.println(book_bundle.getString("favorite book"));

        JSONObject bookshelfBody = new JSONObject();
        JSONArray bookIdArr = new JSONArray();

        // API request
        requestQueue = Volley.newRequestQueue(getContext());

        // place favorite book id
        bookIdArr.put(book_bundle.getString("favorite book"));

        // place personal book id
        bookIdArr.put(book_bundle.getString("personal book"));

        // create a body for request
        try {
            bookshelfBody.accumulate("bookshelf", bookIdArr);
        } catch (Exception e ) { }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, API_URL + "/book/bookshelf",
                bookshelfBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "Printing response");

                    // change to typecheck
                    try {

                        // for loop to get every book title
                        Iterator<String> book_iterator = response.keys();
                        while(book_iterator.hasNext()) {
                            String key = book_iterator.next();
                            //Extract information from each book
                            JSONObject jo = response.getJSONObject(key);
                            String author = jo.getString("author");
                            boolean def = jo.getBoolean("default");
                            String name = jo.getString("name");

                            //Extract recipes from each book
                            try {
                                JSONObject rec_obj = jo.getJSONObject("recipes");
                                Iterator<String> recipe_iterator = rec_obj.keys();
                                recipeList = new ArrayList<String>();

                                while(recipe_iterator.hasNext()) {
                                    String rec_key = recipe_iterator.next();
                                    recipeList.add(rec_key);
                                }

                                books.add(new Book(name, author, def, recipeList));
                            }catch(Exception e){
                                System.out.println(e);
                                books.add(new Book(name, author, def, new ArrayList<String>()));
                            }
                            titles.add(name);
                        }
                        // set up layout manager
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);

                        // define an adapter --> send context, titles, images
                        mAdapter = new bookshelf_adapter(getContext(), titles, images, books);
                        recyclerView.setAdapter(mAdapter);
                        // set on click listener for each item
                        mAdapter.setOnItemClickListener(adapter_listener);

                    } catch (Exception e)
                    {
                        System.out.print(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });
        requestQueue.add(jor);
        return view;
    }

    private bookshelf_adapter.OnItemClickListener adapter_listener = new bookshelf_adapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            System.out.println("Printing book's recipe");
            for(String i : mAdapter.getBook(position).getRecipes()){
                System.out.println(i);
            }
            // store clicked item title into bundle
            data.putStringArrayList("recipe id", mAdapter.getBook(position).getRecipes()); // key for recipe id's

            // load book's recipes
            FragmentManager fm = getParentFragmentManager();
            Fragment book_frag = new fragment_bookshelf_2();

            // send recipe id's
            book_frag.setArguments(data);
            fm.beginTransaction()
                    .replace(R.id.bookshelf_container, book_frag)
                    .addToBackStack("going back to bookshelf")
                    .commit();

        }
    };

}


