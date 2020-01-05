package com.culttrip.client.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 3:44 AM
 */

//class VortexCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm , ) {
//
//    override fun getCount(): Int  = 100
//
//    override fun getItem(i: Int): Fragment {
//        val fragment = DemoObjectFragment()
//        fragment.arguments = Bundle().apply {
//            // Our object is just an integer :-P
//            putInt(ARG_OBJECT, i + 1)
//        }
//        return fragment
//    }
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return "OBJECT ${(position + 1)}"
//    }
//}