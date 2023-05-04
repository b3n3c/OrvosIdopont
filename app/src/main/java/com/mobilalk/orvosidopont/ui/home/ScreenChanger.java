package com.mobilalk.orvosidopont.ui.home;

import androidx.fragment.app.Fragment;

public interface ScreenChanger {
    void addFragment(Fragment fragment);
    void goBack();
    void goForward();
}
