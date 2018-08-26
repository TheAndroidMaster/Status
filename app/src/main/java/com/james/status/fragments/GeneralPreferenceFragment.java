package com.james.status.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.james.status.R;
import com.james.status.adapters.PreferenceSectionAdapter;
import com.james.status.data.PreferenceData;
import com.james.status.data.preference.BasePreferenceData;
import com.james.status.data.preference.BooleanPreferenceData;
import com.james.status.data.preference.ColorPreferenceData;
import com.james.status.data.preference.IntegerPreferenceData;
import com.james.status.dialogs.BackupDialog;
import com.james.status.utils.StaticUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralPreferenceFragment extends SimpleFragment implements BasePreferenceData.OnPreferenceChangeListener {

    private PreferenceSectionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        RecyclerView recycler = v.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        List<BasePreferenceData> preferences = new ArrayList<>();

        preferences.addAll(Arrays.asList(
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_COLOR_AUTO,
                                getString(R.string.preference_bar_color_auto),
                                getString(R.string.preference_bar_color_auto_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.COLORS
                        ),
                        this
                ),
                new ColorPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Integer>(
                                PreferenceData.STATUS_COLOR,
                                getString(R.string.preference_bar_color_chooser),
                                BasePreferenceData.Identifier.SectionIdentifier.COLORS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_HOME_TRANSPARENT,
                                getString(R.string.preference_transparent_home),
                                getString(R.string.preference_transparent_home_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.COLORS
                        ),
                        null
                ),
                new ColorPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Integer>(
                                PreferenceData.STATUS_ICON_COLOR,
                                getString(R.string.preference_default_color_icon),
                                BasePreferenceData.Identifier.SectionIdentifier.ICONS
                        ),
                        null
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_DARK_ICONS,
                                getString(R.string.preference_dark_icons),
                                getString(R.string.preference_dark_icons_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ICONS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_PREVENT_ICON_OVERLAP,
                                getString(R.string.preference_prevent_icon_overlap),
                                getString(R.string.preference_prevent_icon_overlap_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ICONS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_TINTED_ICONS,
                                getString(R.string.preference_tinted_icons),
                                getString(R.string.preference_tinted_icons_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ICONS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_BUMP_MODE,
                                getString(R.string.preference_bump_mode),
                                getString(R.string.preference_bump_mode_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ICONS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_BACKGROUND_ANIMATIONS,
                                getString(R.string.preference_background_animations),
                                getString(R.string.preference_background_animations_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ANIMATIONS
                        ),
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_ICON_ANIMATIONS,
                                getString(R.string.preference_icon_animations),
                                getString(R.string.preference_icon_animations_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.ANIMATIONS
                        ),
                        this
                ),
                /*new BooleanPreferenceData( //TODO: re-enable once stuff is fixed
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_NOTIFICATIONS_HEADS_UP,
                                getString(R.string.preference_heads_up),
                                getString(R.string.preference_heads_up_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.NOTIFICATIONS
                        ),
                        null
                ),*/
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_PERSISTENT_NOTIFICATION,
                                getString(R.string.preference_persistent_notification),
                                getString(R.string.preference_persistent_notification_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        this
                )));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            preferences.add(
                    new BooleanPreferenceData(
                            getContext(),
                            new BasePreferenceData.Identifier<Boolean>(
                                    PreferenceData.STATUS_IGNORE_PERMISSION_CHECKING,
                                    getString(R.string.preference_ignore_permission_checking),
                                    getString(R.string.preference_ignore_permission_checking_desc),
                                    BasePreferenceData.Identifier.SectionIdentifier.OTHER
                            ),
                            this
                    )
            );
        }

        preferences.addAll(Arrays.asList(
                new IntegerPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Integer>(
                                PreferenceData.STATUS_HEIGHT,
                                getString(R.string.preference_height),
                                getString(R.string.preference_height_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        getString(R.string.unit_px),
                        0,
                        Integer.MAX_VALUE,
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_TRANSPARENT_MODE,
                                getString(R.string.preference_transparent_mode),
                                getString(R.string.preference_transparent_mode_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        this
                ),
                new IntegerPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Integer>(
                                PreferenceData.STATUS_SIDE_PADDING,
                                getString(R.string.preference_side_padding),
                                getString(R.string.preference_side_padding_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        getString(R.string.unit_dp),
                        0,
                        100,
                        this
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_BURNIN_PROTECTION,
                                getString(R.string.preference_burnin_protection),
                                getString(R.string.preference_burnin_protection_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        this
                ),
                new BasePreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier(
                                null,
                                getString(R.string.preference_backups),
                                getString(R.string.preference_backups_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        )
                ) {
                    @Override
                    public void onClick(View v) {
                        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        if (StaticUtils.isPermissionsGranted(getContext(), permissions))
                            new BackupDialog((Activity) getContext()).show();
                        else StaticUtils.requestPermissions((Activity) getContext(), permissions);
                    }
                },
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_HIDE_ON_VOLUME,
                                getString(R.string.preference_hide_on_volume),
                                getString(R.string.preference_hide_on_volume_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        null
                ),
                new BooleanPreferenceData(
                        getContext(),
                        new BasePreferenceData.Identifier<Boolean>(
                                PreferenceData.STATUS_DEBUG,
                                getString(R.string.preference_debug),
                                getString(R.string.preference_debug_desc),
                                BasePreferenceData.Identifier.SectionIdentifier.OTHER
                        ),
                        null
                )
        ));

        adapter = new PreferenceSectionAdapter(getContext(), preferences);
        recycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void filter(@Nullable String filter) {
        if (adapter != null) adapter.filter(filter);
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.tab_settings);
    }

    @Override
    public void onPreferenceChange(Object preference) {
        StaticUtils.updateStatusService(getContext(), false);
    }
}
