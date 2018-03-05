package com.winwin.project.winwin.Config;

import static com.winwin.project.winwin.Config.http.URLHTTP;

/**
 * Created by Ayo Maju on 07/02/2018.
 * Updated by Muhammad Iqbal on 07/02/2018.
 */

public class RequestDatabase {
    public static final String URL_GET_ALL = URLHTTP+"view.php?pengajuan_assigned_to=";
    public static final String URL_GET_DETAIL = URLHTTP+"perdata.php?cli_id=";
    public static final String URL_POST_KARYAWAN = URLHTTP+"insert_karyawan.php";
    public static final String URL_POST_MEMBERSYSTEM = URLHTTP+"insert_membersystem.php";
    public static final String URL_GET_CATATAN = URLHTTP+"view_catatan.php?ajunot_cli_id=";
    public static final String URL_ADD_NOTES = URLHTTP+"add_notes.php";
    public static final String URL_GET_HISTORY = URLHTTP+"view_history.php?pemb_id_client=";
    public static final String URL_GET_IMAGE = URLHTTP+"view_ktp.php?cli_doc_cli_id=";
    public static final String URL_GET_JATUH_TEMPO = URLHTTP+"view_jatuh_tempo.php?pengajuan_assigned_to=";
    public static final String URL_POST_PROFIL = URLHTTP+"profile.php?kar_id=";
    public static final String URL_GET_DETAIL_PEMBAYARAN = URLHTTP+"history_pembayaran.php?pengajuan_assigned_to=";
    public static final String URL_GET_KOMISI_TAHAP = URLHTTP+"komisi_tahap.php?pengajuan_assigned_to=";
    public static final String URL_GET_PENCAIRAN = URLHTTP+"history_pencairan.php?pengajuan_assigned_to=";
    public static final String URL_POST_DATA_KUNJUNGAN = URLHTTP+"data_kunjungan.php?id_pengajuan=";
    public static final String URL_GET_ID_PENGAJUAN = URLHTTP+"select_id_pengajuan.php?pengajuan_id_client=";
    public static final String URL_GET_ID_MEMBER = URLHTTP +"getMember_idkaryawan.php?kar_namalengkap=";
    public static final String URL_LOGIN = URLHTTP +"login.php";
    public static final String URL_MEMBER_ID_KARYAWAN = URLHTTP +"getMemberIDUser.php?member_id=";
}
