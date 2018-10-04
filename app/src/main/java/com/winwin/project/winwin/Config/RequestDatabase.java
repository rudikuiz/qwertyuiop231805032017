package com.winwin.project.winwin.Config;

import static com.winwin.project.winwin.Config.http.URLHTTP;


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
    public static final String URL_POST_DATA_KUNJUNGAN = URLHTTP + "add_kunjungan.php";
    public static final String URL_GET_ID_PENGAJUAN = URLHTTP+"select_id_pengajuan.php?pengajuan_id_client=";
    public static final String URL_GET_ID_MEMBER = URLHTTP +"getMember_idkaryawan.php?kar_namalengkap=";
    public static final String URL_LOGIN = URLHTTP +"login.php";
    public static final String URL_SEND_LOCATION = URLHTTP + "update_lokasi.php";
    //    public static final String URL_GET_IMAGE_FROM_FOLDER = "http://hq.ppgwinwin.com/winwin/home/uploads/";
    public static final String URL_GET_IMAGE_FROM_FOLDER = URLHTTP + "images/";
    public static final String URL_UPDATE_PROFIL_TRIAL = URLHTTP + "update_profile.php";
    public static final String URL_GET_JANJI_TRIAL = URLHTTP + "cek_req_client.php?pengajuan_id_client=";
    public static final String URL_GET_ANALIS = URLHTTP + "view_analisa.php?member_id_karyawan=";
    public static final String URL_POST_VISIT = URLHTTP + "pengajuan_visit.php";
    public static final String URL_REQUES_BAYAR = URLHTTP + "request_janji_bayar.php";
    public static final String URL_COUNT_NOTIF = URLHTTP + "view_debt.php?member_id_karyawan=";
    public static final String URL_BADDEBT = URLHTTP + "pengajuan_baddebt.php";
    public static final String URL_ADDTUGAS = URLHTTP + "add_tugas.php";
    public static final String URL_VIEW = URLHTTP + "profile.php?kar_id=";
    public static final String URL_DETAIL = URLHTTP + "detailclient.php?cli_id=";
    public static final String URL_KOMISI = URLHTTP + "view_komisi.php?member_id_karyawan=";
}
