package vn.flast.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.flast.models.Data;
import vn.flast.models.FlastNote;
import vn.flast.repositories.FlastNoteRepository;
import vn.flast.utils.BuilderParams;
import vn.flast.utils.Common;
import vn.flast.utils.JsonUtils;

@Service("flastNoteServer")
@RequiredArgsConstructor
public class FlastNoteService {

    private final FlastNoteRepository noteRepository;

    public void createLeadNote(Data lead) {
        BuilderParams body = BuilderParams.create()
            .addParam("name", lead.getProductName())
            .addParam("note", lead.getNote());
        FlastNote flastNote = factoryAsLead();
        flastNote.setObjectId(lead.getId());
        flastNote.setDataType(FlastNote.DATA_TYPE_LEAD_NOTE);
        flastNote.setContent(JsonUtils.toJson(body));
        noteRepository.save(flastNote);
    }

    private FlastNote factoryAsLead() {
        FlastNote flastNote = new FlastNote();
        flastNote.setObjectType("data");
        assignUser(flastNote);
        return flastNote;
    }

    private void assignUser(FlastNote flastNote) {
        flastNote.setUserId(Common.getUserId());
        flastNote.setUserNote(Common.getSsoId());
    }
}
