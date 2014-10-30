package eu.deustotech.animalclipsdemo;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.deustotech.animalclipsdemo.logic.ExpertSystem;
import eu.deustotech.animalclipsdemo.logic.ExpertTaskFactory;
import eu.deustotech.animalclipsdemo.states.FinalState;
import eu.deustotech.animalclipsdemo.states.InitialState;
import eu.deustotech.animalclipsdemo.states.NextStateListener;
import eu.deustotech.animalclipsdemo.states.StateChoice;
import eu.deustotech.animalclipsdemo.states.UsualState;
import eu.deustotech.clipsandroid.Environment;


class CustomRadioButton extends RadioButton {
	final StateChoice choice;
	
	// sigh...
	final int[][] states = new int[][] {
	    new int[] { android.R.attr.state_enabled}, // enabled
	    new int[] {-android.R.attr.state_enabled}, // disabled
	    new int[] {-android.R.attr.state_checked}, // unchecked
	    new int[] { android.R.attr.state_pressed}  // pressed
	};

	final int[] colors = new int[] {
	    Color.BLACK,
	    Color.GRAY,
	    Color.GREEN,
	    Color.BLUE
	};
	
	final ColorStateList myList = new ColorStateList(states, colors);
	
	
	public CustomRadioButton(Context context, StateChoice choice, String lblText) {
		super(context);
		this.choice = choice;    	

		// Otherwise the text color is set to white.
		// No idea why this happens doing programatically, but not adding a RadioButton to the XML file... :-S
    	this.setTextColor(myList);
		this.setText( lblText );
		this.setSelected( choice.isValid() );
	}

	public String getChoiceId() {
		return this.choice.getId();
	}
}


public class MainActivity extends Activity implements NextStateListener {
	
	final ExecutorService executor = Executors.newSingleThreadExecutor();
		
	Environment clips;
	ExpertSystem animalsExpertSystem;
	ExpertTaskFactory taskFactory;
	
	static final String appRootDirectory = "/clipsDemo";
	
	private String getResourceString(String label) {
		return getString( getResources().getIdentifier( label, "string", getBaseContext().getPackageName() ) );
	}
		
