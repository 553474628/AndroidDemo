package com.example.music;

/**
 * Create by Politness Chen on 2019/9/25--16:43
 * desc:
 */
public interface Iservice {
    void callPlayMusic();
    void callPauseMusic();
    void callRestarstMusic();
    void callSeekTo(int position);
}
