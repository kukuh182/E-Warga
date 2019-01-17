package id.bengkelaplikasi.ewarga.helper;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public class Permissions {

    public static class Calendar {
        public static final String GROUP_CALENDAR = android.Manifest.permission_group.CALENDAR;
        public static final String READ_CALENDAR = android.Manifest.permission.READ_CALENDAR;
        public static final String WRITE_CALENDAR = android.Manifest.permission.WRITE_CALENDAR;
    }

    public static class Camera {
        public static final String GROUP_CAMERA = android.Manifest.permission_group.CAMERA;
        public static final String CAMERA = android.Manifest.permission.CAMERA;
    }

    public static class Contact {
        public static final String GROUP_CONTACT = android.Manifest.permission_group.CONTACTS;
        public static final String READ_CONTACT = android.Manifest.permission.READ_CONTACTS;
        public static final String WRITE_CONTACT = android.Manifest.permission.WRITE_CONTACTS;
        public static final String GET_ACCOUNT = android.Manifest.permission.GET_ACCOUNTS;
    }

    public static class Location {
        public static final String GROUP_LOCATION = android.Manifest.permission_group.LOCATION;
        public static final String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
        public static final String ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    }

    public static class Microphone {
        public static final String GROUP_MICROPHONE = android.Manifest.permission_group.MICROPHONE;
        public static final String RECORD_AUDIO = android.Manifest.permission.RECORD_AUDIO;
    }

    public static class Phone {
        public static final String GROUP_PHONE = android.Manifest.permission_group.PHONE;
        public static final String READ_PHONE_STATE = android.Manifest.permission.READ_PHONE_STATE;
        public static final String CALL_PHONE = android.Manifest.permission.CALL_PHONE;
        public static final String READ_CALL_LOG = android.Manifest.permission.READ_CALL_LOG;
        public static final String WRITE_CALL_LOG = android.Manifest.permission.WRITE_CALL_LOG;
        public static final String ADD_VOICEMAIL = android.Manifest.permission.ADD_VOICEMAIL;
        public static final String USE_SIP = android.Manifest.permission.USE_SIP;
        public static final String PROCESS_OUTGOING_CALLS = android.Manifest.permission.PROCESS_OUTGOING_CALLS;
    }

    public static class Sensor {
        public static final String GROUP_SENSORS = android.Manifest.permission_group.SENSORS;
        public static final String BODY_SENSORS = android.Manifest.permission.BODY_SENSORS;
    }

    public static class Sms {
        public static final String GROUP_SMS = android.Manifest.permission_group.SMS;
        public static final String SEND_SMS = android.Manifest.permission.SEND_SMS;
        public static final String RECEIVE_SMS = android.Manifest.permission.RECEIVE_SMS;
        public static final String READ_SMS = android.Manifest.permission.READ_SMS;
        public static final String RECEIVE_WAP_PUSH = android.Manifest.permission.RECEIVE_WAP_PUSH;
        public static final String RECEIVE_MMS = android.Manifest.permission.RECEIVE_MMS;
    }

    public static class Storage {
        public static final String GROUP_STORAGE = android.Manifest.permission_group.STORAGE;
        public static final String READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        public static final String WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }
}
