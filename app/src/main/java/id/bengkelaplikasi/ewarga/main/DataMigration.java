package id.bengkelaplikasi.ewarga.main;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class DataMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        //Mengambil schema
        RealmSchema schema = realm.getSchema();

        //membuat schema baru jika versi 0
        if (oldVersion == 0) {
            schema.create("ProfilWarga")
                    .addField("sysusermobile_id", int.class)
                    .addField("sysusermobile_username", String.class)
                    .addField("sysusermobile_deviceid", String.class)
                    .addField("rktp_nik", String.class)
                    .addField("rktp_nama", String.class)
                    .addField("rktp_tptlahir", String.class)
                    .addField("rktp_tgllahir", String.class)
                    .addField("rktp_alamat", String.class)
                    .addField("rktp_rt", String.class)
                    .addField("rktp_rw", String.class)
                    .addField("rktp_long", String.class)
                    .addField("rktp_lat", String.class);

            schema.create("Whitelist")
                    .addField("id", int.class)
                    .addField("name", String.class)
                    .addField("phone", String.class);
            schema.create("GroupLayanan")
                    .addField("kode", String.class)
                    .addField("deskripsi", String.class);
            schema.create("JenisLayanan")
                    .addField("kode_group", String.class)
                    .addField("kode_jenis", String.class)
                    .addField("deskripsi", String.class);
            schema.create("KategoriFasum")
                    .addField("kode", String.class)
                    .addField("deskripsi", String.class);
            oldVersion++;
        }

    }
}
