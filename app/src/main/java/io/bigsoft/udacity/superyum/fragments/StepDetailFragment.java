package io.bigsoft.udacity.superyum.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.model.StepsModel;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepDetailFragment extends Fragment {

    private StepsModel stepsModel;
    @BindView(R.id.step_long_description) TextView description;
    @BindView(R.id.no_video_text_view) TextView noVideoTxt;
    @BindView(R.id.media_player) SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private long mExoplayerPosition = C.TIME_UNSET;

    public StepDetailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState == null) {
            ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (!stepsModel.getVideoURL().equals("") && (networkInfo != null && networkInfo.isConnected())) {
                noVideoTxt.setVisibility(View.GONE);
                initializePlayer(Uri.parse(stepsModel.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        if (savedInstanceState != null) {
            stepsModel = (StepsModel) savedInstanceState.getSerializable("ser");
            if (!stepsModel.getVideoURL().equals("")) {
                mExoplayerPosition = savedInstanceState.getLong(getResources().getString(R.string.exoplayer_position_key));
                noVideoTxt.setVisibility(View.GONE);
                initializePlayer(Uri.parse(stepsModel.getVideoURL()));
            } else {
                mPlayerView.setVisibility(View.GONE);
                noVideoTxt.setVisibility(View.VISIBLE);
            }
        }

        description.setText(stepsModel.getDescription());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Baking video");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (mExoplayerPosition != C.TIME_UNSET) mExoPlayer.seekTo(mExoplayerPosition);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (!stepsModel.getVideoURL().equals("") && mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // TODO

        if (mExoPlayer != null) mExoplayerPosition = mExoPlayer.getCurrentPosition();
        outState.putSerializable("ser", stepsModel);
        outState.putLong(getResources().getString(R.string.exoplayer_position_key), mExoplayerPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (stepsModel != null)
            releasePlayer();
    }

    public void setStepsModel(StepsModel stepsModel) {
        this.stepsModel = stepsModel;
    }
}
