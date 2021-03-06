package fr.xebia.expertcafe.ui.expert;

import android.os.Bundle;
import android.view.View;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.Bind;
import fr.xebia.expertcafe.R;
import fr.xebia.expertcafe.common.BaseActivity;
import fr.xebia.expertcafe.common.ParseConstant;
import fr.xebia.expertcafe.model.Expert;
import fr.xebia.expertcafe.ui.LoopViewPager;
import timber.log.Timber;

import static android.view.View.GONE;
import static fr.xebia.expertcafe.common.ParseConstant.EXPERT_TABLE;

public class ExpertPagerActivity extends BaseActivity {

    @Bind(R.id.viewPager) LoopViewPager pager;
    @Bind(R.id.pagerIndicator) CirclePageIndicator pageIndicator;
    @Bind(R.id.progressBar) View progressBar;

    private ExpertPagerAdapter adapter;

    public ExpertPagerActivity() {
        super(R.layout.activity_home, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExperts();
    }

    private void getExperts() {
        final ParseQuery<Expert> query = new ParseQuery<>(Expert.class);
        query.orderByAscending(ParseConstant.FOUNDATION);
        query.findInBackground(new FindCallback<Expert>() {
            @Override
            public void done(final List<Expert> experts, ParseException e) {
                progressBar.setVisibility(GONE);
                if (e == null) {
                    ParseObject.unpinAllInBackground(EXPERT_TABLE, new DeleteCallback() {
                        public void done(ParseException e) {
                            ParseObject.pinAllInBackground(EXPERT_TABLE, experts);
                            adapter = new ExpertPagerAdapter(getSupportFragmentManager(), experts);
                            pager.setAdapter(adapter);
                            pageIndicator.setViewPager(pager);
                        }
                    });
                } else {
                    Timber.e(e, "Cannot get experts");
                }
            }
        });
    }

}
