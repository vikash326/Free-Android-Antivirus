package android.free.antivirus;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import free.an.droid.antivirus.rinix.R;

public class RD extends Builder
{
	private TextView title = null;
	private TextView message = null;
	private ImageView icon = null;

	public RD( Context context )
	{
		super( context );

		View customTitle = View.inflate( context, R.layout.rd_title, null );
		title = (TextView) customTitle.findViewById( R.id.alertTitle );
		icon = (ImageView) customTitle.findViewById( R.id.icon );
		setCustomTitle( customTitle );

		View customMessage = View.inflate( context,
				R.layout.rd_message, null );
		message = (TextView) customMessage.findViewById( R.id.message );
		setView( customMessage );
	}

	@Override
	public RD setTitle( int textResId )
	{
		title.setText( textResId );
		return this;
	}

	@Override
	public RD setTitle( CharSequence text )
	{
		title.setText( text );
		return this;
	}

	@Override
	public RD setMessage( int textResId )
	{
		message.setText( textResId );
		return this;
	}

	@Override
	public RD setMessage( CharSequence text )
	{
		message.setText( text );
		return this;
	}

	@Override
	public RD setIcon( int drawableResId )
	{
		icon.setImageResource( drawableResId );
		return this;
	}

	@Override
	public RD setIcon( Drawable icon )
	{
		this.icon.setImageDrawable( icon );
		return this;
	}
}