	private void createRootDirectoryIfDoesNotExist() throws FileNotFoundException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + MainActivity.appRootDirectory;
			final File file = new File(realpath);
			if( !file.exists() ) {
				file.mkdirs(); // it creates parent folders too
			}
		} else throw new FileNotFoundException("The external storage is not mounted.");
	}
	
	private String getRealFilePathCreatingIfDoesNotExist(String filename) throws IOException {
		final String state = android.os.Environment.getExternalStorageState();
		if( android.os.Environment.MEDIA_MOUNTED.equals(state) ) {
			// get the directory of the triple store
			final File topDir = android.os.Environment.getExternalStorageDirectory();
			final String realpath = topDir.getAbsolutePath() + MainActivity.appRootDirectory + "/" + filename;
			final File file = new File(realpath);
			if( !file.exists() ) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					throw new FileNotFoundException("The unexisting file '" + realpath + "' could not be created");
				}
				
				InputStream input;
				try {
					input = getResources().getAssets().open(filename);
				} catch (IOException e) {
					throw new FileNotFoundException("That's weird, the file '" + filename + "' is not available as an asset.");
				}
				
				final OutputStream output = new FileOutputStream(file);
				
				try {
					final byte[] buffer = new byte[1024]; // Adjust if you want
				    int bytesRead;
				    while ((bytesRead = input.read(buffer)) != -1) {
				        output.write(buffer, 0, bytesRead);
				    }
				} catch (IOException e) {
					throw new IOException("Not able to write the file '" + realpath + "'.");
				} finally {
					output.close();
				}
			}
			// At this point, if it didn't exist, it does now
			return realpath;
		}
		throw new FileNotFoundException("The external storage is not mounted.");
	}
	
	private void setEnabledButtons(boolean restart, boolean previous, boolean next) {
		final Button btnRestart = (Button) findViewById(R.id.btnRestart);
		btnRestart.setEnabled(restart);
		
		final Button btnPrevious = (Button) findViewById(R.id.btnPrevious);
		btnPrevious.setEnabled(previous);
		
		final Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setEnabled(next);
	}
	
	private void setLabelText(String text) {
		final TextView lblMsg = (TextView) findViewById(R.id.label);
		lblMsg.setText( text );
	}
	
	private void setChoices(final Set<StateChoice> choices) {
		final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rg.removeAllViews();
		rg.clearCheck();
		if( choices != null ) {
			for(StateChoice choice: choices) {
				final String lblText = getResourceString(choice.getId());
				final CustomRadioButton rb = new CustomRadioButton( getBaseContext(), choice, lblText );
				rg.addView( rb );
			}
		}
		
		//rg.refreshDrawableState();
		rg.invalidate();
		//ViewGroup vg = (ViewGroup) findViewById (R.id.mainLayout);
		//vg.invalidate();
	}
	
	private String getSelectedChoice() {
		final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		final int checkedRBId = rg.getCheckedRadioButtonId();
		if( checkedRBId==-1 ) return null;
		return ((CustomRadioButton) findViewById(checkedRBId)).getChoiceId();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			createRootDirectoryIfDoesNotExist();
			final String expertSystemRulesFile = getRealFilePathCreatingIfDoesNotExist("bcdemo.clp");
			final String animalsDemoFile = getRealFilePathCreatingIfDoesNotExist("animaldemo.clp");
			
			this.animalsExpertSystem = new ExpertSystem( new String[] {expertSystemRulesFile, animalsDemoFile} );
			this.animalsExpertSystem.addListener(this);
			this.animalsExpertSystem.start();
			this.taskFactory = new ExpertTaskFactory( this.animalsExpertSystem );
			
			submitTaskToExpertSystem( this.taskFactory.createRestartTask() );
		} catch (IOException e) {
			setEnabledButtons( false, false, false );
			setLabelText( e.getMessage() );
		}
	}


	@Override
	protected void onDestroy() {
		if( this.animalsExpertSystem != null ) {
			this.animalsExpertSystem.stop();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickRestart(View view) {
		submitTaskToExpertSystem( this.taskFactory.createRestartTask() );
	}
	
	public void onClickNext(View view) {
		final String chosenStateId = getSelectedChoice();
		// TODO Check that if there are choices, one is selected!
		submitTaskToExpertSystem( this.taskFactory.createNextTask(chosenStateId) );
	}
	
	public void onClickPrevious(View view) {
		submitTaskToExpertSystem( this.taskFactory.createPreviousTask() );
	}
	
	private void submitTaskToExpertSystem(Runnable runnable) {
		// Let's ensure that while CLIPS finishes current reasoning,
		// the GUI will not launch new tasks.
		setEnabledButtons(false, false, false);
				
		this.executor.submit( runnable );
	}

	@Override
	public void started(final InitialState state) {
		this.runOnUiThread(
				new Runnable() {
					public void run() {
						setEnabledButtons(false, false, true);
						setLabelText( getResourceString( state.getQuestion() ) );
						setChoices( state.getChoices() );
					}
				}
		);
	}

	@Override
	public void nextState(final UsualState state) {
		this.runOnUiThread(
				new Runnable() {
					public void run() {
						setEnabledButtons(true, true, true);
						setLabelText( getResourceString( state.getQuestion() ) );
						setChoices( state.getChoices() );
					}
				}
		);
	}

	@Override
	public void finished(final FinalState state) {
		this.runOnUiThread(
			new Runnable() {
				public void run() {
					setEnabledButtons(true, true, false);
					setLabelText( getResourceString( state.getAnswer() ) );
					setChoices( null );
				}
			}
		);
	}
}