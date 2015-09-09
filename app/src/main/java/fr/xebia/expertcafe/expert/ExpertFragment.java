package fr.xebia.expertcafe.expert;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import butterknife.Bind;
import fr.xebia.expertcafe.R;
import fr.xebia.expertcafe.common.BaseFragment;
import timber.log.Timber;

public class ExpertFragment extends BaseFragment {

    public static final String BUNDLE_EXPERT_ID = "BUNDLE_EXPERT_ID";

    @Bind(R.id.descTextView) TextView descTextView;

    private Expert expert;
    private String expertId;

    public static Fragment newInstance(String expertId) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_EXPERT_ID, expertId);
        bundle.putInt(BUNDLE_LAYOUT_ID, R.layout.fragment_expert);
        ExpertFragment fragment = new ExpertFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            expertId = bundle.getString(BUNDLE_EXPERT_ID);
        }

        if (expertId != null) {
            ParseQuery<Expert> query = new ParseQuery<>(Expert.class);
            query.fromLocalDatastore();
            query.getInBackground(expertId, new GetCallback<Expert>() {
                @Override
                public void done(Expert localExpert, ParseException e) {
                    if (e == null) {
                        expert = localExpert;
                        bindView();
                    } else {
                        Timber.e(e, "Cannot retrieve expert " + expertId + " from local datastore");
                    }
                }
            });
        }
    }

    protected void bindView() {
        descTextView.setText(expert.getDescription());
    }
}
