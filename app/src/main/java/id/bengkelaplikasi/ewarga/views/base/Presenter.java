package id.bengkelaplikasi.ewarga.views.base;

/**
 * Created by Kukuh182 on 08-Sep-17
 * Bengkel Aplikasi
 * kukuhpr21@gmail.com
 */

public interface Presenter <T extends View> {
    void onAttach(T view);
    void onDetach();
}