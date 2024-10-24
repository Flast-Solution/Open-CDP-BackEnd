package vn.flast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.flast.models.DataMedia;
import vn.flast.repositories.DataMediaRepository;

import java.util.List;

@Service
public class DataService {

    @Autowired
    private DataMediaRepository dataMediaRepository;

    public enum DATA_STATUS {

        CREATE_DATA(0),
        DO_NOT_MANUFACTORY(1),
        IS_CONTACT(2),
        CONTACT_LATER(6),
        KO_LIEN_HE_DUOC(4),
        THANH_CO_HOI(7);

        private final int statusCode;

        DATA_STATUS(int levelCode) {
            this.statusCode = levelCode;
        }

        public int getStatusCode() {
            return this.statusCode;
        }

        public String getString() {
            return String.valueOf(this.statusCode);
        }
    }

    public enum DATA_SOURCE {
        WEB(0),
        FACEBOOK(1),
        ZALO(2),
        HOTLINE(3),
        DIRECT(4),
        EMAIL(5),
        MKT0D(6),
        GIOITHIEU(7),
        CSKH(8),
        WHATSAPP(11),
        PARTNER(9),
        SHOPEE(10);
        private final int source;
        DATA_SOURCE(int source) {
            this.source = source;
        }
        public int getSource() {
            return this.source;
        }
    }

    public void createAndUpdateDataMedias(List<String> urls, int sessionId, int dataId) {
        if(!urls.isEmpty()) {
            urls.forEach(url -> dataMediaRepository.save(new DataMedia(dataId, sessionId, url)));
        }
    }
}
