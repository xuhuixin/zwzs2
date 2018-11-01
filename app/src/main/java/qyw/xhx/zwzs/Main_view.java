package qyw.xhx.zwzs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class Main_view extends AppCompatActivity {

    public MyApplication myApplication;//初始化全局变量
    ViewPager mainActivityViewPager;
    BottomNavigationView bottomNavView;
    MainActivityViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        myApplication = (MyApplication) getApplication(); //获得自定义的应用程序YApp
        //将我们放到进程中的全局变量拿出来，看是不是我们曾经设置的值
        Log.i("菜单页面", "InitLabel:"+myApplication.getNumber());
        Log.i("菜单页面", "InitLabel:"+myApplication.getCity());
        Log.i("菜单页面", "InitLabel:"+myApplication.getServer_url());
//        获取到两个控件的对象
        mainActivityViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.main_bottom_nav_view);
        //        为ViewPager设置Adapter
        adapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
//        为Adapter添加Fragment
        adapter.addFragment(new Zy_fragment_new()); //添加第一个碎片资源
        adapter.addFragment(new Home_fragment2());
        adapter.addFragment(new Wh_fragment_new());
        mainActivityViewPager.setAdapter(adapter);

//        为 BottomNavigationView 的菜单项  设置监听事件
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//               获取到菜单项的Id
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.btm_nav_item1:
                        mainActivityViewPager.setCurrentItem(0);
                        Log.d("1111","11111");
                        break;
                    case R.id.btm_nav_item2:
                        mainActivityViewPager.setCurrentItem(1);
                        Log.d("2222","22222");
                        break;
                    case R.id.btm_nav_item3:
                        mainActivityViewPager.setCurrentItem(2);
                        Log.d("3333","33333");
                        break;
                }
                // true 会显示这个Item被选中的效果 false 则不会
                return true;
            }
        });
//        为 ViewPager 设置监听事件
        mainActivityViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                当 ViewPager 滑动后设置BottomNavigationView 选中相应选项
                bottomNavView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if(mainActivityViewPager.getCurrentItem()==1){
//            Home_fragment2.clickBack(keyCode, event);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


}