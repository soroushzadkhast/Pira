package gereh.pira;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class TextView extends android.widget.TextView {


    public TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "irsans.ttf");
        setTypeface(tf);
    }
}
