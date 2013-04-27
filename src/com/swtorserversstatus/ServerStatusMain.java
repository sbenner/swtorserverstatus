package com.swtorserversstatus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.*;
import com.swtorserversstatus.model.Server;
import com.swtorserversstatus.ui.*;
import com.swtorserversstatus.utils.ServerListLoader;
import com.swtorserversstatus.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;


public class ServerStatusMain extends FragmentActivity
        implements ServerStatus {
    /**
     * Called when the activity is first created.
     */
    ProgressDialog pd;
    private ListView usServerList;
    private ListView euServerList;
    private ListView lvMyServerList;
    private FragmentActivity mActivity;
    private TabHost mTabHost;
 public   static ViewPager  mViewPager;
    public static HashSet<Server> list;
    private boolean servers = true;
    TabsAdapter mTabsAdapter;
    LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {



        mActivity = this;
        setContentView(R.layout.main);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mViewPager = (ViewPager)findViewById(R.id.pager);





            mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);



            mTabsAdapter.addTab(mTabHost.newTabSpec("my_servers").setIndicator("",getResources().getDrawable(R.drawable.tor_server)),
                    StatusListFragment.class, null);


            mTabsAdapter.addTab(mTabHost.newTabSpec("us_servers").setIndicator("",getResources().getDrawable(R.drawable.a)),
                                StatusListFragment.class, null);


            mTabsAdapter.addTab(mTabHost.newTabSpec("eu_servers").setIndicator("",getResources().getDrawable(R.drawable.b)),
                                StatusListFragment.class, null);




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("my_servers", mTabHost.getCurrentTabTag());
    }


    public void onPause() {

        super.onPause();
        if (pd != null) pd.dismiss();
    }

//    public void processDownload(final String message) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                pd.setMessage(message);
//
//            }
//        });
//    }

    public void processMessage(Message msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
    }



//    public Object onRetainNonConfigurationInstance() {
//
//        return list;
//
//    }




    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.search:
                if (list == null) {
                    Utils.showToast(this, "No Servers Were loaded.");
                    return false;
                }
                SearchDialog sd = null;
                if (mTabHost.getCurrentTabTag().equals("my_servers")) {
                    sd = new SearchDialog(this, getLvMyServerList());
                }
                if (mTabHost.getCurrentTabTag().equals("us_servers")) {
                    sd = new SearchDialog(this, getUsServerList());
                }
                if (mTabHost.getCurrentTabTag().equals("eu_servers")) {
                    sd = new SearchDialog(this, getEuServerList());
                }
                sd.show();

                return true;

            case R.id.myserver:
                if ( ServerListLoader.servers == null) {
                    Utils.showToast(this, "No Servers Were loaded.");
                    return false;
                }
                AddServerDialog ad = new AddServerDialog(this, ServerListLoader.servers,mViewPager);
                ad.show();

                return true;

            case R.id.about:
                AboutDialog aboutDialog = new AboutDialog(this);
                aboutDialog.show();

                return true;

            case R.id.exit:
                System.exit(0);
                return true;

        }

        return false;

    }



    public ListView getUsServerList() {
        return usServerList;
    }

    public void setUsServerList(ListView usServerList) {
        this.usServerList = usServerList;
    }

    public ListView getEuServerList() {
        return euServerList;
    }

    public void setEuServerList(ListView euServerList) {
        this.euServerList = euServerList;
    }

    public ListView getLvMyServerList() {
        return lvMyServerList;
    }

    public void setLvMyServerList(ListView lvMyServerList) {
        this.lvMyServerList = lvMyServerList;
    }

    public ListView getServerList(String which) {
        ListView lv = null;
        if (which.equals("my")) {
            lv = this.lvMyServerList;
        } else if (which.equals("eu")) {
            lv = this.euServerList;
        } else if (which.equals("us")) {
            lv = this.usServerList;
        }
        return lv;
    }

    public void processDownload(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public static class TabsAdapter extends FragmentPagerAdapter
       implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }


            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
           // notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);

            Bundle bundle = new Bundle();
            bundle.putInt("tab",position);
            //return StatusListFragment.instantiate(mContext,"d",info.args);
            return Fragment.instantiate(mContext, info.clss.getName(), bundle);
        }


        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mTabHost.setCurrentTab(position);
            mViewPager.setCurrentItem(position);
            //if(tabId.equals("my_servers"))notifyDataSetChanged();
        }



        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }


        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.

            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            //System.out.println("page selected: "+position);

            widget.setDescendantFocusability(oldFocusability);

//            if(position==0) notifyDataSetChanged();


        }


        public void onPageScrollStateChanged(int state) {
        }
    }


}



















