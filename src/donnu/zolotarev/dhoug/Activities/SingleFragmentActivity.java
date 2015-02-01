package donnu.zolotarev.dhoug.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import donnu.zolotarev.dhoug.R;

public abstract class SingleFragmentActivity extends Activity {
    protected abstract Fragment createFragment();

    protected int getContainerID(){
        return R.id.content_frame;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContent();
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getFragmentManager();
        Fragment myFragment = fm.findFragmentById(getContainerID());

        if (myFragment == null){
            myFragment = createFragment();
            fm.beginTransaction()
                    .add(getContainerID(), myFragment)
                    .commit();
        }
    }

    protected void beforeSetContent() {
    }

    public void loadRootFragment(Fragment fragment, boolean addToBackStack){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (addToBackStack){
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.setCustomAnimations(
                R.anim.card_flip_right_in, R.anim.card_flip_right_out,
                R.anim.card_flip_left_in, R.anim.card_flip_left_out);
        fragmentTransaction.replace(getContainerID(), fragment).commit();
    }

    public void popBackStack(){
        getFragmentManager().popBackStack();
    }
}
