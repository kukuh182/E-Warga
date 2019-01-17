package id.bengkelaplikasi.ewarga.main;

import android.app.Application;
import android.content.Context;

import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.helper.Constanta;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class App extends Application {

    private static Context context;
    private static App app;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        app = this;
        //Custom Fonts
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/pt_san.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        //Configuration Realm Database
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(Constanta.Config.DATABASE_NAME)
                .schemaVersion(0)
                .migration(new DataMigration())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        Realm.getInstance(config);
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }
    public static synchronized Context getContext() {
        return context;
    }

    public static synchronized App getInstance() {
        return app;
    }
}
