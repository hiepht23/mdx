package com.erdemorman.mdx.ui.colors;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialColorTone;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorTonesAdapter extends RecyclerView.Adapter<ColorTonesAdapter.ColorToneViewHolder> {
    private Context mContext;
    private final List<MaterialColorTone> mColorTones;

    public ColorTonesAdapter(Context context, List<MaterialColorTone> colorTones) {
        mContext = context;
        mColorTones = colorTones;
    }

    @Override
    public ColorToneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_color_tones_item_color_tone, parent, false);

        return new ColorToneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColorToneViewHolder holder, int position) {
        MaterialColorTone colorTone = mColorTones.get(position);
        int textColor = ContextCompat.getColor(mContext,
                colorTone.isWhiteText() ? R.color.tone_white_text : R.color.tone_black_text);

        holder.toneName.setText(colorTone.getName());
        holder.toneColor.setText(colorTone.getColor());

        holder.toneName.setTextColor(textColor);
        holder.toneColor.setTextColor(textColor);

        holder.toneContainer.setBackgroundColor(
                Color.parseColor(colorTone.getColor())
        );
    }

    @Override
    public int getItemCount() {
        return mColorTones.size();
    }

    class ColorToneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.tone_container) View toneContainer;
        @Bind(R.id.tone_name) TextView toneName;
        @Bind(R.id.tone_color) TextView toneColor;

        public ColorToneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MaterialColorTone colorTone = mColorTones.get(getAdapterPosition());

            copyColorToClipboard(colorTone);
            showColorCopiedMessage(view, colorTone);
        }

        private void copyColorToClipboard(MaterialColorTone colorTone) {
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            String clipDataLabel = mContext.getString(R.string.tone_copied_clipboard_label);

            ClipData clip = ClipData.newPlainText(clipDataLabel, colorTone.getColor());
            clipboard.setPrimaryClip(clip);
        }

        private void showColorCopiedMessage(View view, MaterialColorTone colorTone) {
            String messageText = mContext.getString(R.string.tone_copied_to_clipboard,
                    colorTone.getColor());
            Snackbar.make(view, messageText, Snackbar.LENGTH_SHORT).show();
        }
    }
}