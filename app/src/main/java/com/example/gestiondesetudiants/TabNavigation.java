package com.example.gestiondesetudiants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TabNavigation extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_navigation);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewpager);
        tabLayout.setupWithViewPager(viewPager);
    TabNavAdapter tabNavAdapter = new TabNavAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabNavAdapter.addFragment(new FragmentPresence(),"Présence");
        tabNavAdapter.addFragment(new FragmentReclamation(),"Reclamation");
        tabNavAdapter.addFragment(new FragmentListeEtudiant(),"Liste Etudiant");
        viewPager.setAdapter(tabNavAdapter);
    }
}