package com.example.firestore

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import java.util.*

class QuerySampleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val btnUpload = findViewById<Button>(R.id.btnUpload)
        val btnGetData = findViewById<Button>(R.id.btnGetData)
        val btnWhereEqualto = findViewById<Button>(R.id.btnWhereEqual)
        val btnWhereNotEqualTo = findViewById<Button>(R.id.btnwhereNotEqualTo)
        val btnWhereLess = findViewById<Button>(R.id.btnWhereLessThan)
        val btnWhereGreaterThanOrEqualTo = findViewById<Button>(R.id.btnwhereGreaterThanOrEqualTo)
        val btnWhereArrayContains = findViewById<Button>(R.id.btnwhereArrayContains)
        val btnWhereIn = findViewById<Button>(R.id.btnwhereIn)
        val btnWhereArrayContainsAny = findViewById<Button>(R.id.btnwhereArrayContainsAny)
        val btnCompoundQuery = findViewById<Button>(R.id.btnCompoundQuery)
        val btnCollectiongrp = findViewById<Button>(R.id.btnCollectiongrp)
        val btnOrderBy = findViewById<Button>(R.id.btnOrderBy)
        val cities = db.collection("cities")
        val citiesDetails = db.collection("citiesDetails")
        var result="";

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "CHINA",
            "capital" to true,
            "population" to 780000,
            "regions" to listOf("east_coast", "Lotus")
        )
        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 950000,
            "regions" to listOf("west_coast", "socal")
        )
        val ggbData = mapOf(
            "name" to "Golden Gate Bridge",
            "type" to "bridge"
        )

        val lohData = mapOf(
            "name" to "Legion of Honor",
            "type" to "museum"
        )


        val gpData = mapOf(
            "name" to "Griffth Park",
            "type" to "park"
        )


        val tgData = mapOf(
            "name" to "The Getty",
            "type" to "museum"
        )


        val lmData = mapOf(
            "name" to "Lincoln Memorial",
            "type" to "memorial"
        )


        val nasaData = mapOf(
            "name" to "National Air and Space Museum",
            "type" to "museum"
        )


        val upData = mapOf(
            "name" to "Ueno Park",
            "type" to "park"
        )


        val nmData = mapOf(
            "name" to "National Musuem of Nature and Science",
            "type" to "museum"
        )


        val jpData = mapOf(
            "name" to "Jingshan Park",
            "type" to "park"
        )


        val baoData = mapOf(
            "name" to "Beijing Ancient Observatory",
            "type" to "musuem"
        )

        /*
        * To upload data's from user to firestore we can use both set and add to update data*/
        btnUpload.setOnClickListener {
            cities.document("SF").set(data1)
            cities.document("LA").set(data2)
            cities.document("DC").set(data3)
            citiesDetails.document("SF").collection("landmarks").add(ggbData)
            citiesDetails.document("SF").collection("landmarks").add(lohData)
            citiesDetails.document("LA").collection("landmarks").add(gpData)
            citiesDetails.document("LA").collection("landmarks").add(tgData)
            citiesDetails.document("DC").collection("landmarks").add(lmData)
            citiesDetails.document("DC").collection("landmarks").add(nasaData)
            citiesDetails.document("TOK").collection("landmarks").add(upData)
            citiesDetails.document("TOK").collection("landmarks").add(nmData)
            citiesDetails.document("BJ").collection("landmarks").add(jpData)
            citiesDetails.document("BJ").collection("landmarks").add(baoData)
        }


        /*To fetch all the data uploaded to firestore*/
        btnGetData.setOnClickListener {
            result = ""
            cities
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        result += document.data.toString()+"\n"

                    }
                    startActivity(result)

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(baseContext, "Error saving data$exception", Toast.LENGTH_SHORT)
                        .show()
                    Log.d(TAG, "Error saving data =>  $exception ")


                }

        }

        /*whereEqualTo - We can compare any data type like boolean,long,string,int etc...*/
        btnWhereEqualto.setOnClickListener {
            result = ""
            cities.whereEqualTo("state", "CA")
                //.whereEqualTo("capital", true)

                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /*whereEqualTo - We can compare any data type like boolean,long,string,int etc...*/
        btnWhereNotEqualTo.setOnClickListener {
            result = ""
            cities.whereNotEqualTo("state", "CA")
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /*whereLessThan - It fetches the country which has population less than 100000*/
        /*whereGreaterThan - It fetches the country which has population greater than 100000*/
        /*Can Compare any datatype*/
        btnWhereLess.setOnClickListener {
            result = ""

            cities.whereLessThan("population", 900000)
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }
        }


        /*whereGreaterThanOrEqualTo - It fetches the Washington D.C and greater than W example if Vietnem is there
           * it will show Washington D.C and Vietnem*/
        /*Valid scenario - cities.whereGreaterThanOrEqualTo("state", "CA")
   .whereLessThanOrEqualTo("state", "IN")
Invalid Scenario: citiesRef.whereEqualTo("state", "CA")
   .whereGreaterThan("population", 1000000)

        * */
        btnWhereGreaterThanOrEqualTo.setOnClickListener {
            result = ""

            cities.whereGreaterThanOrEqualTo("name", "Washington D.C.")
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /*whereArrayContains - It fetches list which has list with region having west_coast
              * Limit upto 10 we can get*/
        btnWhereArrayContains.setOnClickListener {
            result = ""

            cities.whereArrayContains("regions", "west_coast")
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /*whereIn -Fetches list with  country  "USA" and "JAPAN" added listof so that we can add more
 Countries to fetch
 Same thing for wherenotin -Fetches list   country not having  "USA" and "JAPAN" added listof so that we can add more
 Countries to fetch
 Limit upto 10 we can get
 * */

        btnWhereIn.setOnClickListener {
            result = ""
            cities.whereIn("country", listOf("USA", "JAPAN"))
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /* whereArrayContainsAny -combine up to 10 array-contains clauses on the same field with a logical OR.
            tried arrayof got this error-java.lang.IllegalArgumentException: Could not serialize object.
            Serializing Arrays is not supported, please use Lists instead (found in field '[0]')*/

        btnWhereArrayContainsAny.setOnClickListener {
            result = ""
            cities.whereArrayContainsAny("regions", listOf("west_coast", "east_coast"))
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }

        /*
        com.google.firebase.firestore.FirebaseFirestoreException: FAILED_PRECONDITION:
          You can create it here: https://console.firebase.google.com/v1/r/project/firestore-e45dc/firestore
          /indexes?create_exemption=ClJwcm9qZWN0cy9maXJlc3RvcmUtZTQ1ZGMvZGF0YWJhc2VzLyhkZWZhdWx0KS9jb2xsZWN0aW9uR3JvdXBzL2xhbmRtYXJrL2ZpZWxkcy90e
          XBlEAIaCAoEdHlwZRAB
*/
        /* When use same query for different field  java.lang.IllegalArgumentException: All
 where filters with an inequality (notEqualTo, notIn, lessThan, lessThanOrEqualTo, greaterThan,
     or greaterThanOrEqualTo) must be on the same field. But you have filters on 'state' and 'population'*/

        btnCompoundQuery.setOnClickListener {
            result = ""
            cities.whereEqualTo("country", "USA")
                .whereEqualTo("state","CA")
                .whereGreaterThan("population",900000)
              //  .whereGreaterThanOrEqualTo("state", "CA")
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }


        btnCollectiongrp.setOnClickListener {
            result = ""

            db.collectionGroup("landmarks").whereEqualTo("type" ,"musuem")
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }
        /*Limit: Greater than and orderby possible if both refers same field otherwise it wont work*/
        btnOrderBy.setOnClickListener {
            cities.orderBy("population",Query.Direction.DESCENDING).limit(3)
                .addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen error", e)
                        Toast.makeText(baseContext, "Error saving data$e", Toast.LENGTH_SHORT)
                            .show()
                        return@addSnapshotListener
                    }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d(TAG, "New city: ${change.document.data}")
                            result += change.document.data

                        }
                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d(TAG, "Data fetched from $source")
                    }
                    startActivity(result)

                }


        }


    }

    private fun startActivity(result: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Data", result)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()

    }

    companion object {
        fun newInstance(context: android.content.Context) =
            Intent(context, QuerySampleActivity::class.java)
    }


}