package #.login;

import #

public class LoginActivity extends AppCompatActivity {
	/*other parameters*/

    @Inject
    public IChatClientApi mIChatClientApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .appComponent(((App)getApplication()).getAppComponent())
                .apiModule(new ApiModule(BuildConfig.BASE_URL))
                .build();

        apiComponent.inject(this);
    }

	/* other methods   */

    private void authToApi(){
		Observable<AuthResponseObject> call = mIChatClientApi.userAuth(login, password);

		call.observeOn(AndroidSchedulers.mainThread())
		        .subscribe(authResponseObject -> {
					/* login logic */
		            
		        }, throwable -> {
		            // on Error

		            if (throwable instanceof NoNetworkException) { // handle 'no network'
						Toast.makeText(LoginActivity.this, "Connection error. Please try again.", 														Toast.LENGTH_SHORT).show();
		            } else {
		                throwable.printStackTrace();
		                Toast.makeText(LoginActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT)
		                        .show();
		            }
		        });
    }
}
